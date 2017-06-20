package jb.pageModel;

import jb.absx.F;
import jb.listener.Application;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class ZcForumBbs implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String categoryId;
	private String bbsTitle;
	private String bbsContent;
	private String bbsType;
	private String bbsStatus;
	private Boolean isDeleted;
	private Boolean isOffReply;
	private Boolean isTop;
	private Boolean isLight;
	private Boolean isEssence;
	private String addUserId;
	private Date addtime;			
	private String updateUserId;
	private Date updatetime;			
	private Integer bbsComment;
	private Integer bbsRead;
	private Integer bbsReward;
	private Integer bbsShare;
	private Integer bbsListen;
	private Date lastCommentTime;
	private String lastUpdateUserId;
	private Date lastUpdateTime;
	private Boolean isHomeHot;
	private Integer seq;

	private String mediaIds; // 微信服务器图片文件ID
	private String addUserName;
	private String categoryName;
	private List<ZcFile> files;
	private List<ZcFile> voiceFiles;
	private String audioUrl;
	private String icon; // 封面
	private String themeType; // 主题类型：TEXT-文字主题；AUDIO-声音主题

	private Date addtimeBegin;
	private Date addtimeEnd;
	private Boolean isHot;
	private String lastUpdateUserName;
	private String atteId; // 关注人id,查找关注人帖子列表
	private String logUserId;

	public String getBbsTypeZh() {
		return Application.getString(this.bbsType);
	}

	public String getBbsStatusZh() {
		return Application.getString(this.bbsStatus);
	}

	public String getIsOffReplyZh() {
		return this.isOffReply == true ? "是" : "否";
	}

	public String getIsTopZh() {
		return this.isTop == true ? "是" : "否";
	}

	public String getIsLightZh() {
		return this.isLight == true ? "是" : "否";
	}

	public String getIsEssenceZh() {
		return this.isEssence == true ? "是" : "否";
	}
	public String getIsHomeHotZh() {
		return this.isHomeHot == true ? "是" : "否";
	}

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getCategoryId() {
		return this.categoryId;
	}
	public void setBbsTitle(String bbsTitle) {
		this.bbsTitle = bbsTitle;
	}
	
	public String getBbsTitle() {
		return this.bbsTitle;
	}
	public void setBbsContent(String bbsContent) {
		this.bbsContent = bbsContent;
	}
	
	public String getBbsContent() {
		if(!F.empty(this.bbsContent))
			this.bbsContent = this.bbsContent.replaceAll(Application.getSWordReg(), "***");
		return this.bbsContent;
	}

	public String getBbsContentLine() {
		if(!F.empty(this.bbsContent))
			return this.bbsContent.replaceAll(Application.getSWordReg(), "***").replaceAll("[\\r\\n]", "");
		return this.bbsContent;
	}
	public void setBbsType(String bbsType) {
		this.bbsType = bbsType;
	}
	
	public String getBbsType() {
		return this.bbsType;
	}
	public void setBbsStatus(String bbsStatus) {
		this.bbsStatus = bbsStatus;
	}
	
	public String getBbsStatus() {
		return this.bbsStatus;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}
	public void setIsOffReply(Boolean isOffReply) {
		this.isOffReply = isOffReply;
	}
	
	public Boolean getIsOffReply() {
		return this.isOffReply;
	}
	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}
	
	public Boolean getIsTop() {
		return this.isTop;
	}
	public void setIsLight(Boolean isLight) {
		this.isLight = isLight;
	}
	
	public Boolean getIsLight() {
		return this.isLight;
	}
	public void setIsEssence(Boolean isEssence) {
		this.isEssence = isEssence;
	}
	
	public Boolean getIsEssence() {
		return this.isEssence;
	}
	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
	
	public String getAddUserId() {
		return this.addUserId;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	
	public String getUpdateUserId() {
		return this.updateUserId;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	public Date getUpdatetime() {
		return this.updatetime;
	}
	public void setBbsComment(Integer bbsComment) {
		this.bbsComment = bbsComment;
	}
	
	public Integer getBbsComment() {
		return this.bbsComment;
	}
	public void setBbsRead(Integer bbsRead) {
		this.bbsRead = bbsRead;
	}
	
	public Integer getBbsRead() {
		return this.bbsRead;
	}
	public void setBbsReward(Integer bbsReward) {
		this.bbsReward = bbsReward;
	}
	
	public Integer getBbsReward() {
		return this.bbsReward;
	}
	public void setBbsShare(Integer bbsShare) {
		this.bbsShare = bbsShare;
	}
	
	public Integer getBbsShare() {
		return this.bbsShare;
	}
	public void setBbsListen(Integer bbsListen) {
		this.bbsListen = bbsListen;
	}
	
	public Integer getBbsListen() {
		return this.bbsListen;
	}

	public Date getLastCommentTime() {
		return lastCommentTime;
	}

	public void setLastCommentTime(Date lastCommentTime) {
		this.lastCommentTime = lastCommentTime;
	}

	public String getLastUpdateUserId() {
		return lastUpdateUserId;
	}

	public void setLastUpdateUserId(String lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Boolean getIsHomeHot() {
		return isHomeHot;
	}

	public void setIsHomeHot(Boolean isHomeHot) {
		this.isHomeHot = isHomeHot;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getAddUserName() {
		return addUserName;
	}

	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getMediaIds() {
		return mediaIds;
	}

	public void setMediaIds(String mediaIds) {
		this.mediaIds = mediaIds;
	}

	public List<ZcFile> getFiles() {
		return files;
	}

	public void setFiles(List<ZcFile> files) {
		this.files = files;
	}

	public List<ZcFile> getVoiceFiles() {
		return voiceFiles;
	}

	public void setVoiceFiles(List<ZcFile> voiceFiles) {
		this.voiceFiles = voiceFiles;
	}

	public String getAudioUrl() {
		return audioUrl;
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getThemeType() {
		return themeType;
	}

	public void setThemeType(String themeType) {
		this.themeType = themeType;
	}

	public Date getAddtimeBegin() {
		return addtimeBegin;
	}

	public void setAddtimeBegin(Date addtimeBegin) {
		this.addtimeBegin = addtimeBegin;
	}

	public Date getAddtimeEnd() {
		return addtimeEnd;
	}

	public void setAddtimeEnd(Date addtimeEnd) {
		this.addtimeEnd = addtimeEnd;
	}

	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}

	public String getLastUpdateUserName() {
		return lastUpdateUserName;
	}

	public void setLastUpdateUserName(String lastUpdateUserName) {
		this.lastUpdateUserName = lastUpdateUserName;
	}

	public String getAtteId() {
		return atteId;
	}

	public void setAtteId(String atteId) {
		this.atteId = atteId;
	}

	public String getLogUserId() {
		return logUserId;
	}

	public void setLogUserId(String logUserId) {
		this.logUserId = logUserId;
	}
}
