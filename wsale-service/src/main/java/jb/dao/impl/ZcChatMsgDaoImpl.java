package jb.dao.impl;

import jb.dao.ZcChatMsgDaoI;
import jb.model.TzcChatMsg;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ZcChatMsgDaoImpl extends BaseDaoImpl<TzcChatMsg> implements ZcChatMsgDaoI {

    @Override
    public List<TzcChatMsg> findBy(String hql, Map<String, Object> params, int page, int rows) {
        Query q = this.getCurrentSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.setFirstResult(page).setMaxResults(rows).list();
    }
}
