
/*
 * @author John
 * @date - 2016-08-24
 */

package jb.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "teacher")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tteacher implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Teacher";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_AGE = "age";
	public static final String ALIAS_SEX = "sex";
	public static final String ALIAS_POSITION = "position";
	
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private java.lang.String id;
	//@Length(max=50)
	private java.lang.String name;
	//
	private java.lang.Integer age;
	//
	private java.lang.Integer sex;
	//@Length(max=50)
	private java.lang.String position;
	//columns END


		public Tteacher(){
		}
		public Tteacher(String id) {
			this.id = id;
		}
	

	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public java.lang.String getId() {
		return this.id;
	}
	
	@Column(name = "name", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	@Column(name = "age", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getAge() {
		return this.age;
	}
	
	public void setAge(java.lang.Integer age) {
		this.age = age;
	}
	
	@Column(name = "sex", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getSex() {
		return this.sex;
	}
	
	public void setSex(java.lang.Integer sex) {
		this.sex = sex;
	}
	
	@Column(name = "position", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public java.lang.String getPosition() {
		return this.position;
	}
	
	public void setPosition(java.lang.String position) {
		this.position = position;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("Age",getAge())
			.append("Sex",getSex())
			.append("Position",getPosition())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Teacher == false) return false;
		if(this == obj) return true;
		Teacher other = (Teacher)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

