package jb.service;

import jb.pageModel.DonationOrder;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface DonationOrderServiceI {

	/**
	 * 获取DonationOrder数据表格
	 * 
	 * @param donationOrder
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(DonationOrder donationOrder, PageHelper ph);

	/**
	 * 添加DonationOrder
	 * 
	 * @param donationOrder
	 */
	public void add(DonationOrder donationOrder);

	/**
	 * 获得DonationOrder对象
	 * 
	 * @param id
	 * @return
	 */
	public DonationOrder get(String id);

	/**
	 * 修改DonationOrder
	 * 
	 * @param donationOrder
	 */
	public void edit(DonationOrder donationOrder);

	/**
	 * 删除DonationOrder
	 * 
	 * @param id
	 */
	public void delete(String id);

	public void editByOrderNo(DonationOrder donationOrder);

}
