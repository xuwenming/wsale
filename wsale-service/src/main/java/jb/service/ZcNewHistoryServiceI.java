package jb.service;

import jb.pageModel.ZcChatMsg;
import jb.pageModel.ZcNewHistory;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcNewHistoryServiceI {

	/**
	 * 获取ZcNewHistory数据表格
	 * 
	 * @param zcNewHistory
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcNewHistory zcNewHistory, PageHelper ph);

	/**
	 * 添加ZcNewHistory
	 * 
	 * @param zcNewHistory
	 */
	public void add(ZcNewHistory zcNewHistory);

	/**
	 * 获得ZcNewHistory对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcNewHistory get(String id);

	/**
	 * 修改ZcNewHistory
	 * 
	 * @param zcNewHistory
	 */
	public void edit(ZcNewHistory zcNewHistory);

	/**
	 * 删除ZcNewHistory
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcNewHistory get(ZcNewHistory zcNewHistory);

	public List<ZcNewHistory> query(ZcNewHistory zcNewHistory);


	void updateReaded(String openid);

	public int count(ZcNewHistory zcNewHistory);
}
