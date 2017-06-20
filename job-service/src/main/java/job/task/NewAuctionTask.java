package job.task;

import com.alibaba.fastjson.JSONObject;
import jb.listener.Application;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcNewHistoryServiceI;
import jb.service.ZcProductServiceI;
import jb.service.ZcShieldorfansServiceI;
import jb.service.impl.CompletionFactory;
import jb.service.impl.ProductCommon;
import jb.util.DateUtil;
import jb.util.EnumConstants;
import jb.util.wx.WeixinUtil;
import jb.util.wx.bean.Article;
import jb.util.wx.bean.ArticleMessage;
import jb.util.wx.bean.News;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import java.util.*;

/**
 * Created by wenming on 2016/9/27.
 */
public class NewAuctionTask {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserServiceI userService;

    @Autowired
    private ZcShieldorfansServiceI zcShieldorfansService;

    @Autowired
    private ProductCommon productCommon;

    @Autowired
    private ZcNewHistoryServiceI zcNewHistoryService;

    @Autowired
    private ZcProductServiceI zcProductService;

    public void work() {
        CompletionService completionService = CompletionFactory.initCompletion();
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if(hour > 9 && hour < 24) {
            completionService.submit(new Task<Object, Object>(null) {
                @Override
                public Boolean call() throws Exception {
                    execute(); // 模拟数据
                    return true;
                }
            });
        }

        completionService.submit(new Task<Object, Object>(null) {
            @Override
            public Boolean call() throws Exception {
                executeReal(); // 实时数据
                return true;
            }
        });
    }

