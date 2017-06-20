package jb.service;

import jb.pageModel.ZcWalletDetail;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcWalletDetailServiceI {

	/**
	 * 获取ZcWalletDetail数据表格
	 * 
	 * @param zcWalletDetail
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcWalletDetail zcWalletDetail, PageHelper ph);

	/**
	 * 添加ZcWalletDetail
	 * 
	 * @param zcWalletDetail
	 */
	public void add(ZcWalletDetail zcWalletDetail);

	public void addAndUpdateWallet(ZcWalletDetail zcWalletDetail);

	/**
	 * 获得ZcWalletDetail对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcWalletDetail get(String id);

	/**
	 * 修改ZcWalletDetail
	 * 
	 * @param zcWalletDetail
	 */
	public void edit(ZcWalletDetail zcWalletDetail);

	/**
	 * 删除ZcWalletDetail
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcWalletDetail get(ZcWalletDetail zcWalletDetail);

	public List<ZcWalletDetail> query(ZcWalletDetail zcWalletDetail);

}
