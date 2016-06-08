package com.ben.hibernate.entries;
/*
 * 值类型  无Id
 */
public class Pay {
	private int monthlyPay;
	private int yearPay;
	private int vocationWithPay;
	
	private Worker worker;
	
	
	public Worker getWorker() {
		return worker;
	}
	public void setWorker(Worker worker) {
		this.worker = worker;
	}
	public int getMonthlyPay() {
		return monthlyPay;
	}
	public void setMonthlyPay(int monthlyPay) {
		this.monthlyPay = monthlyPay;
	}
	public int getYearPay() {
		return yearPay;
	}
	public void setYearPay(int yearPay) {
		this.yearPay = yearPay;
	}
	public int getVocationWithPay() {
		return vocationWithPay;
	}
	public void setVocationWithPay(int vocationWithPay) {
		this.vocationWithPay = vocationWithPay;
	}
	@Override
	public String toString() {
		return "Pay [monthlyPay=" + monthlyPay + ", yearPay=" + yearPay + ", vocationWithPay=" + vocationWithPay + "]";
	}
	
}
