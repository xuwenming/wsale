package jb.dao.impl;

import jb.dao.ZcProductDaoI;
import jb.model.TzcProduct;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ZcProductDaoImpl extends BaseDaoImpl<TzcProduct> implements ZcProductDaoI {

    @Override
    public List<TzcProduct> getListByIds(String... productIds) {
        String hql = " from TzcProduct t where t.id in (:productIds) ";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameterList("productIds", productIds);
        List<TzcProduct> l = query.list();
        return l;
    }

}
