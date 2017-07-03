package jb.pageModel;

import jb.absx.F;
import jb.listener.Application;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class ZcProduct implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String pno;
	private String categoryId;
	private Date deadline;			
	private Date realDeadline;			
	private Double startingPrice;
	private String approvalDays;
	private Boolean isFreeShipping;
	private Double margin;
	private Boolean isNeedRealId;
	private Boolean isNeedProtectionPrice;
	private Date startingTime;			
	private Double fixedPrice;
	private Double referencePrice;
	private String content;
	private String status;
	private Boolean isClose;
	private Integer readCount;
	private Integer likeCount;
	private Integer shareCount;
	private Double currentPrice;
	private String userId;
	private Double hammerPrice;
	private Date hammerTime;			
	private Boolean isDeleted;
	private Integer seq;
	private String addUserId;
	private Date addtime;			
	private String updateUserId;
	private Date updatetime;			

	private String mediaIds;
	private List<ZcFile> files;
	private String icon;
	private boolean liked;

	private String cname;
	private String addUserName;
	private String userName; // 成交人

	private long deadlineLen;
	private Boolean others;
	private Integer qtype; // 查询类型1：新品开拍 2：即将截拍
	private boolean refund; // 是否退回非成交人的保证金
	private Boolean marginFlag;

	private Integer remindLen; // 距离截拍剩余时长提醒，分钟

	private String atteId; // 关注人id,查找关注人拍品列表

	public String getStatusZh() {
		return Application.getString(this.status);
	}
	public String getApprovalDaysZh() {
		return Application.getString(this.approvalDays);
	}
	public String getIsFreeShippingZh() {
		return this.isFreeShipping == true ? "是" : "否";
	}

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}

	
	public void setPno(String pno) {
		this.pno = pno;
	}
	
	public String getPno() {
		return this.pno;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getCategoryId() {
		return this.categoryId;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
	public Date getDeadline() {
		return this.deadline;
	}
	public void setRealDeadline(Date realDeadline) {
		this.realDeadline = realDeadline;
	}
	
	public Date getRealDeadline() {
		return this.realDeadline;
	}
	public void setStartingPrice(Double startingPrice) {
		this.startingPrice = startingPrice;
	}
	
	public Double getStartingPrice() {
		return this.startingPrice;
	}
	public void setApprovalDays(String approvalDays) {
		this.approvalDays = approvalDays;
	}
	
	public String getApprovalDays() {
		return this.approvalDays;
	}
	public void setIsFreeShipping(Boolean isFreeShipping) {
		this.isFreeShipping = isFreeShipping;
	}
	
	public Boolean getIsFreeShipping() {
		return this.isFreeShipping;
	}
	public void setMargin(Double margin) {
		this.margin = margin;
	}
	
	public Double getMargin() {
		return this.margin;
	}
	public void setIsNeedRealId(Boolean isNeedRealId) {
		this.isNeedRealId = isNeedRealId;
	}
	
	public Boolean getIsNeedRealId() {
		return this.isNeedRealId;
	}
	public void setIsNeedProtectionPrice(Boolean isNeedProtectionPrice) {
		this.isNeedProtectionPrice = isNeedProtectionPrice;
	}
	
	public Boolean getIsNeedProtectionPrice() {
		return this.isNeedProtectionPrice;
	}
	public void setStartingTime(Date startingTime) {
		this.startingTime = startingTime;
	}
	
	public Date getStartingTime() {
		return this.startingTime;
	}
	public void setFixedPrice(Double fixedPrice) {
		this.fixedPrice = fixedPrice;
	}
	
	public Double getFixedPrice() {
		return this.fixedPrice;
	}
	public void setReferencePrice(Double referencePrice) {
		this.referencePrice = referencePrice;
	}
	
	public Double getReferencePrice() {
		return this.referencePrice;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		if(!F.empty(this.content))
			this.content = this.content.replaceAll(Application.getSWordReg(), "***");
		return this.content;
	}
	public String getContentLine() {
		if(!F.empty(this.content))
			return this.content.replaceAll(Application.getSWordReg(), "***").replaceAll("[\\r\\n]", "");
		return this.content;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setIsClose(Boolean isClose) {
		this.isClose = isClose;
	}
	
	public Boolean getIsClose() {
		return this.isClose;
	}
	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}
	
	public Integer getReadCount() {
		return this.readCount;
	}
	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}
	
	public Integer getLikeCount() {
		return this.likeCount;
	}
	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}
	
	public Integer getShareCount() {
		return this.shareCount;
	}
	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}
	
	public Double getCurrentPrice() {
		return this.currentPrice;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return this.userId;
	}
	public void setHammerPrice(Double hammerPrice) {
		this.hammerPrice = hammerPrice;
	}
	
	public Double getHammerPrice() {
		return this.hammerPrice;
	}
	public void setHammerTime(Date hammerTime) {
		this.hammerTime = hammerTime;
	}
	
	public Date getHammerTime() {
		return this.hammerTime;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}

	public long getDeadlineLen() {
		return deadlineLen;
	}

	public void setDeadlineLen(long deadlineLen) {
		this.deadlineLen = deadlineLen;
	}

	public Boolean getOthers() {
		return others;
	}

	public void setOthers(Boolean others) {
		this.others = others;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Integer getQtype() {
		return qtype;
	}

	public void setQtype(Integer qtype) {
		this.qtype = qtype;
	}

	public boolean isRefund() {
		return refund;
	}

	public void setRefund(boolean refund) {
		this.refund = refund;
	}

	public Boolean getMarginFlag() {
		return marginFlag;
	}

	public void setMarginFlag(Boolean marginFlag) {
		this.marginFlag = marginFlag;
	}

	public String getAddUserName() {
		return addUserName;
	}

	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}

	public Integer getRemindLen() {
		return remindLen;
	}

	public void setRemindLen(Integer remindLen) {
		this.remindLen = remindLen;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAtteId() {
		return atteId;
	}

	public void setAtteId(String atteId) {
		this.atteId = atteId;
	}
}
