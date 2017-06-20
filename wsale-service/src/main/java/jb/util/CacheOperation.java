package jb.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wenming on 2016/8/22.
 */
public class CacheOperation {
    private static final Log log = LogFactory.getLog(CacheOperation.class);
    private static CacheOperation singleton = null;
    
    private Hashtable cacheMap;//存放缓存数据 
    
    public static CacheOperation getInstance(){
        if (singleton == null){ 
            singleton = new CacheOperation(); 
        } 
        return singleton; 
    } 
    
    private CacheOperation(){ 
        cacheMap = new Hashtable(); 
    }
    
    /** *//** 
    * 添加数据缓存 
    * 与方法getCacheData(String key, long intervalTime, int maxVisitCount)配合使用 
    * @param key 
    * @param data 
    */ 
    public void addCacheData(String key, Object data){ 
        addCacheData(key, data, true); 
    } 
    
    public void addCacheData(String key, Object data, boolean check){ 
        if (Runtime.getRuntime().freeMemory() < 10L*1024L*1024L){//虚拟机内存小于10兆，则清除缓存
            log.warn("WEB缓存：内存不足，开始清空缓存！"); 
            removeAllCacheData(); 
//            return; 
        } else if(check && cacheMap.containsKey(key)) {
            log.warn("WEB缓存：key值= " + key + " 在缓存中重复, 本次不缓存！"); 
            return; 
        } 
        cacheMap.put(key, new CacheData(data)); 
    } 
    
    /** *//** 
    * 取得缓存中的数据 
    * 与方法addCacheData(String key, Object data)配合使用 
    * @param key 
    * @param intervalTime 缓存的时间周期，小于等于0时不限制 
    * @param maxVisitCount 访问累积次数，小于等于0时不限制 
    * @return 
    */ 
    public Object getCacheData(String key, long intervalTime, int maxVisitCount){ 
        CacheData cacheData = (CacheData)cacheMap.get(key);
        if (cacheData == null){
            return null; 
        } 
        if (intervalTime > 0 && (System.currentTimeMillis() - cacheData.getTime()) > intervalTime){ 
            removeCacheData(key); 
            return null; 
        } 
        if (maxVisitCount > 0 && (maxVisitCount - cacheData.getCount()) <= 0){ 
            removeCacheData(key); 
            return null; 
        } else{ 
            cacheData.addCount(); 
        } 
        return cacheData.getData(); 
    }

    /**
     * 只获取一次缓存中的数据
     * @param key
     * @param intervalTime 缓存的时间周期，小于等于0时不限制
     * @return
     */
    public Object getCacheData(String key, long intervalTime){
        CacheData cacheData = (CacheData) cacheMap.remove(key);
        if (cacheData == null){
            return null;
        }
        if (intervalTime > 0 && (System.currentTimeMillis() - cacheData.getTime()) > intervalTime){
            return null;
        }
        return cacheData.getData();
    }

    /** *//**
     * 移除缓存中的数据 
     * @param key 
     */ 
     public void removeCacheData(String key){ 
         cacheMap.remove(key); 
     } 
     
     /** *//** 
     * 移除所有缓存中的数据 
     * 
     */ 
     public void removeAllCacheData(){ 
         cacheMap.clear();
     } 
     
     /** *//** 
      * 获取所有缓存
      * @param
      */ 
     public Hashtable getAllCache(){
    	 return cacheMap;
     }

     public String toString(){
         StringBuffer sb = new StringBuffer("************************ ");
         sb.append("当前缓存大小：").append(cacheMap.size()).append(" ");
         sb.append("************************");
         return sb.toString();
     }

    class CacheData{
        private Object data;
        private long time;
        private int count;

        public CacheData(){

        }

        public CacheData(Object data, long time, int count){
            this.data = data;
            this.time = time;
            this.count = count;
        }

        public CacheData(Object data){
            this.data = data;
            this.time = System.currentTimeMillis();
            this.count = 1;
        }

        public void addCount(){
            count++;
        }

        public int getCount(){
            return count;
        }
        public void setCount(int count){
            this.count = count;
        }
        public Object getData(){
            return data;
        }
        public void setData(Object data){
            this.data = data;
        }
        public long getTime(){
            return time;
        }
        public void setTime(long time){
            this.time = time;
        }
    }
}

