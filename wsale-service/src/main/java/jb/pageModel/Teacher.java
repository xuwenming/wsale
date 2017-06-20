package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class Teacher implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.String id;	
	private java.lang.String name;	
	private java.lang.Integer age;	
	private java.lang.Integer sex;	
	private java.lang.String position;	

	

	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getId() {
		return this.id;
	}

	
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setAge(java.lang.Integer age) {
		this.age = age;
	}
	
	public java.lang.Integer getAge() {
		return this.age;
	}
	public void setSex(java.lang.Integer sex) {
		this.sex = sex;
	}
	
	public java.lang.Integer getSex() {
		return this.sex;
	}
	public void setPosition(java.lang.String position) {
		this.position = position;
	}
	
	public java.lang.String getPosition() {
		return this.position;
	}

}
