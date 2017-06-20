package jb.service;

import jb.pageModel.ZcPayOrder;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.ZcProductMargin;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcPayOrderServiceI {

	/**
	 * 获取ZcPayOrder数据表格
	 * 
	 * @param zcPayOrder
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcPayOrder zcPayOrder, PageHelper ph);

	/**
	 * 添加ZcPayOrder
	 * 
	 * @param zcPayOrder
	 */
	public void add(ZcPayOrder zcPayOrder);

	/**
	 * 获得ZcPayOrder对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcPayOrder get(String id);

	/**
	 * 修改ZcPayOrder
	 * 
	 * @param zcPayOrder
	 */
	public void edit(ZcPayOrder zcPayOrder);

	/**
	 * 删除ZcPayOrder
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcPayOrder get(ZcPayOrder zcPayOrder);

	public List<ZcPayOrder> query(ZcPayOrder zcPayOrder);

	void editByParam(ZcPayOrder payOrder);

	public void updateWallet(String userId, double amount);

	ZcPayOrder refund(ZcPayOrder payOrder, String walletDesc);
	ZcPayOrder refund(ZcPayOrder payOrder, String walletDesc, ZcProductMargin margin);
}
