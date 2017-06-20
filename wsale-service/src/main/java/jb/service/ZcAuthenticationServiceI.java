package jb.service;

import jb.pageModel.ZcAuthentication;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcAuthenticationServiceI {

	/**
	 * 获取ZcAuthentication数据表格
	 * 
	 * @param zcAuthentication
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcAuthentication zcAuthentication, PageHelper ph);

	/**
	 * 添加ZcAuthentication
	 * 
	 * @param zcAuthentication
	 */
	public void add(ZcAuthentication zcAuthentication);

	/**
	 * 获得ZcAuthentication对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcAuthentication get(String id);

	/**
	 * 修改ZcAuthentication
	 * 
	 * @param zcAuthentication
	 */
	public void edit(ZcAuthentication zcAuthentication);

	/**
	 * 删除ZcAuthentication
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcAuthentication get(ZcAuthentication zcAuthentication);

	public List<ZcAuthentication> query(ZcAuthentication zcAuthentication);

}
