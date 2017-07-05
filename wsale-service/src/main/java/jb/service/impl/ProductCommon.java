package jb.service.impl;

import jb.absx.F;
import jb.listener.Application;
import jb.pageModel.*;
import jb.service.*;
import jb.util.EnumConstants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wsale.concurrent.CacheKey;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import java.util.*;

/**
 * Created by wenming on 2016/8/25.
 */
@Service
public class ProductCommon {
    @Autowired
    private ZcProductServiceI zcProductService;

    @Autowired
    private ZcFileServiceI zcFileService;

    @Autowired
    private ZcProductLikeServiceI zcProductLikeService;

    @Autowired
    private UserServiceI userService;

    @Autowired
    private ZcProductRangeServiceI zcProductRangeService;

    @Autowired
    private ZcProductMarginServiceI zcProductMarginService;

    @Autowired
    private ZcWalletServiceI zcWalletService;
    @Autowired
    private ZcAuctionServiceI zcAuctionService;

    public DataGrid dataGrid(PageHelper ph, ZcProduct zcProduct, final String userId) {
        DataGrid dataGrid = zcProductService.dataGrid(zcProduct, ph);
        List<ZcProduct> products = (List<ZcProduct>) dataGrid.getRows();
        if(!CollectionUtils.isEmpty(products)) {
            final CompletionService completionService = CompletionFactory.initCompletion();
            Date now = new Date();
            for (ZcProduct product : products) {
                // 获取封面
                completionService.submit(new Task<ZcProduct, String>(product) {
                    @Override
                    public String call() throws Exception {
                        ZcFile file = new ZcFile();
                        file.setObjectType(EnumConstants.OBJECT_TYPE.PRODUCT.getCode());
                        file.setObjectId(getD().getId());
                        file.setFileType("FT01");
                        ZcFile f = zcFileService.get(file);
                        return f == null ? null : f.getFileHandleUrl();
                    }

                    protected void set(ZcProduct d, String v) {
                        if (v != null)
                            d.setIcon(v);
                    }
                });

                if(!F.empty(userId)) {
                    // 是否点赞/围观
                    completionService.submit(new Task<ZcProduct, Boolean>(product) {
                        @Override
                        public Boolean call() throws Exception {
                            ZcProductLike like = new ZcProductLike();
                            like.setProductId(getD().getId());
                            like.setUserId(userId);
                            return zcProductLikeService.get(like) == null ? false : true;
                        }

                        protected void set(ZcProduct d, Boolean v) {
                            if (v != null)
                                d.setLiked(v);
                        }
                    });
                }

                // 距街拍
                long deadlineLen = 0;
                if(product.getRealDeadline() != null)
                    deadlineLen = product.getRealDeadline().getTime() - now.getTime();
                deadlineLen = deadlineLen <= 0 ? 0 : deadlineLen/1000;
                product.setDeadlineLen(deadlineLen);
            }
            completionService.sync();
        }

        return dataGrid;
    }

