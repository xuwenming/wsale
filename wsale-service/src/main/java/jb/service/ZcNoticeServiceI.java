package jb.service;

import jb.pageModel.ZcNotice;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcNoticeServiceI {

	/**
	 * 获取ZcNotice数据表格
	 * 
	 * @param zcNotice
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcNotice zcNotice, PageHelper ph);

	/**
	 * 添加ZcNotice
	 * 
	 * @param zcNotice
	 */
	public void add(ZcNotice zcNotice);

	/**
	 * 获得ZcNotice对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcNotice get(String id);

	/**
	 * 修改ZcNotice
	 * 
	 * @param zcNotice
	 */
	public void edit(ZcNotice zcNotice);

	/**
	 * 删除ZcNotice
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcNotice get(ZcNotice zcNotice);

	public List<ZcNotice> query(ZcNotice zcNotice);

	int getUnreadCount(String userId);
}
