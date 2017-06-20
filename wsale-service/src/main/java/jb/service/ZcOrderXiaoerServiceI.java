package jb.service;

import jb.pageModel.ZcOrderXiaoer;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcOrderXiaoerServiceI {

	/**
	 * 获取ZcOrderXiaoer数据表格
	 * 
	 * @param zcOrderXiaoer
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcOrderXiaoer zcOrderXiaoer, PageHelper ph);

	/**
	 * 添加ZcOrderXiaoer
	 * 
	 * @param zcOrderXiaoer
	 */
	public void add(ZcOrderXiaoer zcOrderXiaoer);

	/**
	 * 获得ZcOrderXiaoer对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcOrderXiaoer get(String id);

	/**
	 * 修改ZcOrderXiaoer
	 * 
	 * @param zcOrderXiaoer
	 */
	public void edit(ZcOrderXiaoer zcOrderXiaoer);

	/**
	 * 删除ZcOrderXiaoer
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcOrderXiaoer get(ZcOrderXiaoer zcOrderXiaoer);

	public List<ZcOrderXiaoer> query(ZcOrderXiaoer zcOrderXiaoer);

}
