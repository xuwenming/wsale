package jb.service;

import jb.pageModel.ZcFile;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcFileServiceI {

	/**
	 * 获取ZcFile数据表格
	 * 
	 * @param zcFile
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcFile zcFile, PageHelper ph);

	/**
	 * 添加ZcFile
	 * 
	 * @param zcFile
	 */
	public void add(ZcFile zcFile);

	/**
	 * 获得ZcFile对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcFile get(String id);

	/**
	 * 修改ZcFile
	 * 
	 * @param zcFile
	 */
	public void edit(ZcFile zcFile);

	/**
	 * 删除ZcFile
	 * 
	 * @param id
	 */
	public void delete(String id);

	List<ZcFile> queryFiles(ZcFile file);
	public ZcFile get(ZcFile file);

	int getMaxSeq(ZcFile zcFile);
}
