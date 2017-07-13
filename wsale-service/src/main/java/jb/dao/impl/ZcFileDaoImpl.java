package jb.dao.impl;

import jb.dao.ZcFileDaoI;
import jb.model.TzcFile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ZcFileDaoImpl extends BaseDaoImpl<TzcFile> implements ZcFileDaoI {

    @Override
    public Map<String, String> queryIcons(String objectType, String... objectIds) {
        String in = "";
        for(String id : objectIds){
            in += ",'"+id+"'";
        }
        String sql = "select t.object_id, t.file_handle_url from (select * from zc_file where  object_type = :objectType and file_type = :fileType and object_id in ("+in.substring(1)+") order by seq asc) t group by t.object_id ";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("objectType", objectType);
        params.put("fileType", "FT01");
        List<Map> results = findBySql2Map(sql, params);

        Map<String, String> result = new HashMap<String, String>();
        for(Map map : results) {
            result.put((String)map.get("object_id"), (String)map.get("file_handle_url"));
        }

        return result;
    }
}
