package com.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import com.board.dbconnect.DBConnector;
import com.board.dto.MemberDTO;

public class MemberDAO {
private Scanner sc;
private DBConnector dbConnector;
private Connection con=null;
private PreparedStatement st=null;
private ResultSet rs=null;
public MemberDAO() {
	dbConnector = new DBConnector();
	sc=new Scanner(System.in);
}
    //회원가입
	public String memInsert() {
		 String message="중복된 아이디입니다.";
		try {
			int result=0;
			con=dbConnector.getConnect();
			System.out.println("ID를 입력해주세요");
			String id=sc.next();
			String checkSql="SELECT * FROM MEMBER WHERE ID=?";
			st=con.prepareStatement(checkSql);
			st.setString(1,id);
			rs=st.executeQuery();
			if(rs.next()) {
				return message;
			}else {
				System.out.println("PW를 입력해주세요");
				String pw = sc.next();
				System.out.println("이름을 입력해주세요");
				String name = sc.next();
				String insertSql="INSERT INTO MEMBER VALUES(?,?,?,SYSDATE)";
				st=con.prepareStatement(insertSql);
				st.setString(1, id);
				st.setString(2, pw);
				st.setString(3, name);
				result=st.executeUpdate();
				if(result==1) {
					message="회원가입 성공!";
				}
			}
		} catch (Exception e) {			
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				st.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return message;
	}//회원가입
	
	public MemberDTO memLogin() {
		MemberDTO  memberDTO= null;
		try {
			con=dbConnector.getConnect();
			System.out.println("ID를 입력해주세요");
			String id=sc.next();
			System.out.println("PW를 입력해주세요");
			String pw = sc.next();
			String sql="SELECT * FROM MEMBER WHERE ID=? AND PW=?";
			st=con.prepareStatement(sql);
			st.setString(1, id);
			st.setString(2, pw);
			rs=st.executeQuery();
			if(rs.next()) {
				memberDTO=new MemberDTO();
				memberDTO.setId(rs.getString("ID"));
				memberDTO.setPw(rs.getString("PW"));
				memberDTO.setName(rs.getString("NAME"));
				memberDTO.setR_date(rs.getDate("R_DATE"));
				memberDTO.setAdminflag(rs.getInt("ADMINFLAG"));
			}
		} catch (Exception e) {
		   e.printStackTrace();
		}finally {
			try {
				rs.close();
				st.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return memberDTO;
	}
	public Boolean memDelete(MemberDTO memberDTO) {
		Boolean deleteCheck=false;
		int result =0;
		try {
			con=dbConnector.getConnect();
		    System.out.println("비밀번호를 입력해주세요");
		    String pw = sc.next();
		    if(pw.equals(memberDTO.getPw())) {
		    	String sql ="DELETE FROM MEMBER WHERE ID=?";
		    	st=con.prepareStatement(sql);
		    	st.setString(1, memberDTO.getId());
		    	result=st.executeUpdate();
		    	if(result==1) {
		    	  deleteCheck=true;
		    	}
		    }
		    
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {				
				st.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return deleteCheck;
	}
	public Boolean changePw(MemberDTO memberDTO) {
		Boolean changeCheck=false;
		int result =0;
		try {
			con=dbConnector.getConnect();
			System.out.println("새로운 비밀번호를 입력해주세요");
			String pw1=sc.next();
			System.out.println("새로운 비밀번호확인을 위해 한번 더 입력해주세요");
			String pw2=sc.next();
			if(pw1.equals(pw2)) {
				String sql="UPDATE MEMBER SET PW=? WHERE ID=?";
				st=con.prepareStatement(sql);
				st.setString(1, pw1);
				st.setString(2, memberDTO.getId());
				result=st.executeUpdate();
				if(result==1) {
					changeCheck=true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {				
				st.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return changeCheck;
	}
}
