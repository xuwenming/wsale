package jb.service;

import jb.pageModel.ZcProtection;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcProtectionServiceI {

	/**
	 * 获取ZcProtection数据表格
	 * 
	 * @param zcProtection
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcProtection zcProtection, PageHelper ph);

	/**
	 * 添加ZcProtection
	 * 
	 * @param zcProtection
	 */
	public void add(ZcProtection zcProtection);

	/**
	 * 获得ZcProtection对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcProtection get(String id);

	/**
	 * 修改ZcProtection
	 * 
	 * @param zcProtection
	 */
	public void edit(ZcProtection zcProtection);

	/**
	 * 删除ZcProtection
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcProtection get(ZcProtection zcProtection);

	public List<ZcProtection> query(ZcProtection zcProtection);

	void addAndUpdateWallet(ZcProtection zcProtection);
}
