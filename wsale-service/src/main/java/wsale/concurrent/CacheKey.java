package wsale.concurrent;

/**
 * Created by john on 16/8/13.
 */
public class CacheKey {
    private String nameSpace;
    private String key;

    public String getNameSpace() {
        return nameSpace;
    }

    public CacheKey(String nameSpace, String key) {
        this.nameSpace = nameSpace;
        this.key = key;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "CacheKey{" +
                "nameSpace='" + nameSpace + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}

