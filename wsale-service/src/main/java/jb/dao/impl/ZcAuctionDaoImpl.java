package jb.dao.impl;

import jb.dao.ZcAuctionDaoI;
import jb.model.TzcAuction;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ZcAuctionDaoImpl extends BaseDaoImpl<TzcAuction> implements ZcAuctionDaoI {

    @Override
    public Map<String, Integer> getCountAuctionNum(String[] productIds) {
        String in = "";
        for(String id : productIds){
            in += ",'"+id+"'";
        }
        String countSql = "select count(*) as num, product_id from zc_auction where product_id in ("+in.substring(1)+")  group by product_id";
        Map<String, Object> params = new HashMap<String, Object>();
        List<Map> results = findBySql2Map(countSql, params);
        Map countMap = new HashMap();
        for(Map map : results){
            countMap.put(map.get("product_id"),((BigInteger)map.get("num")).intValue());
        }
        return countMap;
    }
}
