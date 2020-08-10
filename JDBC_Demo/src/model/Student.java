package model;

import java.util.Date;

public class Student {
	private int id;
	private String sname;
	private int age;
	@Override
	public String toString() {
		return "Student [id=" + id + ", sname=" + sname + ", age=" + age + "]";
	}
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Student(int id, String sname, int age) {
		super();
		this.id = id;
		this.sname = sname;
		this.age = age;
	}
	
	
}
