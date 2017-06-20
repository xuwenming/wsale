package jb.service;

import jb.pageModel.ZcProductRange;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcProductRangeServiceI {

	/**
	 * 获取ZcProductRange数据表格
	 * 
	 * @param zcProductRange
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcProductRange zcProductRange, PageHelper ph);

	/**
	 * 添加ZcProductRange
	 * 
	 * @param zcProductRange
	 */
	public void add(ZcProductRange zcProductRange);

	/**
	 * 获得ZcProductRange对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcProductRange get(String id);

	/**
	 * 修改ZcProductRange
	 * 
	 * @param zcProductRange
	 */
	public void edit(ZcProductRange zcProductRange);

	/**
	 * 删除ZcProductRange
	 * 
	 * @param id
	 */
	public void delete(String id);

	public void delete(ZcProductRange zcProductRange);

	List<ZcProductRange> query(ZcProductRange zcProductRange);

	ZcProductRange getLastByUserId(String userId);

	/**
	 * 获取拍品当前加价幅度
	 * @param productId
	 * @param currentPrice
	 * @return
	 */
	double getRangePrice(String productId, double currentPrice);
}
