package com.example.tryapp.object;

public class Employee {

	private int id;
	private int type;
	private String name;
	private String eadd;
	private String cellnum;
	private String birthday;
	private double income;
	
	public Employee(int id, String name, String eadd, String cellnum, String birthday, double income, int type){
		this.id = id;
		this.name = name;
		this.eadd = eadd;
		this.cellnum = cellnum;
		this.birthday = birthday;
		this.income = income;
		this.type = type;
	}
	
	public Employee(String name, String eadd, String cellnum, String birthday, double income, int type){
		this.name = name;
		this.eadd = eadd;
		this.cellnum = cellnum;
		this.birthday = birthday;
		this.income = income;
		this.type = type;
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
	
	public Double getIncome() {
		return income;
	}
	
	public int getType() {
		return type;
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
	
	public void setIncome(Double income) {
		this.income = income;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
}
