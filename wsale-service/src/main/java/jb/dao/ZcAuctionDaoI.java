package jb.dao;

import jb.model.TzcAuction;

import java.util.Map;

/**
 * ZcAuction数据库操作类
 * 
 * @author John
 * 
 */
public interface ZcAuctionDaoI extends BaseDaoI<TzcAuction> {

    Map<String,Integer> getCountAuctionNum(String[] productIds);
}
