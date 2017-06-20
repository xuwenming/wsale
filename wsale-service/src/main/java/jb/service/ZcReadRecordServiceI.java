package jb.service;

import jb.pageModel.ZcReadRecord;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcReadRecordServiceI {

	/**
	 * 获取ZcReadRecord数据表格
	 * 
	 * @param zcReadRecord
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcReadRecord zcReadRecord, PageHelper ph);

	/**
	 * 添加ZcReadRecord
	 * 
	 * @param zcReadRecord
	 */
	public void add(ZcReadRecord zcReadRecord);

	/**
	 * 获得ZcReadRecord对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcReadRecord get(String id);

	/**
	 * 修改ZcReadRecord
	 * 
	 * @param zcReadRecord
	 */
	public void edit(ZcReadRecord zcReadRecord);

	/**
	 * 删除ZcReadRecord
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcReadRecord get(ZcReadRecord zcReadRecord);

	public List<ZcReadRecord> query(ZcReadRecord zcReadRecord);

}
