package jb.dao.impl;

import jb.dao.ZcProductDaoI;
import jb.model.TzcProduct;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, Integer> getCountBiddingNum(String[] userIds) {
        String in = "";
        for(String id : userIds){
            in += ",'"+id+"'";
        }
        String countSql = "select count(*) as num, addUserId from zc_product where isDeleted = 0 and status = 'PT03' and addUserId in ("+in.substring(1)+")  group by addUserId";
        Map<String, Object> params = new HashMap<String, Object>();
        List<Map> results = findBySql2Map(countSql, params);
        Map countMap = new HashMap();
        for(Map map : results){
            countMap.put(map.get("addUserId"),((BigInteger)map.get("num")).intValue());
        }
        return countMap;
    }

}
