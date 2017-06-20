package jb.service;

import jb.pageModel.ZcBanner;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcBannerServiceI {

	/**
	 * 获取ZcBanner数据表格
	 * 
	 * @param zcBanner
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcBanner zcBanner, PageHelper ph);

	/**
	 * 添加ZcBanner
	 * 
	 * @param zcBanner
	 */
	public void add(ZcBanner zcBanner);

	/**
	 * 获得ZcBanner对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcBanner get(String id);

	/**
	 * 修改ZcBanner
	 * 
	 * @param zcBanner
	 */
	public void edit(ZcBanner zcBanner);

	/**
	 * 删除ZcBanner
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcBanner get(ZcBanner zcBanner);

	public List<ZcBanner> query(ZcBanner zcBanner);

}
