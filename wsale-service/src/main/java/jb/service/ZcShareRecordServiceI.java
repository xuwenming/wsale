package jb.service;

import jb.pageModel.ZcShareRecord;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcShareRecordServiceI {

	/**
	 * 获取ZcShareRecord数据表格
	 * 
	 * @param zcShareRecord
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcShareRecord zcShareRecord, PageHelper ph);

	/**
	 * 添加ZcShareRecord
	 * 
	 * @param zcShareRecord
	 */
	public void add(ZcShareRecord zcShareRecord);

	/**
	 * 获得ZcShareRecord对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcShareRecord get(String id);

	/**
	 * 修改ZcShareRecord
	 * 
	 * @param zcShareRecord
	 */
	public void edit(ZcShareRecord zcShareRecord);

	/**
	 * 删除ZcShareRecord
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcShareRecord get(ZcShareRecord zcShareRecord);

	public List<ZcShareRecord> query(ZcShareRecord zcShareRecord);

}
