package jb.service;

import jb.pageModel.ZcComment;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcCommentServiceI {

	/**
	 * 获取ZcComment数据表格
	 * 
	 * @param zcComment
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcComment zcComment, PageHelper ph);

	/**
	 * 添加ZcComment
	 * 
	 * @param zcComment
	 */
	public void add(ZcComment zcComment);

	/**
	 * 获得ZcComment对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcComment get(String id);

	/**
	 * 修改ZcComment
	 * 
	 * @param zcComment
	 */
	public void edit(ZcComment zcComment);

	/**
	 * 删除ZcComment
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcComment get(ZcComment zcComment);

	public List<ZcComment> query(ZcComment zcComment);

	float getGradeAvgByUserId(String userId);
}
