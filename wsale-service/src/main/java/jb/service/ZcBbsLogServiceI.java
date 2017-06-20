package jb.service;

import jb.pageModel.ZcBbsLog;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcBbsLogServiceI {

	/**
	 * 获取ZcBbsLog数据表格
	 * 
	 * @param zcBbsLog
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcBbsLog zcBbsLog, PageHelper ph);

	/**
	 * 添加ZcBbsLog
	 * 
	 * @param zcBbsLog
	 */
	public void add(ZcBbsLog zcBbsLog);

	/**
	 * 获得ZcBbsLog对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcBbsLog get(String id);

	/**
	 * 修改ZcBbsLog
	 * 
	 * @param zcBbsLog
	 */
	public void edit(ZcBbsLog zcBbsLog);

	/**
	 * 删除ZcBbsLog
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcBbsLog get(ZcBbsLog zcBbsLog);

	public List<ZcBbsLog> query(ZcBbsLog zcBbsLog);

}
