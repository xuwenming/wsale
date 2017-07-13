package jb.service;

import jb.pageModel.ZcAuction;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.ZcProduct;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author John
 * 
 */
public interface ZcAuctionServiceI {

	/**
	 * 获取ZcAuction数据表格
	 * 
	 * @param zcAuction
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ZcAuction zcAuction, PageHelper ph);

	/**
	 * 添加ZcAuction
	 * 
	 * @param zcAuction
	 */
	public void add(ZcAuction zcAuction);

	/**
	 * 获得ZcAuction对象
	 * 
	 * @param id
	 * @return
	 */
	public ZcAuction get(String id);

	/**
	 * 修改ZcAuction
	 * 
	 * @param zcAuction
	 */
	public void edit(ZcAuction zcAuction);

	/**
	 * 删除ZcAuction
	 * 
	 * @param id
	 */
	public void delete(String id);

	public ZcAuction get(ZcAuction zcAuction);

	public List<ZcAuction> query(ZcAuction zcAuction);


	DataGrid dataGridComplet(ZcAuction zcAuction, PageHelper ph);
	DataGrid dataGridCompletetd( String id, PageHelper ph);

	Map<String,Integer> getCountAuctionNum(String[] productIds);
}
