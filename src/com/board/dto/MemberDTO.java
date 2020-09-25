package com.board.dto;

import java.util.Date;

public class MemberDTO {

	public MemberDTO() {

	}

	private String id;
	private String pw;
	private String name;
	private Date r_date;
	private int adminflag;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getR_date() {
		return r_date;
	}

	public void setR_date(Date r_date) {
		this.r_date = r_date;
	}

	public int getAdminflag() {
		return adminflag;
	}

	public void setAdminflag(int adminflag) {
		this.adminflag = adminflag;
	}

}
