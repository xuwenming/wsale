package jb.service;

import jb.pageModel.ZcTopic;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcTopicServiceI {

	/**
	 * 获取ZcTopic数据表格
	 * 
	 * @param zcTopic
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcTopic zcTopic, PageHelper ph);

	/**
	 * 添加ZcTopic
	 * 
	 * @param zcTopic
	 */
	public void add(ZcTopic zcTopic);

	/**
	 * 获得ZcTopic对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcTopic get(String id);

	/**
	 * 修改ZcTopic
	 * 
	 * @param zcTopic
	 */
	public void edit(ZcTopic zcTopic);

	/**
	 * 删除ZcTopic
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcTopic get(ZcTopic zcTopic);

	public List<ZcTopic> query(ZcTopic zcTopic);

	ZcTopic addReadAndDetail(String id);
}
