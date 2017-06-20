package jb.service;

import jb.pageModel.ZcCategory;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcCategoryServiceI {

	/**
	 * 获取ZcCategory数据表格
	 * 
	 * @param zcCategory
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcCategory zcCategory, PageHelper ph);

	/**
	 * 添加ZcCategory
	 * 
	 * @param zcCategory
	 */
	public void add(ZcCategory zcCategory);

	/**
	 * 获得ZcCategory对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcCategory get(String id);

	/**
	 * 修改ZcCategory
	 * 
	 * @param zcCategory
	 */
	public void edit(ZcCategory zcCategory);

	/**
	 * 删除ZcCategory
	 * 
	 * @param id
	 */
	public void delete(ZcCategory zcCategory);

	List<ZcCategory> query(ZcCategory c);
}
