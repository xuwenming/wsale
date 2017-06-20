
/*
 * @author John
 * @date - 2016-08-15
 */

package jb.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "zc_file")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcFile implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcFile";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_OBJECT_TYPE = "对象类型";
	public static final String ALIAS_OBJECT_ID = "对象ID";
	public static final String ALIAS_FILE_TYPE = "文件类型";
	public static final String ALIAS_FILE_ORIGINAL_URL = "原文件URL";
	public static final String ALIAS_FILE_HANDLE_URL = "处理后文件Url";
	public static final String ALIAS_FILE_ORIGINAL_SIZE = "原文件大小";
	public static final String ALIAS_FILE_HANDLE_SIZE = "处理后文件文件大小";
	public static final String ALIAS_DURATION = "时长（S）";
	public static final String ALIAS_ADDTIME = "创建时间";
	
	//date formats
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=18)
	private String objectType;
	//@Length(max=36)
	private String objectId;
	//@Length(max=4)
	private String fileType;
	//@Length(max=100)
	private String fileOriginalUrl;
	//@Length(max=100)
	private String fileHandleUrl;
	//
	private Integer fileOriginalSize;
	//
	private Integer fileHandleSize;
	//
	private Integer duration;
	private Integer seq;
	//
	private Date addtime;
	//columns END


		public TzcFile(){
		}
		public TzcFile(String id) {
			this.id = id;
		}
	

	public void setId(String id) {
		this.id = id;
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}
	
	@Column(name = "object_type", unique = false, nullable = true, insertable = true, updatable = true, length = 18)
	public String getObjectType() {
		return this.objectType;
	}
	
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	
	@Column(name = "object_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getObjectId() {
		return this.objectId;
	}
	
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	
	@Column(name = "file_type", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getFileType() {
		return this.fileType;
	}
	
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	@Column(name = "file_original_url", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public String getFileOriginalUrl() {
		return this.fileOriginalUrl;
	}
	
	public void setFileOriginalUrl(String fileOriginalUrl) {
		this.fileOriginalUrl = fileOriginalUrl;
	}
	
	@Column(name = "file_handle_url", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public String getFileHandleUrl() {
		return this.fileHandleUrl;
	}
	
	public void setFileHandleUrl(String fileHandleUrl) {
		this.fileHandleUrl = fileHandleUrl;
	}
	
	@Column(name = "file_original_size", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getFileOriginalSize() {
		return this.fileOriginalSize;
	}
	
	public void setFileOriginalSize(Integer fileOriginalSize) {
		this.fileOriginalSize = fileOriginalSize;
	}
	
	@Column(name = "file_handle_size", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getFileHandleSize() {
		return this.fileHandleSize;
	}
	
	public void setFileHandleSize(Integer fileHandleSize) {
		this.fileHandleSize = fileHandleSize;
	}
	
	@Column(name = "duration", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getDuration() {
		return this.duration;
	}
	
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@Column(name = "seq", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@Column(name = "addtime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getAddtime() {
		return this.addtime;
	}
	
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ObjectType",getObjectType())
			.append("ObjectId",getObjectId())
			.append("FileType",getFileType())
			.append("FileOriginalUrl",getFileOriginalUrl())
			.append("FileHandleUrl",getFileHandleUrl())
			.append("FileOriginalSize",getFileOriginalSize())
			.append("FileHandleSize",getFileHandleSize())
			.append("Duration",getDuration())
			.append("Addtime",getAddtime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcFile == false) return false;
		if(this == obj) return true;
		ZcFile other = (ZcFile)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

