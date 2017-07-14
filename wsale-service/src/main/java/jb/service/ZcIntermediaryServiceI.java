package jb.service;

import jb.pageModel.ZcIntermediary;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcIntermediaryServiceI {

	/**
	 * 获取ZcIntermediary数据表格
	 * 
	 * @param zcIntermediary
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcIntermediary zcIntermediary, PageHelper ph);

	/**
	 * 添加ZcIntermediary
	 * 
	 * @param zcIntermediary
	 */
	public void add(ZcIntermediary zcIntermediary);

	/**
	 * 获得ZcIntermediary对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcIntermediary get(String id);

	/**
	 * 修改ZcIntermediary
	 * 
	 * @param zcIntermediary
	 */
	public void edit(ZcIntermediary zcIntermediary);

	/**
	 * 删除ZcIntermediary
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcIntermediary get(ZcIntermediary zcIntermediary);

	public List<ZcIntermediary> query(ZcIntermediary zcIntermediary);

	ZcIntermediary getDetail(String id);
}
