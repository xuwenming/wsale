package jb.util;

/**
 * Created by wenming on 2016/8/19.
 */
public class EnumConstants {

    public static enum OBJECT_TYPE {
        BBS("帖子"),
        COMMENT("评论"),
        PRODUCT("拍品"),
        XR("小二申诉"),
        USER("用户"),
        TOPIC("专题文章");
        private String cnName;
        OBJECT_TYPE(String name) {
            this.cnName=name;
        }
        public String getCode() {
            return this.name();
        }
        public String getCnName() {
            return this.cnName;
        }
        public static String getCnName(String code) {
            for(OBJECT_TYPE item : OBJECT_TYPE.values()){
                if(item.getCode().equals(code)) {
                    return item.getCnName();
                }
            }
            return code;
        }
        public String toString(){
            return "code:"+this.name()+",cnName:"+this.cnName;
        }
    }

    public static enum SHIELDOR_FANS {
        FS("粉丝"),
        SD("屏蔽");
        private String cnName;
        SHIELDOR_FANS(String name) {
            this.cnName=name;
        }
        public String getCode() {
            return this.name();
        }
        public String getCnName() {
            return this.cnName;
        }
        public static String getCnName(String code) {
            for(SHIELDOR_FANS item : SHIELDOR_FANS.values()){
                if(item.getCode().equals(code)) {
                    return item.getCnName();
                }
            }
            return code;
        }
        public String toString() {
            return "code:"+this.name()+",cnName:"+this.cnName;
        }
    }

    public static enum BEST_CHANNEL {
        HOME("主页精拍"),
        CATEGORY("分类精拍");
        private String cnName;
        BEST_CHANNEL(String name) {
            this.cnName=name;
        }
        public String getCode() {
            return this.name();
        }
        public String getCnName() {
            return this.cnName;
        }
        public static String getCnName(String code) {
            for(BEST_CHANNEL item : BEST_CHANNEL.values()){
                if(item.getCode().equals(code)) {
                    return item.getCnName();
                }
            }
            return code;
        }
        public String toString() {
            return "code:"+this.name()+",cnName:"+this.cnName;
        }
    }

    public static enum MSG_TYPE {
        TEXT("文本"),
        IMAGE("图片"),
        AUDIO("语音"),
        PRODUCT("拍品"),
        BBS("帖子");
        private String cnName;
        MSG_TYPE(String name) {
            this.cnName=name;
        }
        public String getCode() {
            return this.name();
        }
        public String getCnName() {
            return this.cnName;
        }
        public static String getCnName(String code) {
            for(MSG_TYPE item : MSG_TYPE.values()){
                if(item.getCode().equals(code)) {
                    return item.getCnName();
                }
            }
            return code;
        }
        public String toString() {
            return "code:"+this.name()+",cnName:"+this.cnName;
        }
    }

    public static enum SHARE_CHANNEL {
        AppMessage("微信朋友"),
        Timeline("微信朋友圈"),
        QQ("QQ"),
        QZone("QQ空间"),
        Weibo("微博");
        private String cnName;
        SHARE_CHANNEL(String name) {
            this.cnName=name;
        }
        public String getCode() {
            return this.name();
        }
        public String getCnName() {
            return this.cnName;
        }
        public static String getCnName(String code) {
            for(SHARE_CHANNEL item : SHARE_CHANNEL.values()){
                if(item.getCode().equals(code)) {
                    return item.getCnName();
                }
            }
            return code;
        }
        public String toString() {
            return "code:"+this.name()+",cnName:"+this.cnName;
        }
    }

}
