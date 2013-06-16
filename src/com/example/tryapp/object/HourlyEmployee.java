package com.example.tryapp.object;

public class HourlyEmployee extends Employee{
	
	private double wage;
	private int hours;

	public HourlyEmployee(String name, String eadd, String cellnum, String birthday, double income) {
		super(name, eadd, cellnum, birthday, income);
	}
	
	public HourlyEmployee(String name, String eadd, String cellnum, String birthday, double income, double wage, int hours) {
		super(name, eadd, cellnum, birthday, income);
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
