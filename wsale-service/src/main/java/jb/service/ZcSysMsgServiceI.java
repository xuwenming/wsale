package jb.service;

import jb.pageModel.ZcSysMsg;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcSysMsgServiceI {

	/**
	 * 获取ZcSysMsg数据表格
	 * 
	 * @param zcSysMsg
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcSysMsg zcSysMsg, PageHelper ph);

	/**
	 * 添加ZcSysMsg
	 * 
	 * @param zcSysMsg
	 */
	public void add(ZcSysMsg zcSysMsg);

	/**
	 * 获得ZcSysMsg对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcSysMsg get(String id);

	/**
	 * 修改ZcSysMsg
	 * 
	 * @param zcSysMsg
	 */
	public void edit(ZcSysMsg zcSysMsg);

	/**
	 * 删除ZcSysMsg
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcSysMsg get(ZcSysMsg zcSysMsg);

	public List<ZcSysMsg> query(ZcSysMsg zcSysMsg);

}
