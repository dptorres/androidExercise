package com.example.tryapp.object;

public class Executive extends Employee{

	private int percent;
	private double salary;
	
	public Executive(String name, String eadd, String cellnum, String birthday, double income, int type) {
		super(name, eadd, cellnum, birthday, income, type);
	}

	public Executive(String name, String eadd, String cellnum, String birthday, double income, int type, int percent, double salary) {
		super(name, eadd, cellnum, birthday, income, type);
		this.percent = percent;
		this.salary = salary;
	}
	
	public int getPercent() {
		return percent;
	}
	
	public double getSalary() {
		return salary;
	}
	
	public void setPercent(int percent) {
		this.percent = percent;
	}
	
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
}
