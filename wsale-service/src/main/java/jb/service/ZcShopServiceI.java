package jb.service;

import jb.pageModel.ZcShop;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcShopServiceI {

	/**
	 * 获取ZcShop数据表格
	 * 
	 * @param zcShop
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcShop zcShop, PageHelper ph);

	/**
	 * 添加ZcShop
	 * 
	 * @param zcShop
	 */
	public void add(ZcShop zcShop);

	/**
	 * 获得ZcShop对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcShop get(String id);

	/**
	 * 修改ZcShop
	 * 
	 * @param zcShop
	 */
	public void edit(ZcShop zcShop);

	/**
	 * 删除ZcShop
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcShop get(ZcShop zcShop);

	public List<ZcShop> query(ZcShop zcShop);

}
