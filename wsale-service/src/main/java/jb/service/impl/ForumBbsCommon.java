package jb.service.impl;

import jb.absx.F;
import jb.pageModel.*;
import jb.service.*;
import jb.util.EnumConstants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wsale.concurrent.CacheKey;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import java.util.Date;
import java.util.List;

/**
 * Created by wenming on 2016/8/25.
 */
@Service
public class ForumBbsCommon {
    @Autowired
    private UserServiceI userService;

    @Autowired
    private ZcForumBbsServiceI zcForumBbsService;

    @Autowired
    private ZcFileServiceI zcFileService;

    public DataGrid dataGrid(PageHelper ph, ZcForumBbs bbs) {
        DataGrid dataGrid = null;
        if(F.empty(bbs.getAtteId())) {
            dataGrid = zcForumBbsService.dataGrid(bbs, ph);
        } else {
            dataGrid = zcForumBbsService.dataGridComplex(bbs, ph);
        }

        List<ZcForumBbs> list = (List<ZcForumBbs>) dataGrid.getRows();
        if(!CollectionUtils.isEmpty(list)) {
            final CompletionService completionService = CompletionFactory.initCompletion();
            for(ZcForumBbs forumBbs : list) {
                completionService.submit(new Task<ZcForumBbs, User>(new CacheKey("user", forumBbs.getAddUserId()), forumBbs) {
                    @Override
                    public User call() throws Exception {
                        User user = userService.get(getD().getAddUserId(), true);
                        return user;
                    }

                    protected void set(ZcForumBbs d, User v) {
                        if (v != null)
                            d.setAddUserName(v.getNickname());
                    }

                });

                completionService.submit(new Task<ZcForumBbs, String>(forumBbs) {
                    @Override
                    public String call() throws Exception {
                        ZcFile file = new ZcFile();
                        file.setObjectType(EnumConstants.OBJECT_TYPE.BBS.getCode());
                        file.setObjectId(getD().getId());
                        file.setFileType("FT01");
                        ZcFile f = zcFileService.get(file);
                        return f == null ? null : f.getFileHandleUrl();
                    }

                    protected void set(ZcForumBbs d, String v) {
                        if (v != null)
                            d.setIcon(v);
                    }

                });
            }
            completionService.sync();
        }

        return dataGrid;
    }
}
