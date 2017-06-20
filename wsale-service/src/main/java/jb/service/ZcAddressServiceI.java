package jb.service;

import jb.pageModel.ZcAddress;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcAddressServiceI {

	/**
	 * 获取ZcAddress数据表格
	 * 
	 * @param zcAddress
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcAddress zcAddress, PageHelper ph);

	/**
	 * 添加ZcAddress
	 * 
	 * @param zcAddress
	 */
	public void add(ZcAddress zcAddress);

	/**
	 * 获得ZcAddress对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcAddress get(String id);

	/**
	 * 修改ZcAddress
	 * 
	 * @param zcAddress
	 */
	public void edit(ZcAddress zcAddress);

	/**
	 * 删除ZcAddress
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcAddress get(ZcAddress zcAddress);

	public List<ZcAddress> query(ZcAddress zcAddress);

}
