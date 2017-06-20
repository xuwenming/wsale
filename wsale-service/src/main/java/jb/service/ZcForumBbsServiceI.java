package jb.service;

import jb.pageModel.ZcForumBbs;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcForumBbsServiceI {

	/**
	 * 获取ZcForumBbs数据表格
	 * 
	 * @param zcForumBbs
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcForumBbs zcForumBbs, PageHelper ph);
	DataGrid dataGridComplex(ZcForumBbs bbs, PageHelper ph);

	/**
	 * 添加ZcForumBbs
	 * 
	 * @param zcForumBbs
	 */
	public void add(ZcForumBbs zcForumBbs);

	/**
	 * 获得ZcForumBbs对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcForumBbs get(String id);

	/**
	 * 修改ZcForumBbs
	 * 
	 * @param zcForumBbs
	 */
	public void edit(ZcForumBbs zcForumBbs);

	/**
	 * 删除ZcForumBbs
	 * 
	 * @param id
	 */
	public void delete(String id);

	ZcForumBbs addReadAndDetail(String id, String userId);
	void updateCount(String id, int count, String type);
	void updateCountByWhere(String where, int count, String type);

	int getTextthemeCommentNums(String userId);

	List<ZcForumBbs> query(ZcForumBbs bbs);

}
