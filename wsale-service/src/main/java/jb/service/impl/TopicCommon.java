package jb.service.impl;

import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.User;
import jb.pageModel.ZcTopic;
import jb.service.UserServiceI;
import jb.service.ZcTopicServiceI;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wsale.concurrent.CacheKey;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import java.util.List;

/**
 * Created by wenming on 2016/8/25.
 */
@Service
public class TopicCommon {
    @Autowired
    private UserServiceI userService;

    @Autowired
    private ZcTopicServiceI zcTopicService;

    public DataGrid dataGrid(PageHelper ph, ZcTopic zcTopic) {
        DataGrid dataGrid = zcTopicService.dataGrid(zcTopic, ph);

        List<ZcTopic> list = (List<ZcTopic>) dataGrid.getRows();
        if(!CollectionUtils.isEmpty(list)) {
            final CompletionService completionService = CompletionFactory.initCompletion();
            for(ZcTopic topic : list) {
                completionService.submit(new Task<ZcTopic, User>(new CacheKey("user", topic.getAddUserId()), topic) {
                    @Override
                    public User call() throws Exception {
                        User user = userService.getByZc(getD().getAddUserId());
                        return user;
                    }

                    protected void set(ZcTopic d, User v) {
                        if (v != null)
                            d.setUser(v);
                    }
                });
            }
            completionService.sync();
        }

        return dataGrid;
    }
}
