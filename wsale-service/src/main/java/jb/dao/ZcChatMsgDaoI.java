package jb.dao;

import jb.model.TzcChatMsg;

import java.util.List;
import java.util.Map;

/**
 * ZcChatMsg数据库操作类
 * 
 * @author John
 * 
 */
public interface ZcChatMsgDaoI extends BaseDaoI<TzcChatMsg> {

    List<TzcChatMsg> findBy(String hql, Map<String, Object> params, int page, int rows);
}
