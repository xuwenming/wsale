package job.task;

import jb.pageModel.ZcOrder;
import jb.service.UserServiceI;
import jb.service.ZcOrderServiceI;
import jb.service.impl.CompletionFactory;
import jb.service.impl.order.OrderState;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by wenming on 2016/9/27.
 */
public class UserRoleTask {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserServiceI userService;

    public void work() {
        // 删除所有的过期用户角色数据
        userService.deleteExpireUserRole();
    }

}
