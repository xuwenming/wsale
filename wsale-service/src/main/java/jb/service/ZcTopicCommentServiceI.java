package jb.service;

import jb.pageModel.ZcTopicComment;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcTopicCommentServiceI {

	/**
	 * 获取ZcTopicComment数据表格
	 * 
	 * @param zcTopicComment
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcTopicComment zcTopicComment, PageHelper ph);

	/**
	 * 添加ZcTopicComment
	 * 
	 * @param zcTopicComment
	 */
	public void add(ZcTopicComment zcTopicComment);

	/**
	 * 获得ZcTopicComment对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcTopicComment get(String id);

	/**
	 * 修改ZcTopicComment
	 * 
	 * @param zcTopicComment
	 */
	public void edit(ZcTopicComment zcTopicComment);

	/**
	 * 删除ZcTopicComment
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcTopicComment get(ZcTopicComment zcTopicComment);

	public List<ZcTopicComment> query(ZcTopicComment zcTopicComment);

}
