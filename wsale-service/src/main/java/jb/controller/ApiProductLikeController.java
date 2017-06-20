package jb.controller;

import jb.pageModel.*;
import jb.service.ZcAuctionServiceI;
import jb.service.ZcProductLikeServiceI;
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
 *TODO  Created by james on 2016/8/26 0026.
 */
@Controller
@RequestMapping("/api/apiProductLikeController")
public class ApiProductLikeController extends BaseController {

    @Autowired
    private ZcProductLikeServiceI zcProductLikeServiceI;

    @Autowired
    private ZcProductServiceI zcProductService;
    /**
     * 点赞拍品列表（james）
     * http://localhost:8080/api/apiProductLikeController/productLikeList?tokenId=1D96DACB84F21890ED9F4928FA8B352B&page=1&rows=10
     * @return
     */
    @RequestMapping("/productLikeList")
    @ResponseBody
    public Json productLikeList(PageHelper ph,ZcProductLike zcProductLike, HttpServletRequest request) {
        Json j = new Json();
        try{
            SessionInfo s = getSessionInfo(request);
            ph.setSort("addtime");
            ph.setOrder("desc");
            zcProductLike.setUserId(s.getId());
            DataGrid dataGrid =zcProductLikeServiceI.dataGrid(zcProductLike, ph);
            List<ZcProductLike> zc = (List<ZcProductLike>) dataGrid.getRows();
            if(!CollectionUtils.isEmpty(zc)) {
                final CompletionService completionService = CompletionFactory.initCompletion();
                final String userId = s.getId();
                for (ZcProductLike zcProductLike1 : zc) {
                    completionService.submit(new Task<ZcProductLike, ZcProduct>(zcProductLike1) {
                        @Override
                        public ZcProduct call() throws Exception {
                            ZcProduct zc = zcProductService.get(getD().getProductId(), userId);
                            return zc;
                        }

                        protected void set(ZcProductLike d, ZcProduct v) {
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
