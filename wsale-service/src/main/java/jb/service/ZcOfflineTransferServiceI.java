package jb.service;

import jb.pageModel.ZcOfflineTransfer;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcOfflineTransferServiceI {

	/**
	 * 获取ZcOfflineTransfer数据表格
	 * 
	 * @param zcOfflineTransfer
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcOfflineTransfer zcOfflineTransfer, PageHelper ph);

	/**
	 * 添加ZcOfflineTransfer
	 * 
	 * @param zcOfflineTransfer
	 */
	public void add(ZcOfflineTransfer zcOfflineTransfer);

	/**
	 * 获得ZcOfflineTransfer对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcOfflineTransfer get(String id);

	/**
	 * 修改ZcOfflineTransfer
	 * 
	 * @param zcOfflineTransfer
	 */
	public void edit(ZcOfflineTransfer zcOfflineTransfer);

	/**
	 * 删除ZcOfflineTransfer
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcOfflineTransfer get(ZcOfflineTransfer zcOfflineTransfer);

	public List<ZcOfflineTransfer> query(ZcOfflineTransfer zcOfflineTransfer);

}
