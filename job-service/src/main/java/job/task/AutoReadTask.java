package job.task;

import jb.pageModel.BaseData;
import jb.pageModel.ZcCategory;
import jb.service.BasedataServiceI;
import jb.service.ZcCategoryServiceI;
import jb.service.ZcForumBbsServiceI;
import jb.util.Constants;
import jb.util.DateUtil;
import jb.util.wx.AccessTokenInstance;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by wenming on 2017/5/8.
 */
public class AutoReadTask {

    @Autowired
    private ZcCategoryServiceI zcCategoryService;
    @Autowired
    private ZcForumBbsServiceI zcForumBbsService;
    @Autowired
    private BasedataServiceI basedataService;

    public void work() {
        // 查询所有阅读自增数不等于0的一级分类
        ZcCategory firstQ = new ZcCategory();
        firstQ.setPid("0");
        firstQ.setIsDeleted(false);
        List<ZcCategory> firstCategorys = zcCategoryService.query(firstQ);
        if(CollectionUtils.isNotEmpty(firstCategorys)) {
            int time = 6;
            BaseData bd = basedataService.get("SV300");
            if(bd != null) {
                try {
                    time = Integer.valueOf(bd.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for(ZcCategory firstCategory : firstCategorys) {
                if(firstCategory.getAutoRead() == null || firstCategory.getAutoRead() == 0) continue;
                int count = firstCategory.getAutoRead();
                if(count < 0) {
                    Random random = new Random();
                    count = random.nextInt(-count) + 1;
                }
                ZcCategory childQ = new ZcCategory();
                childQ.setIsDeleted(false);
                childQ.setPid(firstCategory.getId());
                List<ZcCategory> childCategorys = zcCategoryService.query(childQ);
                if(CollectionUtils.isNotEmpty(childCategorys)) {
                    String in = "";
                    for(ZcCategory childCategory : childCategorys) {
                        in += ",'"+childCategory.getId()+"'";
                    }

                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.HOUR_OF_DAY, -time);
                    String where = " where t.isDeleted = 0 and t.bbs_status = 'BS01' and t.category_id in (" + in.substring(1) + ") and date_format(t.addtime, '%Y-%m-%d %H:%i:%s') >= '" + DateUtil.format(cal.getTime(), Constants.DATE_FORMAT) + "'";
                    zcForumBbsService.updateCountByWhere(where, count, "bbs_read");
                }
            }
        }
    }
}
