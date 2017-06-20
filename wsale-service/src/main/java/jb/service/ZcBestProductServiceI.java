package jb.service;

import jb.pageModel.ZcBestProduct;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcBestProductServiceI {

	/**
	 * 获取ZcBestProduct数据表格
	 * 
	 * @param zcBestProduct
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcBestProduct zcBestProduct, PageHelper ph);

	/**
	 * 添加ZcBestProduct
	 * 
	 * @param zcBestProduct
	 */
	public void add(ZcBestProduct zcBestProduct);

	/**
	 * 获得ZcBestProduct对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcBestProduct get(String id);

	/**
	 * 修改ZcBestProduct
	 * 
	 * @param zcBestProduct
	 */
	public void edit(ZcBestProduct zcBestProduct);

	/**
	 * 删除ZcBestProduct
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcBestProduct get(ZcBestProduct zcBestProduct);

	public List<ZcBestProduct> query(ZcBestProduct zcBestProduct);

	long getCount(ZcBestProduct zcBestProduct);
}
