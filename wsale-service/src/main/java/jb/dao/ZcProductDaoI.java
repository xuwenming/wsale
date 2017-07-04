package jb.dao;

import jb.model.TzcProduct;
import jb.pageModel.ZcProduct;

import java.util.List;

/**
 * ZcProduct数据库操作类
 * 
 * @author John
 * 
 */
public interface ZcProductDaoI extends BaseDaoI<TzcProduct> {

    List<TzcProduct> getListByIds(String... productIds);
}
