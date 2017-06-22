package jb.service;

import jb.pageModel.ZcPraise;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcPraiseServiceI {

	/**
	 * 获取ZcPraise数据表格
	 * 
	 * @param zcPraise
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcPraise zcPraise, PageHelper ph);

	/**
	 * 添加ZcPraise
	 * 
	 * @param zcPraise
	 */
	public void add(ZcPraise zcPraise);

	/**
	 * 获得ZcPraise对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcPraise get(String id);

	/**
	 * 修改ZcPraise
	 * 
	 * @param zcPraise
	 */
	public void edit(ZcPraise zcPraise);

	/**
	 * 删除ZcPraise
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcPraise get(ZcPraise zcPraise);

	public List<ZcPraise> query(ZcPraise zcPraise);

}
