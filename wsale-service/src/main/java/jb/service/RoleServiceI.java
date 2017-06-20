package jb.service;

import java.util.List;

import jb.pageModel.Role;
import jb.pageModel.SessionInfo;
import jb.pageModel.Tree;
import jb.pageModel.User;

/**
 * 角色业务逻辑
 * 
 * @author John
 * 
 */
public interface RoleServiceI {

	/**
	 * 保存角色
	 * 
	 * @param role
	 */
	public void add(Role role, SessionInfo sessionInfo);

	/**
	 * 获得角色
	 * 
	 * @param id
	 * @return
	 */
	public Role get(String id);

	/**
	 * 编辑角色
	 * 
	 * @param role
	 */
	public void edit(Role role);

	/**
	 * 获得角色treeGrid
	 * 
	 * @return
	 */
	public List<Role> treeGrid(SessionInfo sessionInfo);

	/**
	 * 删除角色
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 获得角色树(只能看到自己拥有的角色)
	 * 
	 * @return
	 */
	public List<Tree> tree(SessionInfo sessionInfo);

	/**
	 * 获得角色树
	 * 
	 * @return
	 */
	public List<Tree> allTree();
	
	/**
	 * 获得角色下所有用户
	 * @param roleId
	 * @return
	 */
	public List<User> getUsers(String roleId);

	/**
	 * 为角色授权
	 * 
	 * @param role
	 */
	public void grant(Role role);

}
