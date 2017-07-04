package jb.service;

import jb.pageModel.ZcProduct;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcProductServiceI {

	/**
	 * 获取ZcProduct数据表格
	 * 
	 * @param zcProduct
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcProduct zcProduct, PageHelper ph);
	DataGrid dataGridComplex(ZcProduct zcProduct, PageHelper ph);

	/**
	 * 添加ZcProduct
	 * 
	 * @param zcProduct
	 */
	public void add(ZcProduct zcProduct);

	/**
	 * 获得ZcProduct对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcProduct get(String id);

	public ZcProduct get(String id, String userId);

	/**
	 * 修改ZcProduct
	 * 
	 * @param zcProduct
	 */
	public void edit(ZcProduct zcProduct);

	/**
	 * 删除ZcProduct
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcProduct get(ZcProduct zcProduct);

	public List<ZcProduct> query(ZcProduct zcProduct);

	ZcProduct addReadAndDetail(String id, String userId);
	void updateCount(String id, int count, String type);

	void updateState(String productIds, String status);

	long getCount(ZcProduct product);

	List<ZcProduct> getListByIds(String... productIds);
}