    /**
     * 查询5分钟内发布的拍品，真实推送
     */
    private void executeReal() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -5);
        ZcProduct q = new ZcProduct();
        q.setStartingTime(cal.getTime());
        q.setStatus("PT03");
        q.setIsDeleted(false);
        List<ZcProduct> products = zcProductService.query(q);
        if(CollectionUtils.isNotEmpty(products)) {
            CompletionService completionService = CompletionFactory.initCompletion();
            for(ZcProduct product : products) {
                completionService.submit(new Task<ZcProduct, Object>(product) {
                    @Override
                    public Boolean call() throws Exception {
                        pushMessageReal(getD());
                        return true;
                    }
                });
            }

        }
    }

    private void pushMessageReal(final ZcProduct product) {
        ZcShieldorfans sf = new ZcShieldorfans();
        sf.setObjectType("FS");
        sf.setObjectById(product.getAddUserId());
        List<ZcShieldorfans> attrs = zcShieldorfansService.query(sf);
        if(CollectionUtils.isNotEmpty(attrs)) {
            int page = 1, pageSize = 10;
            List<ZcProduct> products = new ArrayList<ZcProduct>();
            while (true) {
                int count = 0;
                ZcProduct q = new ZcProduct();
                q.setIsDeleted(false);
                q.setAddUserId(product.getAddUserId());
                q.setStatus("PT03");
                PageHelper ph = new PageHelper();
                ph.setPage(page);
                ph.setRows(pageSize);
                ph.setSort("startingTime");
                ph.setOrder("desc");
                List<ZcProduct> temps = (List<ZcProduct>) productCommon.dataGrid(ph, q, null).getRows();
                boolean b = false;
                if (CollectionUtils.isNotEmpty(temps)) {
                    count = temps.size();
                    int len = 0;
                    for (ZcProduct t : temps) {
                        if (products.size() == 7) {
                            b = true;
                            break;
                        }
                        if (len == 3) break;
                        products.add(t);
                        len++;
                    }
                }

                page++;
                if (count < pageSize || b) break;
            }

            if(CollectionUtils.isNotEmpty(products)) {
                final CompletionService completionService = CompletionFactory.initCompletion();
                final List<ZcProduct> pushProducts = products;
                final User u = userService.getByZc(product.getAddUserId());
                for(ZcShieldorfans at : attrs) {
                    completionService.submit(new Task<ZcShieldorfans, Object>(at) {
                        @Override
                        public Boolean call() throws Exception {
                            User user = userService.getByZc(getD().getObjectId());
                            Article article = null;
                            Article[] articles = new Article[pushProducts.size()+1];
                            int len = 0;
                            String productIds = "";
                            Date addtime = null;
                            for(int i=0; i<pushProducts.size(); i++) {
                                ZcProduct p = pushProducts.get(i);
                                String title = p.getContentLine();
                                if(title.length() > 28)
                                    title = title.substring(0, 28) + "...";

                                if(i == 0) {
                                    article = new Article();
                                    article.setTitle("『"+u.getNickname()+"』的新品开拍啦");
                                    article.setDescription("");
                                    article.setPicurl(getIconPath(p.getIcon()));
                                    article.setUrl(getShopUrl(u.getId()));
                                    articles[len] = article;
                                    len ++ ;
                                    addtime = p.getStartingTime();
                                }
                                article = new Article();
                                article.setTitle(title);
                                article.setDescription("");
                                article.setPicurl(getIconPath(p.getIcon()));
                                article.setUrl(getProductUrl(p.getId()));
                                articles[len] = article;
                                len ++ ;
                                productIds += "," + p.getId();
                            }
                            News news = new News();
                            news.setArticles(articles);
                            ArticleMessage am = new ArticleMessage();
                            am.setTouser(user.getName());
                            am.setMsgtype("news");
                            am.setNews(news);
                            WeixinUtil.sendCustomMessage(JSONObject.toJSONString(am));

                            ZcNewHistory zcNewHistory = new ZcNewHistory();
                            zcNewHistory.setOpenid(user.getName());
                            zcNewHistory.setUserId(u.getId());
                            zcNewHistory.setProductIds(productIds.substring(1));
                            zcNewHistory.setAddtime(addtime);
                            completionService.submit(new Task<ZcNewHistory, Object>(zcNewHistory) {
                                @Override
                                public Object call() throws Exception {
                                    zcNewHistoryService.add(getD());
                                    return null;
                                }
                            });
                            return true;
                        }
                    });
                }
            }
        }

    }

    private void execute() {
//        System.out.println("NewAuctionTask start!");
        int page = 1, pageSize = 50;
        PageHelper ph = new PageHelper();
        Random random = new Random();
        while (true) {
            int count = 0;
            User u = new User();
            u.setUtype("UT02");
            ph.setPage(page);
            ph.setRows(pageSize);
            List<User> users = (List<User>) userService.dataGridSimple(u, ph, " and exists (select 1 from TzcShieldorfans f where f.objectId = t.id and f.objectType = 'FS')").getRows();
            if (CollectionUtils.isNotEmpty(users)) {
                count = users.size();
//                System.out.println("users.size:" + users.size() + "-----count:" + count + "------page:" + page);
                ZcShieldorfans q = null;
                for (User user : users) {
//                    System.out.println("用户nickname:" + user.getNickname());
                    if(random.nextInt(100) > 10) continue; // 概率问题
                    q = new ZcShieldorfans();
                    q.setObjectType("FS");
                    q.setObjectId(user.getId());
                    List<ZcShieldorfans> attrs = zcShieldorfansService.query(q);
                    ZcShieldorfans attr = attrs.get(random.nextInt(attrs.size()));
                    pushMessage(user.getName(), attr.getObjectById());
                }
            }
            page++;
            if (count < pageSize) break;
        }
    }



    private void pushMessage(String openid, String userId) {
//        System.out.println("pushMessage start! openid:" + openid + "----userId:" + userId);
        ZcNewHistory exist = new ZcNewHistory();
        exist.setUserId(userId);
        exist.setOpenid(openid);
        exist.setSameDay(true);
        if(CollectionUtils.isNotEmpty(zcNewHistoryService.query(exist)))
            return;

        int page = 1, pageSize = 10;
        List<ZcProduct> products = new ArrayList<ZcProduct>();
        while(true) {
            int count = 0;
            ZcProduct q = new ZcProduct();
            q.setIsDeleted(false);
            q.setAddUserId(userId);
            q.setStatus("PT03");
            PageHelper ph = new PageHelper();
            ph.setPage(page);
            ph.setRows(pageSize);
            ph.setSort("startingTime");
            ph.setOrder("asc");
            List<ZcProduct> temps = (List<ZcProduct>) productCommon.dataGrid(ph, q, null).getRows();
            boolean b = false;
            if(CollectionUtils.isNotEmpty(temps)) {
                count = temps.size();
                int len = 0;
                for(ZcProduct t : temps) {
                    if(products.size() == 7) {
                        b = true;
                        break;
                    }
                    if(len == 3) break;
                    products.add(t);
                    len ++;
                }
            }

            page++;
            if (count < pageSize || b) break;
        }

//        System.out.println("pushMessage start! productsSize:" + products.size());

        if(CollectionUtils.isNotEmpty(products)) {
            User user = userService.getByZc(userId);
            Article article = null;
            Article[] articles = new Article[products.size()+1];
            int len = 0;
            String productIds = "";
            for(int i=0; i<products.size(); i++) {
                ZcProduct product = products.get(i);
                String title = product.getContentLine();
                if(title.length() > 28)
                    title = title.substring(0, 28) + "...";

                if(i == 0) {
                    article = new Article();
                    article.setTitle("『"+user.getNickname()+"』的新品开拍啦");
                    article.setDescription("");
                    article.setPicurl(getIconPath(product.getIcon()));
                    article.setUrl(getShopUrl(userId));
                    articles[len] = article;
                    len ++ ;
                }
                article = new Article();
                article.setTitle(title);
                article.setDescription("");
                article.setPicurl(getIconPath(product.getIcon()));
                article.setUrl(getProductUrl(product.getId()));
                articles[len] = article;
                len ++ ;
                productIds += "," + product.getId();
            }
            News news = new News();
            news.setArticles(articles);
            ArticleMessage am = new ArticleMessage();
            am.setTouser(openid);
            am.setMsgtype("news");
            am.setNews(news);
            WeixinUtil.sendCustomMessage(JSONObject.toJSONString(am));

            final CompletionService completionService = CompletionFactory.initCompletion();
            ZcNewHistory zcNewHistory = new ZcNewHistory();
            zcNewHistory.setOpenid(openid);
            zcNewHistory.setUserId(userId);
            zcNewHistory.setProductIds(productIds.substring(1));
            completionService.submit(new Task<ZcNewHistory, Object>(zcNewHistory) {
                @Override
                public Object call() throws Exception {
                    zcNewHistoryService.add(getD());
                    return null;
                }
            });

//            System.out.println("pushMessage success!");
        }
    }

    private String getIconPath(String path) {
//        return Application.getString("SV100") + path;
        return path;
    }

    private String getShopUrl(String userId) {
        return Application.getString("SV100") + "api/apiShop/shop?userId=" + userId;
    }

    private String getProductUrl(String productId) {
        return Application.getString("SV100") + "api/apiProductController/productDetail?id=" + productId;
    }
}
