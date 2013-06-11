package com.example.tryapp.object;

public class Employee {

	private int id;
	private String name;
	private String eadd;
	private String cellnum;
	private String birthday;
	
	public Employee(int id, String name, String eadd, String cellnum, String birthday){
		this.id = id;
		this.name = name;
		this.eadd = eadd;
		this.cellnum = cellnum;
		this.birthday = birthday;
	}
	
	public Employee(String name, String eadd, String cellnum, String birthday){
		this.name = name;
		this.eadd = eadd;
		this.cellnum = cellnum;
		this.birthday = birthday;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEadd() {
		return eadd;
	}
	
	public String getCellNum() {
		return cellnum;
	}
	
	public String getBirthday() {
		return birthday;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setEadd(String eadd) {
		this.eadd = eadd;
	}
	
	public void setCellNum(String cellnum) {
		this.cellnum = cellnum;
	}
	
	public void setBirthday(String birthday){
		this.birthday = birthday;
	}
	
}
