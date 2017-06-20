package jb.controller;

import jb.absx.F;
import jb.pageModel.*;
import jb.service.ZcAuctionServiceI;
import jb.service.ZcProductServiceI;
import jb.service.impl.CompletionFactory;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * TODO Created by james on 2016/8/26 0026.
 */
@Controller
@RequestMapping("/api/apiAuctionController")
public class ApiAuctionController extends BaseController {

    @Autowired
    private ZcAuctionServiceI zcAuctionService;

    @Autowired
    private ZcProductServiceI zcProductService;
    /**
     * 参品拍品列表（james）
     * http://localhost:8080/api/apiAuctionController/auctionProductList?tokenId=1D96DACB84F21890ED9F4928FA8B352B&page=1&rows=10
     * @return
     */
    @RequestMapping("/auctionProductList")
    @ResponseBody
    public Json auctionProductList(PageHelper ph,ZcAuction zcAuction, HttpServletRequest request, String userId) {
        Json j = new Json();
        try{
            ph.setSort("addtime");
            ph.setOrder("desc");
            SessionInfo s = getSessionInfo(request);
            zcAuction.setBuyerId(F.empty(userId) ? s.getId() : userId);
            DataGrid dataGrid = zcAuctionService.dataGridComplet(zcAuction, ph);
            List<ZcAuction> zc = (List<ZcAuction>) dataGrid.getRows();
            if(!CollectionUtils.isEmpty(zc)) {
                final CompletionService completionService = CompletionFactory.initCompletion();
                final String curUserId = s.getId();
                for (ZcAuction a : zc) {
                    completionService.submit(new Task<ZcAuction, ZcProduct>(a) {
                        @Override
                        public ZcProduct call() throws Exception {
                            ZcProduct zc = zcProductService.get(getD().getProductId(), curUserId);
                            return zc;
                        }

                        protected void set(ZcAuction d, ZcProduct v) {
                            if (v != null)
                                d.setZcProduct(v);

                        }
                    });

                }
                completionService.sync();
            }
            j.setObj(dataGrid);
            j.success();
            j.setMsg("操作成功");

        }catch(Exception e){
            j.fail();
            e.printStackTrace();
        }
        return j;
    }


}
