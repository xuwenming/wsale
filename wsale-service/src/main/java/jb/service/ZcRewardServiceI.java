package jb.service;

import jb.pageModel.ZcReward;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ZcRewardServiceI {

	/**
	 * 获取ZcReward数据表格
	 * 
	 * @param zcReward
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcReward zcReward, PageHelper ph);

	/**
	 * 添加ZcReward
	 * 
	 * @param zcReward
	 */
	public void add(ZcReward zcReward);

	/**
	 * 获得ZcReward对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcReward get(String id);

	/**
	 * 修改ZcReward
	 * 
	 * @param zcReward
	 */
	public void edit(ZcReward zcReward);

	/**
	 * 删除ZcReward
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcReward get(ZcReward zcReward);

	public List<ZcReward> query(ZcReward zcReward);

}
