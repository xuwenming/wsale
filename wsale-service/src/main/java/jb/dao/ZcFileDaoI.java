package jb.dao;

import jb.model.TzcFile;

import java.util.Map;

/**
 * ZcFile数据库操作类
 * 
 * @author John
 * 
 */
public interface ZcFileDaoI extends BaseDaoI<TzcFile> {

    Map<String,String> queryIcons(String objectType, String... objectIds);
}
