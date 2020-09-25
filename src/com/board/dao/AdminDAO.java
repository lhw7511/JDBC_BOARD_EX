package com.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.board.dbconnect.DBConnector;
import com.board.dto.MemberDTO;

public class AdminDAO {
	private Scanner sc;
	private DBConnector dbConnector;
	private Connection con=null;
	private PreparedStatement st=null;
	private ResultSet rs=null;
	public AdminDAO() {
		dbConnector = new DBConnector();
		sc=new Scanner(System.in);
	}
	//회원정보조회
	public List<MemberDTO> memberRead() {
		List<MemberDTO> memberDTOs = new ArrayList<>();
		MemberDTO memberDTO =null;
		try {
			con=dbConnector.getConnect();
			String sql="SELECT * FROM MEMBER WHERE ADMINFLAG=0";
			st=con.prepareStatement(sql);
			rs=st.executeQuery();
			while(rs.next()) {
				memberDTO=new MemberDTO();
				memberDTO.setId(rs.getString("ID"));
				memberDTO.setPw(rs.getString("PW"));
				memberDTO.setName(rs.getString("NAME"));
				memberDTO.setR_date(rs.getDate("R_DATE"));
				memberDTO.setAdminflag(rs.getInt("ADMINFLAG"));
				memberDTOs.add(memberDTO);
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
		return memberDTOs;
	}//
	public String boardDelete() {
		String message="삭제할 번호의 게시글이 없습니다";
		System.out.println("삭제할 글의 번호를 입력해주세요");
		int result=0;
		int num =sc.nextInt();
		try {
			con=dbConnector.getConnect();
			String sql="DELETE FROM BOARD WHERE B_ID=?";
			st=con.prepareStatement(sql);
			st.setInt(1, num);
			result=st.executeUpdate();
			if(result==1) {
				message="삭제되었습니다";
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
		return message;
	}	
	
}
