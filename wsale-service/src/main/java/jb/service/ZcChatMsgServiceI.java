package jb.service;

import jb.pageModel.ZcChatMsg;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcChatMsgServiceI {

	/**
	 * 获取ZcChatMsg数据表格
	 * 
	 * @param zcChatMsg
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcChatMsg zcChatMsg, PageHelper ph);

	DataGrid dataGridBy(ZcChatMsg msg, PageHelper ph);

	/**
	 * 添加ZcChatMsg
	 * 
	 * @param zcChatMsg
	 */
	public void add(ZcChatMsg zcChatMsg);

	/**
	 * 获得ZcChatMsg对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcChatMsg get(String id);

	/**
	 * 修改ZcChatMsg
	 * 
	 * @param zcChatMsg
	 */
	public void edit(ZcChatMsg zcChatMsg);

	/**
	 * 删除ZcChatMsg
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcChatMsg get(ZcChatMsg zcChatMsg);

	public List<ZcChatMsg> query(ZcChatMsg zcChatMsg);
	public int count(ZcChatMsg zcChatMsg);

	/**
	 * 把fromUserId发送给toUserId的消息设置为已读
	 * @param fromUserId
	 * @param toUserId
	 */
	void updateReaded(String fromUserId, String toUserId);

	DataGrid dataGridComplex(String userId, PageHelper ph);

}
