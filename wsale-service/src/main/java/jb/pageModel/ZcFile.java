package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class ZcFile implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String objectType;
	private String objectId;
	private String fileType;
	private String fileOriginalUrl;
	private String fileHandleUrl;
	private Integer fileOriginalSize;
	private Integer fileHandleSize;
	private Integer duration;
	private Integer seq;
	private Date addtime;			

	

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
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public String getFileType() {
		return this.fileType;
	}
	public void setFileOriginalUrl(String fileOriginalUrl) {
		this.fileOriginalUrl = fileOriginalUrl;
	}
	
	public String getFileOriginalUrl() {
		return this.fileOriginalUrl;
	}
	public void setFileHandleUrl(String fileHandleUrl) {
		this.fileHandleUrl = fileHandleUrl;
	}
	
	public String getFileHandleUrl() {
		return this.fileHandleUrl;
	}
	public void setFileOriginalSize(Integer fileOriginalSize) {
		this.fileOriginalSize = fileOriginalSize;
	}
	
	public Integer getFileOriginalSize() {
		return this.fileOriginalSize;
	}
	public void setFileHandleSize(Integer fileHandleSize) {
		this.fileHandleSize = fileHandleSize;
	}
	
	public Integer getFileHandleSize() {
		return this.fileHandleSize;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	public Integer getDuration() {
		return this.duration;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}

}
