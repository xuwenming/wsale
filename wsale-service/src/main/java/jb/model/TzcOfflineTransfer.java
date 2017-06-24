
/*
 * @author John
 * @date - 2017-03-24
 */

package jb.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "zc_offline_transfer")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TzcOfflineTransfer implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ZcOfflineTransfer";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_USER_ID = "转账人ID";
	public static final String ALIAS_TRANSFER_USER_NAME = "汇款人姓名";
	public static final String ALIAS_TRANSFER_AMOUNT = "汇款金额";
	public static final String ALIAS_TRANSFER_TIME = "汇款时间";
	public static final String ALIAS_REMARK = "汇款备注";
	public static final String ALIAS_HANDLE_STATUS = "处理状态";
	public static final String ALIAS_HANDLE_USER_ID = "处理人";
	public static final String ALIAS_HANDLE_REMARK = "处理结果";
	public static final String ALIAS_HANDLE_TIME = "处理时间";
	public static final String ALIAS_ADDTIME = "创建时间";
	
	//date formats
	public static final String FORMAT_TRANSFER_TIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_HANDLE_TIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_ADDTIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	private String transferNo;
	//@Length(max=36)
	private String userId;
	//@Length(max=6)
	private String transferUserName;
	//
	private Double transferAmount;
	//
	private Date transferTime;
	//@Length(max=128)
	private String remark;
	private String bankCode;
	//@Length(max=4)
	private String handleStatus;
	//@Length(max=36)
	private String handleUserId;
	//@Length(max=300)
	private String handleRemark;
	//
	private Date handleTime;
	private Boolean isWallet;
	//
	private Date addtime;
	//columns END


		public TzcOfflineTransfer(){
		}
		public TzcOfflineTransfer(String id) {
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

	@Column(name = "transfer_no", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getTransferNo() {
		return transferNo;
	}

	public void setTransferNo(String transferNo) {
		this.transferNo = transferNo;
	}

	@Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name = "transfer_user_name", unique = false, nullable = true, insertable = true, updatable = true, length = 6)
	public String getTransferUserName() {
		return this.transferUserName;
	}
	
	public void setTransferUserName(String transferUserName) {
		this.transferUserName = transferUserName;
	}
	
	@Column(name = "transfer_amount", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	public Double getTransferAmount() {
		return this.transferAmount;
	}
	
	public void setTransferAmount(Double transferAmount) {
		this.transferAmount = transferAmount;
	}
	

	@Column(name = "transfer_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getTransferTime() {
		return this.transferTime;
	}
	
	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}
	
	@Column(name = "remark", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "bank_code", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "handle_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getHandleStatus() {
		return this.handleStatus;
	}
	
	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}
	
	@Column(name = "handle_user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getHandleUserId() {
		return this.handleUserId;
	}
	
	public void setHandleUserId(String handleUserId) {
		this.handleUserId = handleUserId;
	}
	
	@Column(name = "handle_remark", unique = false, nullable = true, insertable = true, updatable = true, length = 300)
	public String getHandleRemark() {
		return this.handleRemark;
	}
	
	public void setHandleRemark(String handleRemark) {
		this.handleRemark = handleRemark;
	}
	

	@Column(name = "handle_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getHandleTime() {
		return this.handleTime;
	}
	
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	@Column(name = "is_wallet", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsWallet() {
		return isWallet;
	}

	public void setIsWallet(Boolean isWallet) {
		this.isWallet = isWallet;
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
			.append("UserId",getUserId())
			.append("TransferUserName",getTransferUserName())
			.append("TransferAmount",getTransferAmount())
			.append("TransferTime",getTransferTime())
			.append("Remark",getRemark())
			.append("HandleStatus",getHandleStatus())
			.append("HandleUserId",getHandleUserId())
			.append("HandleRemark",getHandleRemark())
			.append("HandleTime",getHandleTime())
			.append("Addtime",getAddtime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ZcOfflineTransfer == false) return false;
		if(this == obj) return true;
		ZcOfflineTransfer other = (ZcOfflineTransfer)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

