package jb.service;

import jb.pageModel.ZcLastViewLog;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcLastViewLogServiceI {

	/**
	 * 获取ZcLastViewLog数据表格
	 * 
	 * @param zcLastViewLog
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcLastViewLog zcLastViewLog, PageHelper ph);

	/**
	 * 添加ZcLastViewLog
	 * 
	 * @param zcLastViewLog
	 */
	public void add(ZcLastViewLog zcLastViewLog);

	/**
	 * 获得ZcLastViewLog对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcLastViewLog get(String id);

	/**
	 * 修改ZcLastViewLog
	 * 
	 * @param zcLastViewLog
	 */
	public void edit(ZcLastViewLog zcLastViewLog);

	/**
	 * 删除ZcLastViewLog
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcLastViewLog get(ZcLastViewLog zcLastViewLog);

	public List<ZcLastViewLog> query(ZcLastViewLog zcLastViewLog);

}
