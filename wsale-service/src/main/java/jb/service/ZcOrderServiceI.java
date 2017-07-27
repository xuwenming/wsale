package jb.service;

import jb.pageModel.*;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author John
 * 
 */
public interface ZcOrderServiceI {

	/**
	 * 获取ZcOrder数据表格
	 * 
	 * @param zcOrder
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcOrder zcOrder, PageHelper ph);

	public DataGrid dataGridComp(ZcOrder zcOrder,ZcProduct zcProduct,Boolean isXiaoer, PageHelper ph);
	/**
	 * 添加ZcOrder
	 * 
	 * @param zcOrder
	 */
	public void add(ZcOrder zcOrder);

	/**
	 * 获得ZcOrder对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcOrder get(String id);

	/**
	 * 修改ZcOrder
	 * 
	 * @param zcOrder
	 */
	public void edit(ZcOrder zcOrder);

	/**
	 * 删除ZcOrder
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcOrder get(ZcOrder zcOrder);

	public List<ZcOrder> query(ZcOrder zcOrder);
	public List<ZcOrder> query(ZcOrder zcOrder, String otherWhere);

	Map<String,Object> orderCount(String userId);
	Map<String,Object> orderAmountCount(String userId);
	Map<String, Object> orderStatusCount(String userId);
	Double getTurnover(String userId);

	/**
	 * 订单状态转换
	 * @param zcOrder
	 */
	void transform(ZcOrder zcOrder);

	OrderProductInfo getProductInfo(ZcOrder order);
}
