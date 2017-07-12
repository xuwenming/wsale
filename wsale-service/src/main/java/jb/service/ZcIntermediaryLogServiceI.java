package jb.service;

import jb.pageModel.ZcIntermediaryLog;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcIntermediaryLogServiceI {

	/**
	 * 获取ZcIntermediaryLog数据表格
	 * 
	 * @param zcIntermediaryLog
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcIntermediaryLog zcIntermediaryLog, PageHelper ph);

	/**
	 * 添加ZcIntermediaryLog
	 * 
	 * @param zcIntermediaryLog
	 */
	public void add(ZcIntermediaryLog zcIntermediaryLog);

	/**
	 * 获得ZcIntermediaryLog对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcIntermediaryLog get(String id);

	/**
	 * 修改ZcIntermediaryLog
	 * 
	 * @param zcIntermediaryLog
	 */
	public void edit(ZcIntermediaryLog zcIntermediaryLog);

	/**
	 * 删除ZcIntermediaryLog
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcIntermediaryLog get(ZcIntermediaryLog zcIntermediaryLog);

	public List<ZcIntermediaryLog> query(ZcIntermediaryLog zcIntermediaryLog);

}
