package com.base.modules.gswms.test.bos.webapi.entities;

public class Customer {

	long CustID;
	String FNumber;
	public long getCustID() {
		return CustID;
	}
	public void setCustID(long custID) {
		CustID = custID;
	}
	public String getFNumber() {
		return FNumber;
	}
	public void setFNumber(String fNumber) {
		FNumber = fNumber;
	}
	public String getFName() {
		return FName;
	}
	public void setFName(String fName) {
		FName = fName;
	}
	String FName;
	
	String FShortName;
	public String getFShortName() {
		return FShortName;
	}
	public void setFShortName(String fShortName) {
		FShortName = fShortName;
	}
}
