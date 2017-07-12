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
        String hql = "select t from TzcProduct t, TzcBestProduct b where t.id = b.productId and t.id in (:productIds) order by b.productSeq desc, b.auditTime desc";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameterList("productIds", productIds);
        List<TzcProduct> l = query.list();
        return l;
    }

}
