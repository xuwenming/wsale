package job.task;

import jb.service.UserServiceI;
import jb.service.impl.CompletionFactory;
import jb.service.impl.SendWxMessageImpl;
import jb.util.Constants;
import jb.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/16.
 */
public class PositionExpiredTask {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserServiceI userService;

    @Autowired
    private SendWxMessageImpl sendWxMessage;

    public void work() {
        final CompletionService completionService = CompletionFactory.initCompletion();
        completionService.submit(new Task<Object, Object>(null) {
            @Override
            public Boolean call() throws Exception {
                expiredRemind(30, "zscj"); // 资深藏家30天到期提醒
                return true;
            }
        });
        completionService.submit(new Task<Object, Object>(null) {
            @Override
            public Boolean call() throws Exception {
                expiredRemind(10, "zscj"); // 资深藏家10天到期提醒
                return true;
            }
        });
        completionService.submit(new Task<Object, Object>(null) {
            @Override
            public Boolean call() throws Exception {
                expiredRemind(5, "zscj"); // 资深藏家5天到期提醒
                return true;
            }
        });

        completionService.submit(new Task<Object, Object>(null) {
            @Override
            public Boolean call() throws Exception {
                expiredRemind(15, "zsjs"); // 资深讲师15天到期提醒
                return true;
            }
        });
        completionService.submit(new Task<Object, Object>(null) {
            @Override
            public Boolean call() throws Exception {
                expiredRemind(10, "zsjs"); // 资深讲师10天到期提醒
                return true;
            }
        });
        completionService.submit(new Task<Object, Object>(null) {
            @Override
            public Boolean call() throws Exception {
                expiredRemind(5, "zsjs"); // 资深讲师5天到期提醒
                return true;
            }
        });
    }

    private void expiredRemind(int d, String roleId) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, d);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("roleId", roleId);
        params.put("expireTime", DateUtil.format(cal.getTime(), Constants.DATE_FORMAT_YMD));
        List<String> userIds = userService.queryExpireUserIds(params);
        for(String userId : userIds) {
            StringBuffer buffer = new StringBuffer();
            if("zscj".equals(roleId)) {
                buffer.append("您好，您的资深藏家身份，距离" + DateUtil.format(cal.getTime(), "yyyy年MM月dd日") + "到期还有" + d + "天。");
            } else if("zsjs".equals(roleId)) {
                buffer.append("您好，您的资深讲师身份，距离" + DateUtil.format(cal.getTime(), "yyyy年MM月dd日") + "到期还有" + d + "天。");
            }
            sendWxMessage.sendCustomMessageByUserId(userId, buffer.toString());
        }
    }
}
