package jb.service;

import jb.pageModel.ZcUserAutoPrice;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcUserAutoPriceServiceI {

	/**
	 * 获取ZcUserAutoPrice数据表格
	 * 
	 * @param zcUserAutoPrice
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcUserAutoPrice zcUserAutoPrice, PageHelper ph);

	/**
	 * 添加ZcUserAutoPrice
	 * 
	 * @param zcUserAutoPrice
	 */
	public void add(ZcUserAutoPrice zcUserAutoPrice);

	/**
	 * 获得ZcUserAutoPrice对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcUserAutoPrice get(String id);

	/**
	 * 修改ZcUserAutoPrice
	 * 
	 * @param zcUserAutoPrice
	 */
	public void edit(ZcUserAutoPrice zcUserAutoPrice);

	/**
	 * 删除ZcUserAutoPrice
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcUserAutoPrice get(ZcUserAutoPrice zcUserAutoPrice);

	public List<ZcUserAutoPrice> query(ZcUserAutoPrice zcUserAutoPrice);

}
