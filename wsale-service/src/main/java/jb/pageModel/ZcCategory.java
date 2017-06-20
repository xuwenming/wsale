package jb.pageModel;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class ZcCategory implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String name;
	private String icon;
	private String summary;
	private Integer seq;
	private String pid;
	private String forumIntroduce;
	private String chiefModeratorId;
	private String addUserId;
	private Date addtime;			
	private String updateUserId;
	private Date updatetime;			
	private Boolean isDeleted;
	private Integer autoRead;

	private String pname;
	private String chiefModeratorName;
	private String addUserName;
	private List<User> moderators;
	private List<ZcCategory> childCategorys;
	private String moderatorsStr; // 版主集合，逗号隔开

	private String state = "open";// open,closed

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}

	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getIcon() {
		return this.icon;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	public Integer getSeq() {
		return this.seq;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public String getPid() {
		return this.pid;
	}
	public void setForumIntroduce(String forumIntroduce) {
		this.forumIntroduce = forumIntroduce;
	}
	
	public String getForumIntroduce() {
		return this.forumIntroduce;
	}
	public void setChiefModeratorId(String chiefModeratorId) {
		this.chiefModeratorId = chiefModeratorId;
	}
	
	public String getChiefModeratorId() {
		return this.chiefModeratorId;
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
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public Integer getAutoRead() {
		return autoRead;
	}

	public void setAutoRead(Integer autoRead) {
		this.autoRead = autoRead;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getChiefModeratorName() {
		return chiefModeratorName;
	}

	public void setChiefModeratorName(String chiefModeratorName) {
		this.chiefModeratorName = chiefModeratorName;
	}

	public String getAddUserName() {
		return addUserName;
	}

	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}

	public List<User> getModerators() {
		return moderators;
	}

	public void setModerators(List<User> moderators) {
		this.moderators = moderators;
	}

	public List<ZcCategory> getChildCategorys() {
		return childCategorys;
	}

	public void setChildCategorys(List<ZcCategory> childCategorys) {
		this.childCategorys = childCategorys;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getModeratorsStr() {
		return moderatorsStr;
	}

	public void setModeratorsStr(String moderatorsStr) {
		this.moderatorsStr = moderatorsStr;
	}

}
