package model;

import java.util.Date;

public class User {
	public  User(){
		super();
	}
	private String logname;
	private String password;
	private String phone;
	private String address;
	private String realname;
	private Date date;
	public String getLogname() {
		return logname;
	}
	public void setLogname(String logname) {
		this.logname = logname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "User [logname=" + logname + ", password=" + password + ", phone=" + phone + ", address=" + address
				+ ", realname=" + realname + ", date=" + date + "]";
	}
	public User(String logname, String password, String phone, String address, String realname, Date date) {
		super();
		this.logname = logname;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.realname = realname;
		this.date = date;
	}
	

}