    public DataGrid dataGridComplex(PageHelper ph, ZcProduct zcProduct, final String userId) {
        final String atteId = zcProduct.getAtteId();
        DataGrid dataGrid = zcProductService.dataGridComplex(zcProduct, ph);
        List<ZcProduct> products = (List<ZcProduct>) dataGrid.getRows();
        if(!CollectionUtils.isEmpty(products)) {
            final CompletionService completionService = CompletionFactory.initCompletion();
            final Date now = new Date();
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            for (ZcProduct product : products) {
                final String productId = product.getId();
                completionService.submit(new Task<List<Map<String, Object>>, Map<String, Object>>(result) {
                    @Override
                    public Map<String, Object> call() throws Exception {
                        Map<String, Object> map = new HashMap<String, Object>();
                        ZcProduct p = zcProductService.get(productId, userId);
                        // 距街拍
                        long deadlineLen = 0;
                        if(p.getRealDeadline() != null)
                            deadlineLen = p.getRealDeadline().getTime() - now.getTime();
                        deadlineLen = deadlineLen <= 0 ? 0 : deadlineLen/1000;
                        p.setDeadlineLen(deadlineLen);

                        // 是否需要跳转保证金
                        boolean marginFlag = false;
                        if(p.getMargin() != null && p.getMargin() > 0) {
                            ZcProductMargin margin = new ZcProductMargin();
                            margin.setProductId(p.getId());
                            margin.setBuyUserId(userId);
                            margin.setPayStatus("PS02");
                            margin = zcProductMarginService.get(margin);
                            if(margin == null) {
                                marginFlag = true;
                            }
                        }
                        p.setMarginFlag(marginFlag);

                        map.put("product", p);

                        // 获取当前加价幅度
                        double rangePrice = zcProductRangeService.getRangePrice(p.getId(), p.getCurrentPrice());
                        map.put("rangePrice", rangePrice);

                        // 获取点赞/围观列表
                        PageHelper ph = new PageHelper();
                        ph.setPage(1);
                        ph.setRows(12);
                        ph.setSort("addtime");
                        ph.setOrder("desc");
                        ZcProductLike like = new ZcProductLike();
                        like.setProductId(productId);
                        //List<ZcProductLike> likes = zcProductLikeService.query(like);
                        DataGrid likesDataGrid = zcProductLikeService.dataGrid(like, ph);
                        List<ZcProductLike> likes = (List<ZcProductLike>)likesDataGrid.getRows();
                        if(!CollectionUtils.isEmpty(likes)) {
                            for(ZcProductLike productLike : likes) {
                                completionService.submit(new Task<ZcProductLike, User>(new CacheKey("user", productLike.getUserId()), productLike) {
                                    @Override
                                    public User call() throws Exception {
                                        User user = userService.get(getD().getUserId(), true);
                                        return user;
                                    }

                                    protected void set(ZcProductLike d, User v) {
                                        if (v != null)
                                            d.setUser(v);
                                    }

                                });
                            }
                        }
                        map.put("likes", likesDataGrid);

                        boolean closeFlag = false;
                        // 关注人列表查询发布人信息
                        if(!F.empty(atteId)) {
                            User user = userService.get(p.getAddUserId(), atteId);
                            ZcWallet q = new ZcWallet();
                            q.setUserId(user.getId());
                            ZcWallet wallet = zcWalletService.get(q);
                            if(wallet != null) {
                                if(wallet.getProtection() >= Double.parseDouble(Application.getString("AF06"))) {
                                    user.setIsPayBond(true);
                                } else {
                                    user.setIsPayBond(false);
                                }
                            } else {
                                user.setIsPayBond(false);
                            }

                            map.put("user", user);
                        } else { // 判断拍品封存是否是参拍用户
                            if(p.getIsClose() && !userId.equals(p.getAddUserId())) {
                                ZcAuction auction = new ZcAuction();
                                auction.setProductId(p.getId());
                                auction.setBuyerId(userId);
                                if(CollectionUtils.isEmpty(zcAuctionService.query(auction))) {
                                    closeFlag = true;
                                }
                            }
                        }
                        map.put("closeFlag", closeFlag);

                        return map;
                    }

                    protected void set(List<Map<String, Object>> d, Map<String, Object> v) {
                        if (v != null)
                            d.add(v);
                    }
                });

            }
            completionService.sync();
            dataGrid.setRows(result);
        }

        return dataGrid;
    }

    public DataGrid dataGridHot(PageHelper ph, ZcProduct zcProduct, final String userId) {
        DataGrid dataGrid = zcProductService.dataGrid(zcProduct, ph);
        List<ZcProduct> products = (List<ZcProduct>) dataGrid.getRows();
        if(!CollectionUtils.isEmpty(products)) {
            final CompletionService completionService = CompletionFactory.initCompletion();
            String[] productIds = new String[products.size()];
            int i = 0;
            for (ZcProduct product : products) {
                productIds[i] = product.getId();
                i++;
                // 获取图片集合
                completionService.submit(new Task<ZcProduct, List<ZcFile>>(product) {
                    @Override
                    public List<ZcFile> call() throws Exception {
                        ZcFile file = new ZcFile();
                        file.setObjectType(EnumConstants.OBJECT_TYPE.PRODUCT.getCode());
                        file.setObjectId(getD().getId());
                        file.setFileType("FT01");
                        return zcFileService.queryFiles(file);
                    }

                    protected void set(ZcProduct d, List<ZcFile> v) {
                        if (v != null)
                            d.setFiles(v);
                    }
                });

                completionService.submit(new Task<ZcProduct, User>(product) {
                    @Override
                    public User call() throws Exception {
                        User user = userService.get(getD().getAddUserId(), userId);
                        return user;
                    }

                    protected void set(ZcProduct d, User v) {
                        if (v != null)
                            d.setUser(v);
                    }
                });
            }
            completionService.sync();

            Map<String, Integer> auctions = zcAuctionService.getCountAuctionNum(productIds);
            for (ZcProduct product : products) {
                Integer num = auctions.get(product.getId());
                if(num != null) product.setAuctionNum(num);
            }
        }

        return dataGrid;
    }
}
