package job.task;

import jb.pageModel.*;
import jb.service.*;
import jb.service.impl.CompletionFactory;
import jb.service.impl.SendWxMessageImpl;
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
public class IntermediaryTask {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private final static long TIME_1 = 3*24*60*60*1000;

    @Autowired
    private ZcIntermediaryServiceI zcIntermediaryService;

    @Autowired
    private ZcIntermediaryLogServiceI zcIntermediaryLogService;

    @Autowired
    private SendWxMessageImpl sendWxMessage;

    public void work() {
        final CompletionService completionService = CompletionFactory.initCompletion();
        completionService.submit(new Task<Object, Object>(null) {
            @Override
            public Boolean call() throws Exception {
                execute(); // 中介交易三天未处理超时处理
                return true;
            }
        });
    }

    private void execute() {
        ZcIntermediary q = new ZcIntermediary();
        q.setStatus("IS01"); // 待处理
        List<ZcIntermediary> ims = zcIntermediaryService.query(q);
        if(CollectionUtils.isNotEmpty(ims)) {
            final CompletionService completionService = CompletionFactory.initCompletion();
            Date now = new Date();
            for(ZcIntermediary im : ims) {
                // 排除不满72小时
                if(now.getTime() - im.getAddtime().getTime() < TIME_1) continue;
                completionService.submit(new Task<ZcIntermediary, Boolean>(im) {
                    @Override
                    public Boolean call() throws Exception {
                        ZcIntermediary im = new ZcIntermediary();
                        im.setId(getD().getId());
                        im.setStatus("IS03");

                        ZcIntermediaryLog log = new ZcIntermediaryLog();
                        log.setUserId(getD().getSellUserId());
                        log.setLogType("IL04");
                        log.setContent("卖家未处理");
                        log.setIntermediary(im);

                        zcIntermediaryLogService.addAndUpdateIM(log);

                        // 给买家推送拒绝交易通知
                        sendWxMessage.sendIMResultTemplateMessage(im, log.getLogType());

                        return true;
                    }
                });
            }
        }
    }

}
