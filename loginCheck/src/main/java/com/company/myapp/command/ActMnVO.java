package com.company.myapp.command;

public class ActMnVO {

	private	String  usr_id;
	private	String  pwd_no_enc_cntn;
	private	String  usr_nm;		
	private Integer usr_count; // 오류 횟수
	private String  usr_lck;   // 기본으로 들어가는 값이 'N' 
	
	public String getUsr_id() {
		return usr_id;
	}
	public void setUsr_id(String usr_id) {
		this.usr_id = usr_id;
	}
	public String getPwd_no_enc_cntn() {
		return pwd_no_enc_cntn;
	}
	public void setPwd_no_enc_cntn(String pwd_no_enc_cntn) {
		this.pwd_no_enc_cntn = pwd_no_enc_cntn;
	}
	public String getUsr_nm() {
		return usr_nm;
	}
	public void setUsr_nm(String usr_nm) {
		this.usr_nm = usr_nm;
	}
	public Integer getUsr_count() {
		return usr_count;
	}
	public void setUsr_count(Integer usr_count) {
		this.usr_count = usr_count;
	}
	public String getUsr_lck() {
		return usr_lck;
	}
	public void setUsr_lck(String usr_lck) {
		this.usr_lck = usr_lck;
	}
	
	@Override
	public String toString() {
		return "usr_id=" + usr_id + ", pwd_no_enc_cntn=" + pwd_no_enc_cntn + ", usr_nm=" + usr_nm
				+ ", usr_count=" + usr_count + ", usr_lck=" + usr_lck;
	}

}