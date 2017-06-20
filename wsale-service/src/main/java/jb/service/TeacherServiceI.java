package jb.service;

import jb.pageModel.Teacher;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface TeacherServiceI {

	/**
	 * 获取Teacher数据表格
	 * 
	 * @param teacher
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(Teacher teacher, PageHelper ph);

	/**
	 * 添加Teacher
	 * 
	 * @param teacher
	 */
	public void add(Teacher teacher);

	/**
	 * 获得Teacher对象
	 * 
	 * @param id
	 * @return
	 */
	public Teacher get(String id);

	/**
	 * 修改Teacher
	 * 
	 * @param teacher
	 */
	public void edit(Teacher teacher);

	/**
	 * 删除Teacher
	 * 
	 * @param id
	 */
	public void delete(String id);

	public Teacher get(Teacher teacher);

	public List<Teacher> query(Teacher teacher);

}
