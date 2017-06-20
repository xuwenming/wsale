package jb.service;

import jb.pageModel.ZcBbsComment;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface ZcBbsCommentServiceI {

	/**
	 * 获取ZcBbsComment数据表格
	 * 
	 * @param zcBbsComment
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcBbsComment zcBbsComment, PageHelper ph);

	/**
	 * 添加ZcBbsComment
	 * 
	 * @param zcBbsComment
	 */
	public void add(ZcBbsComment zcBbsComment);

	/**
	 * 获得ZcBbsComment对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcBbsComment get(String id);

	/**
	 * 修改ZcBbsComment
	 * 
	 * @param zcBbsComment
	 */
	public void edit(ZcBbsComment zcBbsComment);

	/**
	 * 删除ZcBbsComment
	 * 
	 * @param id
	 */
	public void delete(String id);

	void delete(ZcBbsComment comment);


}
