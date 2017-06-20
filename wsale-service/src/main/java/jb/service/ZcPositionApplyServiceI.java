package jb.service;

import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.User;
import jb.pageModel.ZcPositionApply;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcPositionApplyServiceI {

	/**
	 * 获取ZcPositionApply数据表格
	 * 
	 * @param zcPositionApply
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcPositionApply zcPositionApply, PageHelper ph);

	/**
	 * 添加ZcPositionApply
	 * 
	 * @param zcPositionApply
	 */
	public void add(ZcPositionApply zcPositionApply);

	/**
	 * 获得ZcPositionApply对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcPositionApply get(String id);

	/**
	 * 修改ZcPositionApply
	 * 
	 * @param zcPositionApply
	 */
	public void edit(ZcPositionApply zcPositionApply);

	/**
	 * 删除ZcPositionApply
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 根据分类ID获取所有版主信息
	 * @param categoryId
	 * @return
	 */
	public List<User> getAllModerators(String categoryId);

	DataGrid dataGridComplex(ZcPositionApply zcPositionApply, PageHelper ph);

	void editAudit(ZcPositionApply zcPositionApply);

	public ZcPositionApply get(ZcPositionApply zcPositionApply);

	public List<ZcPositionApply> query(ZcPositionApply zcPositionApply);
}
