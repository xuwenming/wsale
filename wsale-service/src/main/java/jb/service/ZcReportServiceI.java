package jb.service;

import jb.pageModel.ZcReport;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcReportServiceI {

	/**
	 * 获取ZcReport数据表格
	 * 
	 * @param zcReport
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcReport zcReport, PageHelper ph);

	/**
	 * 添加ZcReport
	 * 
	 * @param zcReport
	 */
	public void add(ZcReport zcReport);

	/**
	 * 获得ZcReport对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcReport get(String id);

	/**
	 * 修改ZcReport
	 * 
	 * @param zcReport
	 */
	public void edit(ZcReport zcReport);

	/**
	 * 删除ZcReport
	 * 
	 * @param id
	 */
	public void delete(String id);

	List<ZcReport> query(ZcReport r);
}
