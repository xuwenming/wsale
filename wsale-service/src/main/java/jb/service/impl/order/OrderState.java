package jb.service.impl.order;

import jb.pageModel.ZcOrder;

/**
 * Created by john on 16/10/30.
 */
public interface OrderState {

    String prefix = "OS";
    /**
     * 获取状态
     * @return
     */
    String getStateName();

    /**
     * 某状态下处理操作
     * @param zcOrder
     */
    void handle(ZcOrder zcOrder);

    /**
     * 下一个状态
     * @return
     */
    OrderState next(ZcOrder zcOrder);
}
