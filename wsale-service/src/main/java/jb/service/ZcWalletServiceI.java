package jb.service;

import jb.pageModel.ZcWallet;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcWalletServiceI {

	/**
	 * 获取ZcWallet数据表格
	 * 
	 * @param zcWallet
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcWallet zcWallet, PageHelper ph);

	/**
	 * 添加ZcWallet
	 * 
	 * @param zcWallet
	 */
	public void add(ZcWallet zcWallet);

	/**
	 * 获得ZcWallet对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcWallet get(String id);

	/**
	 * 修改ZcWallet
	 * 
	 * @param zcWallet
	 */
	public void edit(ZcWallet zcWallet);

	/**
	 * 删除ZcWallet
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcWallet get(ZcWallet zcWallet);

	public List<ZcWallet> query(ZcWallet zcWallet);

	void updateProtection(String userId, double protection);

	void updateAmount(String userId, double amount);
}
