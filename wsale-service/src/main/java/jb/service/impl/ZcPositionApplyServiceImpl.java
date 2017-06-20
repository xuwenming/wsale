package jb.service.impl;

import jb.absx.F;
import jb.dao.UserDaoI;
import jb.dao.ZcPositionApplyDaoI;
import jb.listener.Application;
import jb.model.Tuser;
import jb.model.TzcPositionApply;
import jb.pageModel.*;
import jb.service.UserServiceI;
import jb.service.ZcPositionApplyServiceI;
import jb.util.Constants;
import jb.util.DateUtil;
import jb.util.MyBeanUtils;
import jb.util.PathUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wsale.concurrent.CacheKey;
import wsale.concurrent.CompletionService;
import wsale.concurrent.Task;

import java.math.BigInteger;
import java.util.*;

@Service
public class ZcPositionApplyServiceImpl extends BaseServiceImpl<ZcPositionApply> implements ZcPositionApplyServiceI {

	@Autowired
	private ZcPositionApplyDaoI zcPositionApplyDao;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private SendWxMessageImpl sendWxMessage;

	@Override
	public DataGrid dataGrid(ZcPositionApply zcPositionApply, PageHelper ph) {
		List<ZcPositionApply> ol = new ArrayList<ZcPositionApply>();
		String hql = " from TzcPositionApply t ";
		DataGrid dg = dataGridQuery(hql, ph, zcPositionApply, zcPositionApplyDao);
		@SuppressWarnings("unchecked")
		List<TzcPositionApply> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TzcPositionApply t : l) {
				ZcPositionApply o = new ZcPositionApply();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}

	@Override
	public DataGrid dataGridComplex(ZcPositionApply zcPositionApply, PageHelper ph) {
		DataGrid dg = new DataGrid();
		String sql = "select t.id, t.audit_status auditStatus, t.audit_time auditTime, t.audit_remark auditRemark, "
				+ " t.addtime addtime, t.pay_status payStatus, t.paytime paytime, c.name categoryName, "
				+ " r.name roleName, u1.nickname applyUserName, u2.nickname auditUserName ";
		String from	= " from zc_position_apply t "
				+ " left join zc_category c on c.id = t.category_id "
				+ " left join trole r on r.id = t.role_id "
				+ " left join tuser u1 on u1.id = t.apply_user_id "
				+ " left join tuser u2 on u2.id = t.audit_user_id ";
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(zcPositionApply, params);
		List<Map> l = zcPositionApplyDao.findBySql2Map(sql + from +  where + orderHql(ph), params, ph.getPage(), ph.getRows());
		for(Map m : l) {
			//org.apache.commons.beanutils.BeanUtils.populate(r, m);
			m.put("auditStatusZh", Application.getString((String) m.get("auditStatus")));
			m.put("payStatusZh", Application.getString((String)m.get("payStatus")));
		}
		dg.setRows(l);
		BigInteger count = zcPositionApplyDao.countBySql("select count(*) " + from + where, params);
		dg.setTotal(count == null ? 0 : count.longValue());
		return dg;
	}

