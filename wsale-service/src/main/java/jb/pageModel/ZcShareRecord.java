package jb.pageModel;

import jb.util.EnumConstants;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcShareRecord implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String bbsId;
	private String shareChannel;
	private String userId;
	private Date addtime;			

	private String userName;

	public String getShareChannelZh() {
		return EnumConstants.SHARE_CHANNEL.getCnName(this.shareChannel);
	}

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}

	
	public void setBbsId(String bbsId) {
		this.bbsId = bbsId;
	}
	
	public String getBbsId() {
		return this.bbsId;
	}
	public void setShareChannel(String shareChannel) {
		this.shareChannel = shareChannel;
	}
	
	public String getShareChannel() {
		return this.shareChannel;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return this.userId;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
