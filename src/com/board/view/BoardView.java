package com.board.view;

import java.text.SimpleDateFormat;
import java.util.List;

import com.board.dto.BoardDTO;
import com.board.dto.MemberDTO;

public class BoardView {
 private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public void view(String message) {
		System.out.println(message);
	}

	public void view(List<BoardDTO> boardDTOs) {
		System.out.println("번호\t아이디\t\t날짜\t\t\t제목\t\t내용");
		for (BoardDTO boardDTO : boardDTOs) {
			System.out.print(boardDTO.getB_id() + "\t");
			System.out.print(boardDTO.getId() + "\t\t");
			System.out.print(boardDTO.getUp_date() + "\t\t");
			System.out.print(boardDTO.getTitle() + "\t\t");
			System.out.println(boardDTO.getContent() + "...");
            System.out.println("=================================================================================================");
		}
	}
	public void searchView(BoardDTO boardDTO) {
		System.out.println("번호 : "+boardDTO.getB_id());
		System.out.println("아이디 : "+boardDTO.getId());
		System.out.println("날짜 : "+dateFormat.format(boardDTO.getUp_date()));
		System.out.println("제목 : "+boardDTO.getTitle());		
		System.out.println("내용");
	    System.out.println(boardDTO.getContent());
			
		
	}
	public void searchView(List<BoardDTO> boardDTOs) {
		for(BoardDTO boardDTO : boardDTOs) {
            this.searchView(boardDTO);
		}
	}
	public void memberView(List<MemberDTO> memberDTOs) {
		System.out.println("아이디\t이름\t가입일");
		for(MemberDTO memberDTO : memberDTOs) {
			System.out.print(memberDTO.getId()+"\t");
			System.out.print(memberDTO.getName()+"\t");
			System.out.println(memberDTO.getR_date());
		}
	}
}
