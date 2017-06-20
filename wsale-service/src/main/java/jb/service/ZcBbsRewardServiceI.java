package jb.service;

import jb.pageModel.ZcBbsReward;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcBbsRewardServiceI {

	/**
	 * 获取ZcBbsReward数据表格
	 * 
	 * @param zcBbsReward
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcBbsReward zcBbsReward, PageHelper ph);

	/**
	 * 添加ZcBbsReward
	 * 
	 * @param zcBbsReward
	 */
	public void add(ZcBbsReward zcBbsReward);

	/**
	 * 获得ZcBbsReward对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcBbsReward get(String id);

	/**
	 * 修改ZcBbsReward
	 * 
	 * @param zcBbsReward
	 */
	public void edit(ZcBbsReward zcBbsReward);

	/**
	 * 删除ZcBbsReward
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcBbsReward get(ZcBbsReward zcBbsReward);

	public List<ZcBbsReward> query(ZcBbsReward zcBbsReward);

}
