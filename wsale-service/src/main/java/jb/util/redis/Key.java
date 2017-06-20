package jb.util.redis;

/**
 * Created by john on 15/12/30.
 */
public abstract class Key {
    public static String build(String namespace,String key){
        return namespace+":"+key;
    }
}
