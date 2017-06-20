package jb.pageModel;

import jb.util.Constants;
import jb.util.EnumConstants;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcReport implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String objectType;
	private String objectId;
	private String reportReason;
	private String userId;
	private Date addtime;
	private java.lang.String handleStatus;
	private java.lang.String handleUserId;
	private java.lang.String handleRemark;
	private Date handleTime;

	private String userName;
	private String ObjectName;
	private Date addtimeBegin;
	private Date addtimeEnd;

	public String getObjectTypeZh() {
		return EnumConstants.OBJECT_TYPE.getCnName(this.objectType);
	}

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}

	
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	
	public String getObjectType() {
		return this.objectType;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	
	public String getObjectId() {
		return this.objectId;
	}
	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}
	
	public String getReportReason() {
		return this.reportReason;
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

	public String getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}

	public String getHandleUserId() {
		return handleUserId;
	}

	public void setHandleUserId(String handleUserId) {
		this.handleUserId = handleUserId;
	}

	public String getHandleRemark() {
		return handleRemark;
	}

	public void setHandleRemark(String handleRemark) {
		this.handleRemark = handleRemark;
	}

	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getObjectName() {
		return ObjectName;
	}

	public void setObjectName(String objectName) {
		ObjectName = objectName;
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
}