	protected String whereHql(ZcPositionApply zcPositionApply, Map<String, Object> params) {
		String whereHql = "";	
		if (zcPositionApply != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(zcPositionApply.getAuditStatus())) {
				whereHql += " and t.auditStatus = :auditStatus";
				params.put("auditStatus", zcPositionApply.getAuditStatus());
			}
			if (!F.empty(zcPositionApply.getPayStatus())) {
				whereHql += " and t.payStatus = :payStatus";
				params.put("payStatus", zcPositionApply.getPayStatus());
			}
			if (!F.empty(zcPositionApply.getCategoryId())) {
				whereHql += " and t.categoryId = :categoryId";
				params.put("categoryId", zcPositionApply.getCategoryId());
			}
			if (!F.empty(zcPositionApply.getRoleId())) {
				whereHql += " and t.roleId = :roleId";
				params.put("roleId", zcPositionApply.getRoleId());
			}
			if (!F.empty(zcPositionApply.getApplyUserId())) {
				whereHql += " and t.applyUserId = :applyUserId";
				params.put("applyUserId", zcPositionApply.getApplyUserId());
			}
			/*if (!F.empty(zcPositionApply.getCategoryName())) {
				whereHql += " and c.name = :categoryName";
				params.put("categoryName", "%%" + zcPositionApply.getCategoryName() + "%%");
			}
			if (!F.empty(zcPositionApply.getRoleName())) {
				whereHql += " and r.name = :roleName";
				params.put("roleName", "%%" + zcPositionApply.getRoleName() + "%%");
			}
			if (!F.empty(zcPositionApply.getApplyUserName())) {
				whereHql += " and u1.nickname = :applyUserName";
				params.put("applyUserName", "%%" + zcPositionApply.getApplyUserName() + "%%");
			}*/
		}	
		return whereHql;
	}

	@Override
	public void add(ZcPositionApply zcPositionApply) {
		zcPositionApply.setId(jb.absx.UUID.uuid());
		TzcPositionApply t = new TzcPositionApply();
		BeanUtils.copyProperties(zcPositionApply, t);
		//t.setCreatedatetime(new Date());
		zcPositionApplyDao.save(t);
	}

	@Override
	public ZcPositionApply get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TzcPositionApply t = zcPositionApplyDao.get("from TzcPositionApply t  where t.id = :id", params);
		ZcPositionApply o = new ZcPositionApply();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ZcPositionApply zcPositionApply) {
		TzcPositionApply t = zcPositionApplyDao.get(TzcPositionApply.class, zcPositionApply.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(zcPositionApply, t, new String[] { "id" , "createdatetime" },true);
			zcPositionApply.setCategoryId(t.getCategoryId());
			zcPositionApply.setApplyUserId(t.getApplyUserId());
			zcPositionApply.setRoleId(t.getRoleId());
			//t.setModifydatetime(new Date());
		}
	}

	/**
	 * 审核
	 * @param zcPositionApply
	 */
	public void editAudit(ZcPositionApply zcPositionApply) {
		zcPositionApply.setAuditTime(new Date());
		edit(zcPositionApply);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", zcPositionApply.getApplyUserId());
		params.put("roleId", zcPositionApply.getRoleId());
		zcPositionApplyDao.executeSql("delete from tuser_trole where TUSER_ID = :userId and TROLE_ID = :roleId", params);
		// 审核通过
		if("AS02".equals(zcPositionApply.getAuditStatus())) {
			params.put("categoryId", zcPositionApply.getCategoryId());
			StringBuffer buffer = new StringBuffer();
			//BigInteger count = zcPositionApplyDao.countBySql("select count(*) from tuser_trole t where t.TUSER_ID = :userId and t.TROLE_ID = :roleId and t.category_id = :categoryId", params);
			//if(count == null || count.intValue() == 0)  {
				params.put("valid_start_time", null);
				params.put("valid_end_time", null);
				Calendar c = Calendar.getInstance();
				if("zsjs".equals(zcPositionApply.getRoleId())) {
					params.put("valid_start_time", c.getTime());
					String rd03 = Application.getString("RD03");
					c.add(Calendar.MONTH, rd03 == null ? 3 : Integer.valueOf(rd03));
					params.put("valid_end_time", c.getTime());

					buffer.append("恭喜您成为资深讲师，拥有了更多特权，快去讲堂发声音吧。").append("\n\n");
					buffer.append("有效期：3个月").append("\n");
					buffer.append("到期日期：" + DateUtil.format(c.getTime(), Constants.DATE_FORMAT_YMD)).append("\n\n");
					buffer.append("查看<a href='https://mp.weixin.qq.com/s/q65CxNiEB7kirteEFJcRTQ'>查看《讲堂使用手册》</a>");
				} else if("zscj".equals(zcPositionApply.getRoleId())) {
					params.put("valid_start_time", c.getTime());
					String rd12 = Application.getString("RD12");
					c.add(Calendar.MONTH, rd12 == null ? 12 : Integer.valueOf(rd12));
					params.put("valid_end_time", c.getTime());

					buffer.append("恭喜您成为资深藏家，拥有了更多特权。").append("\n\n");
					//buffer.append("有效期：1年").append("\n");
					//buffer.append("到期日期：" + DateUtil.format(c.getTime(), Constants.DATE_FORMAT_YMD)).append("\n\n");
					buffer.append("查看<a href='https://mp.weixin.qq.com/s/Zl0EJWYK4q8sVgtcuTNxzw'>“什么是资深藏家？”</a>");
				} else if("bz".equals(zcPositionApply.getRoleId())) {
					buffer.append("恭喜您成为集东集西的版主，尊敬的版主我们非常期待与你一起愉快的合作，同时欢迎您为网站建设谏言献策。").append("\n\n");
					buffer.append("查看<a href='https://mp.weixin.qq.com/s/v4rwWPI680QO2wh-kaM6qw'>《版主、贵宾使用手册》</a>");
				} else if("gb".equals(zcPositionApply.getRoleId())) {
					buffer.append("恭喜您成为集东集西的特邀贵宾，我们非常期待与您一起愉快的合作，同时欢迎您为网站建设谏言献策。").append("\n\n");
					buffer.append("查看<a href='https://mp.weixin.qq.com/s/v4rwWPI680QO2wh-kaM6qw'>《版主、贵宾使用手册》</a>");
				}
				zcPositionApplyDao.executeSql("insert into tuser_trole (TUSER_ID, TROLE_ID, category_id, valid_start_time, valid_end_time) values (:userId, :roleId, :categoryId, :valid_start_time, :valid_end_time)", params);
			//}

			// 推送职务通知
			if(!F.empty(buffer.toString())) {
				sendWxMessage.sendCustomMessageByUserId(zcPositionApply.getApplyUserId(), buffer.toString());
			}
		} else if("AS03".equals(zcPositionApply.getAuditStatus())) {
			StringBuffer buffer = new StringBuffer();
			if("zsjs".equals(zcPositionApply.getRoleId())) {
				buffer.append("您申请的资深讲师被退回，如有疑问请联系集东集西客服。").append("\n\n");
			} else if("zscj".equals(zcPositionApply.getRoleId())) {
				buffer.append("您申请的资深藏家被退回，如有疑问请联系集东集西客服。").append("\n\n");
			} else if("bz".equals(zcPositionApply.getRoleId())) {
				buffer.append("您申请的版主被退回，如有疑问请联系集东集西客服。").append("\n\n");
			} else if("gb".equals(zcPositionApply.getRoleId())) {
				buffer.append("您申请的贵宾被退回，如有疑问请联系集东集西客服。").append("\n\n");
			}
			// 推送职务通知
			if(!F.empty(buffer.toString())) {
				buffer.append("理由：").append(zcPositionApply.getAuditRemark());
				sendWxMessage.sendCustomMessageByUserId(zcPositionApply.getApplyUserId(), buffer.toString());
			}
		}
	}

	@Override
	public void delete(String id) {
		zcPositionApplyDao.delete(zcPositionApplyDao.get(TzcPositionApply.class, id));
	}

	@Override
	public List<User> getAllModerators(String categoryId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("categoryId", categoryId);
		params.put("roleId", "bz"); // 版主角色
//		params.put("auditStatus", "AS02"); // 审核通过
		List<Map> l = zcPositionApplyDao.findBySql2Map("select TUSER_ID userId from tuser_trole where category_id = :categoryId and TROLE_ID = :roleId order by id asc", params);
		List<User> users = new ArrayList<User>();
		if(CollectionUtils.isNotEmpty(l)) {
			final CompletionService completionService = CompletionFactory.initCompletion();
			for(Map m : l) {
				final String userId = (String) m.get("userId");
				completionService.submit(new Task<List<User>, User>(users) {
					@Override
					public User call() throws Exception {
						User user = userService.getByZc(userId);
						//getD().add(user);
						return user;
					}

					protected void set(List<User> d, User v) {
						if (v != null)
							d.add(v);
					}

				});
			}
			completionService.sync();
		}

		//List<Tuser> users = userDao.find("select u from Tuser u, TzcPositionApply t where u.id = t.applyUserId and t.categoryId = :categoryId and t.roleId = :roleId and t.auditStatus = :auditStatus order by t.addtime asc", params);
		return users;
	}

	private List<User> convert(List<Tuser> users){
		List<User> list = new ArrayList<User>();
		if(CollectionUtils.isEmpty(users)) return list;

		for(Tuser s : users){
			User o = new User();
			MyBeanUtils.copyProperties(s, o, null, new String[] { "id" , "name", "nickname", "headImage"}, true );
			list.add(o);
		}
		return list;
	}

	@Override
	public List<ZcPositionApply> query(ZcPositionApply zcPositionApply) {
		List<ZcPositionApply> ol = new ArrayList<ZcPositionApply>();
		String hql = " from TzcPositionApply t ";
		@SuppressWarnings("unchecked")
		List<TzcPositionApply> l = query(hql, zcPositionApply, zcPositionApplyDao, "addtime", "desc");
		if (l != null && l.size() > 0) {
			for (TzcPositionApply t : l) {
				ZcPositionApply o = new ZcPositionApply();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public ZcPositionApply get(ZcPositionApply zcPositionApply) {
		String hql = " from TzcPositionApply t ";
		@SuppressWarnings("unchecked")
		List<TzcPositionApply> l = query(hql, zcPositionApply, zcPositionApplyDao, "addtime", "desc");
		ZcPositionApply o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new ZcPositionApply();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
