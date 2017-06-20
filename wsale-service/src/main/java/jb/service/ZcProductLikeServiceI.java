package jb.service;

import jb.pageModel.ZcProductLike;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcProductLikeServiceI {

	/**
	 * 获取ZcProductLike数据表格
	 * 
	 * @param zcProductLike
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcProductLike zcProductLike, PageHelper ph);

	/**
	 * 添加ZcProductLike
	 * 
	 * @param zcProductLike
	 */
	public void add(ZcProductLike zcProductLike);

	/**
	 * 获得ZcProductLike对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcProductLike get(String id);

	/**
	 * 修改ZcProductLike
	 * 
	 * @param zcProductLike
	 */
	public void edit(ZcProductLike zcProductLike);

	/**
	 * 删除ZcProductLike
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcProductLike get(ZcProductLike zcProductLike);

	public List<ZcProductLike> query(ZcProductLike zcProductLike);

}
