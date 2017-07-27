package jb.dao;

import jb.model.TzcProduct;
import jb.pageModel.ZcProduct;

import java.util.List;
import java.util.Map;

/**
 * ZcProduct数据库操作类
 * 
 * @author John
 * 
 */
public interface ZcProductDaoI extends BaseDaoI<TzcProduct> {

    List<TzcProduct> getListByIds(String... productIds);

    Map<String,Integer> getCountBiddingNum(String[] userIds);
}
