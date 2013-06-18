package com.example.tryapp.object;

public class HourlyEmployee extends Employee{
	
	private double wage;
	private int hours;

	public HourlyEmployee(String name, String eadd, String cellnum, String birthday, double income, int type) {
		super(name, eadd, cellnum, birthday, income, type);
	}
	
	public HourlyEmployee(String name, String eadd, String cellnum, String birthday, double income, int type, double wage, int hours) {
		super(name, eadd, cellnum, birthday, income, type);
		this.wage = wage;
		this.hours = hours;
	}
	
	public double getWage() {
		return wage;
	}
	
	public int getHours() {
		return hours;
	}
	
	public void setWage(double wage) {
		this.wage = wage;
	}
	
	public void setHours(int hours) {
		this.hours = hours;
	}

}
