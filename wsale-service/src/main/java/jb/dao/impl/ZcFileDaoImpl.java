package jb.dao.impl;

import jb.dao.ZcFileDaoI;
import jb.model.TzcFile;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ZcFileDaoImpl extends BaseDaoImpl<TzcFile> implements ZcFileDaoI {

    @Override
    public Map<String, String> queryIcons(String objectType, String... objectIds) {
        String hql = " from TzcFile t where t.objectType = :objectType and t.fileType = :fileType and t.objectId in (:objectIds) group by t.objectId order by t.seq asc, t.addtime asc ";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameterList("objectIds", objectIds);
        query.setParameter("objectType", objectType);
        query.setParameter("fileType", "FT01");
        List<TzcFile> l = query.list();
        Map<String, String> result = new HashMap<String, String>();
        for(TzcFile f : l) {
            result.put(f.getObjectId(), f.getFileHandleUrl());
        }

        return result;
    }
}
