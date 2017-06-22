package jb.service;

import jb.pageModel.ZcCollect;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcCollectServiceI {

	/**
	 * 获取ZcCollect数据表格
	 * 
	 * @param zcCollect
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcCollect zcCollect, PageHelper ph);

	/**
	 * 添加ZcCollect
	 * 
	 * @param zcCollect
	 */
	public void add(ZcCollect zcCollect);

	/**
	 * 获得ZcCollect对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcCollect get(String id);

	/**
	 * 修改ZcCollect
	 * 
	 * @param zcCollect
	 */
	public void edit(ZcCollect zcCollect);

	/**
	 * 删除ZcCollect
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcCollect get(ZcCollect zcCollect);

	public List<ZcCollect> query(ZcCollect zcCollect);

}
