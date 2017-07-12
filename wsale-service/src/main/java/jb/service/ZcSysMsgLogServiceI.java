package jb.service;

import jb.pageModel.ZcSysMsgLog;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcSysMsgLogServiceI {

	/**
	 * 获取ZcSysMsgLog数据表格
	 * 
	 * @param zcSysMsgLog
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcSysMsgLog zcSysMsgLog, PageHelper ph);

	/**
	 * 添加ZcSysMsgLog
	 * 
	 * @param zcSysMsgLog
	 */
	public void add(ZcSysMsgLog zcSysMsgLog);

	/**
	 * 获得ZcSysMsgLog对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcSysMsgLog get(String id);

	/**
	 * 修改ZcSysMsgLog
	 * 
	 * @param zcSysMsgLog
	 */
	public void edit(ZcSysMsgLog zcSysMsgLog);

	/**
	 * 删除ZcSysMsgLog
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcSysMsgLog get(ZcSysMsgLog zcSysMsgLog);

	public List<ZcSysMsgLog> query(ZcSysMsgLog zcSysMsgLog);

}
