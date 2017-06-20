package jb.service;

import jb.pageModel.ZcChatFriend;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.ZcChatMsg;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcChatFriendServiceI {

	/**
	 * 获取ZcChatFriend数据表格
	 * 
	 * @param zcChatFriend
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcChatFriend zcChatFriend, PageHelper ph);

	/**
	 * 添加ZcChatFriend
	 * 
	 * @param zcChatFriend
	 */
	public void add(ZcChatFriend zcChatFriend);

	public void addOrUpdate(ZcChatFriend zcChatFriend);

	/**
	 * 获得ZcChatFriend对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcChatFriend get(String id);

	/**
	 * 修改ZcChatFriend
	 * 
	 * @param zcChatFriend
	 */
	public void edit(ZcChatFriend zcChatFriend);

	/**
	 * 删除ZcChatFriend
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcChatFriend get(ZcChatFriend zcChatFriend);

	public List<ZcChatFriend> query(ZcChatFriend zcChatFriend);

	void updateFriend(ZcChatMsg msg);
}
