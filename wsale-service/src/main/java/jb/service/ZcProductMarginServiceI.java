package jb.service;

import jb.pageModel.ZcProductMargin;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcProductMarginServiceI {

	/**
	 * 获取ZcProductMargin数据表格
	 * 
	 * @param zcProductMargin
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcProductMargin zcProductMargin, PageHelper ph);

	/**
	 * 添加ZcProductMargin
	 * 
	 * @param zcProductMargin
	 */
	public void add(ZcProductMargin zcProductMargin);

	/**
	 * 获得ZcProductMargin对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcProductMargin get(String id);

	/**
	 * 修改ZcProductMargin
	 * 
	 * @param zcProductMargin
	 */
	public void edit(ZcProductMargin zcProductMargin);

	/**
	 * 删除ZcProductMargin
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcProductMargin get(ZcProductMargin zcProductMargin);

	public List<ZcProductMargin> query(ZcProductMargin zcProductMargin);

}
