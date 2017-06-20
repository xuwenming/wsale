package jb.service;

import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.ZcShieldorfans;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcShieldorfansServiceI {

	/**
	 * 获取ZcShieldorfans数据表格
	 * 
	 * @param zcShieldorfans
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcShieldorfans zcShieldorfans, PageHelper ph);

	/**
	 * 添加ZcShieldorfans
	 * 
	 * @param zcShieldorfans
	 */
	public void add(ZcShieldorfans zcShieldorfans);

	/**
	 * 获得ZcShieldorfans对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcShieldorfans get(String id);

	/**
	 * 修改ZcShieldorfans
	 * 
	 * @param zcShieldorfans
	 */
	public void edit(ZcShieldorfans zcShieldorfans);

	/**
	 * 删除ZcShieldorfans
	 * 
	 * @param id
	 */
	public void delete(String id);

	ZcShieldorfans get(ZcShieldorfans shieldorfans);

	public List<ZcShieldorfans> query(ZcShieldorfans shieldorfans);
}
