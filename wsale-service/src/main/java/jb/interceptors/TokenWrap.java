package jb.interceptors;

import jb.pageModel.SessionInfo;

/**
 * Created by john on 16/1/10.
 */
public class TokenWrap  implements java.io.Serializable{
    private String tokenId;
    private SessionInfo sessionInfo;
    private String serverHost;
    private long ctime;//上一次使用时间
    private TokenManage tokenManage;

    public TokenWrap() {
    }

    public TokenWrap(String tokenId, SessionInfo sessionInfo, TokenManage tokenManage){
        this.tokenId = tokenId;
        this.sessionInfo = sessionInfo;
        this.tokenManage = tokenManage;
    }
    public void retime(){
        if(tokenManage == null){
            tokenManage = TokenManage.getTokenManage();
        }
        if (tokenManage != null && tokenManage.enableRedis) {
            tokenManage.refreshRedisToken(tokenId);
        } else {
            ctime = System.currentTimeMillis();
        }
    }
    public String getTokenId() {
        return tokenId;
    }
    public long getCtime() {
        return ctime;
    }


    public String getServerHost() {
        return serverHost;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }


    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public TokenManage getTokenManage() {
        return tokenManage;
    }

    public void setTokenManage(TokenManage tokenManage) {
        this.tokenManage = tokenManage;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }
}