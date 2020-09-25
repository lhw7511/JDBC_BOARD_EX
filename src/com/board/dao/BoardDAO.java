package com.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.board.dbconnect.DBConnector;
import com.board.dto.BoardDTO;
import com.board.dto.MemberDTO;

public class BoardDAO {
	private Scanner sc1;
	private Scanner sc2;
	private DBConnector dbConnector;
	private Connection con=null;
	private PreparedStatement st=null;
	private ResultSet rs=null;
public BoardDAO() {
	dbConnector = new DBConnector();
	sc1= new Scanner(System.in);
	sc2= new Scanner(System.in);		
}
    //글 업로드
	public String boardInsert(MemberDTO memberDTO) {
		String message="";		
		try {
			int result=0;
			con=dbConnector.getConnect();
			
			System.out.println("글 제목을 작성해주세요");
			String title = sc2.nextLine();
		 
			System.out.println("글 내용을 작성해주세요");
			String content = sc2.nextLine();
			String sql="INSERT INTO BOARD VALUES(SEQ_BOARD.NEXTVAL,?,?,?,SYSDATE)";
			st=con.prepareStatement(sql);
			st.setString(1, title);
			st.setString(2, content);
			st.setString(3, memberDTO.getId());
			result=st.executeUpdate();
			if(result==1) {
				message="게시글이 업로드되었습니다!";
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
	//글 목록 약식조회
	public List<BoardDTO> boardRead() {
		List<BoardDTO> boardDTOs = new ArrayList<>();
		try {
			con=dbConnector.getConnect();
			String sql="SELECT B_ID,SUBSTR(TITLE,1,5) TITLE,SUBSTR(CONTENT,1,10) CONTENT,ID,UP_DATE FROM BOARD"
					+ " ORDER BY B_ID DESC";
			st=con.prepareStatement(sql);
			rs=st.executeQuery();
			while(rs.next()) {
				BoardDTO boardDTO = new BoardDTO();
				boardDTO.setB_id(rs.getInt("B_ID"));
				boardDTO.setTitle(rs.getString("TITLE"));
				boardDTO.setContent(rs.getString("CONTENT"));
				boardDTO.setId(rs.getString("ID"));
				boardDTO.setUp_date(rs.getDate("UP_DATE"));
				boardDTOs.add(boardDTO);
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
		return boardDTOs;
	}
	
	//번호로검색
	public BoardDTO boardNumSearch() {
		BoardDTO boardDTO =null;
		try {
			con = dbConnector.getConnect();
			System.out.println("검색할 글번호를 입력해주세요");
			int b_id=sc1.nextInt();
			String sql="SELECT * FROM BOARD WHERE B_ID=?";
			st=con.prepareStatement(sql);
			st.setInt(1, b_id);
			rs=st.executeQuery();
			if(rs.next()) {
				boardDTO=new BoardDTO();
				boardDTO.setB_id(rs.getInt("B_ID"));
				boardDTO.setTitle(rs.getString("TITLE"));
				boardDTO.setContent(rs.getString("CONTENT"));
				boardDTO.setId(rs.getString("ID"));
				boardDTO.setUp_date(rs.getDate("UP_DATE"));
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
		return boardDTO;
	}
	//제목으로검색
	public List<BoardDTO> boardTitleSearch() {
		List<BoardDTO> boardDTOs = new ArrayList<>();
		try {
			con = dbConnector.getConnect();
			System.out.println("제목으로 검색해주세요");
			String title=sc2.nextLine();
			String sql="SELECT * FROM BOARD WHERE TITLE LIKE '%'||?||'%'  ORDER BY B_ID DESC";
			st=con.prepareStatement(sql);
			st.setString(1, title);
			rs=st.executeQuery();
			while(rs.next()) {
				BoardDTO boardDTO =new BoardDTO();
				boardDTO.setB_id(rs.getInt("B_ID"));
				boardDTO.setTitle(rs.getString("TITLE"));
				boardDTO.setContent(rs.getString("CONTENT"));
				boardDTO.setId(rs.getString("ID"));
				boardDTO.setUp_date(rs.getDate("UP_DATE"));
				boardDTOs.add(boardDTO);
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
		return boardDTOs;
	}
	//마이페이지 내가 쓴글 목록
	public List<BoardDTO> myBoard(MemberDTO memberDTO){
		List<BoardDTO> boardDTOs = new ArrayList<>();
		try {
			con=dbConnector.getConnect();
			String sql="SELECT B_ID,SUBSTR(TITLE,1,8) TITLE,SUBSTR(CONTENT,1,10) CONTENT,ID,UP_DATE FROM BOARD WHERE ID=? ORDER BY B_ID DESC";
			st=con.prepareStatement(sql);
			st.setString(1,memberDTO.getId());
			rs=st.executeQuery();
			while(rs.next()) {
				BoardDTO boardDTO =new BoardDTO();
				boardDTO.setB_id(rs.getInt("B_ID"));
				boardDTO.setTitle(rs.getString("TITLE"));
				boardDTO.setContent(rs.getString("CONTENT"));
				boardDTO.setId(rs.getString("ID"));
				boardDTO.setUp_date(rs.getDate("UP_DATE"));
				boardDTOs.add(boardDTO);
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
		return boardDTOs;
	}
	//내가 쓴 글 삭제
	public String myBoardDelete(MemberDTO memberDTO) {
		String message="삭제권한이 없거나 해당하는 번호가 없습니다";
		int result=0;
		try {
			con=dbConnector.getConnect();
			System.out.println("삭제할 글의 번호를 입력해주세요");
			int b_id=sc1.nextInt();
			String sql="DELETE FROM BOARD WHERE B_ID=? AND ID=?";
			st=con.prepareStatement(sql);
			st.setInt(1, b_id);
			st.setString(2, memberDTO.getId());
			result=st.executeUpdate();
			if(result==1) {
				message="삭제되었습니다";
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
	}
	public String myBoardUpdate(MemberDTO memberDTO) {
	
		String message="수정권한이 없거나 해당하는 번호가 없습니다";
		int result=0;
		try {
			con=dbConnector.getConnect();
			System.out.println("수정할 글의 번호를 입력해주세요");
			int b_id = sc1.nextInt();
			String checkSql="SELECT * FROM BOARD WHERE B_ID=? AND ID=?";
			st=con.prepareStatement(checkSql);
			st.setInt(1, b_id);
			st.setString(2, memberDTO.getId());
			rs=st.executeQuery();
			if(rs.next()) {		
				System.out.println("글의 내용을 수정합니다 다시 작성해주세요");
				String content = sc2.nextLine();
				String sql="UPDATE BOARD SET UP_DATE=SYSDATE,CONTENT =? WHERE B_ID=? AND ID=?";
				st=con.prepareStatement(sql);
				st.setString(1, content);
				st.setInt(2, b_id);
				st.setString(3, memberDTO.getId());
				result=st.executeUpdate();
			}
			if(result==1) {
				message="수정되었습니다";
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
	}
}
