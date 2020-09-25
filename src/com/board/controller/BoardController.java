package com.board.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.board.dao.AdminDAO;
import com.board.dao.BoardDAO;
import com.board.dao.MemberDAO;
import com.board.dto.BoardDTO;
import com.board.dto.MemberDTO;
import com.board.view.BoardView;

public class BoardController {
	private boolean check;
	private Scanner sc;
	private MemberDTO memberDTO;
	private BoardDTO boardDTO;
	private List<BoardDTO> boardDTOs;
	private BoardView boardView;
	private MemberDAO memberDAO;
	private BoardDAO boardDAO;
	private String message;
	private List<MemberDTO> memberDTOs;
	private AdminDAO adminDAO;

	public BoardController() {
		check = true;
		sc = new Scanner(System.in);
		memberDTO = null;
		memberDTOs = new ArrayList<>();
		boardDTO = null;
		boardDTOs = new ArrayList<>();
		boardView = new BoardView();
		memberDAO = new MemberDAO();
		boardDAO = new BoardDAO();
		adminDAO = new AdminDAO();
		message = "";
	}

	public void start() {
		while (check) {
			if (memberDTO == null) {
				System.out.println("1.회원가입  2.로그인  3.프로그램종료");
				int num = sc.nextInt();
				switch (num) {
				case 1:
					message = memberDAO.memInsert();
					boardView.view(message);
					break;
				case 2:
					message = "로그인 실패!";
					memberDTO = memberDAO.memLogin();
					if (memberDTO != null) {
						message = "로그인 성공!";
					}
					boardView.view(message);
					break;
				default:
					boardView.view("프로그램을 종료합니다");
					check = false;
				}
			} else {
				if (memberDTO.getAdminflag() == 0) {
					System.out.println("1.글 작성  2.글 조회  3.나의 활동  4.비밀번호변경  5.회원탈퇴  6.로그아웃");
				} else {
					System.out.println("1.글 작성  2.글 조회  3.나의 활동  4.비밀번호변경  5.회원정보 조회  6.글 삭제  7.로그아웃");
				}

				int num = sc.nextInt();
				switch (num) {
				case 1:
					message = boardDAO.boardInsert(memberDTO);
					boardView.view(message);
					break;
				case 2:
					boolean readFlag = true;
					while (readFlag) {
						boardDTOs = boardDAO.boardRead();
						if (boardDTOs.isEmpty()) {
							boardView.view("등록된 게시글이 없습니다");
						} else {
							boardView.view(boardDTOs);
						}
						System.out.println("1.글 번호로 검색  2.제목으로 검색  3.뒤로가기");
						num = sc.nextInt();
						switch (num) {
						case 1:
							boardDTO = boardDAO.boardNumSearch();
							if (boardDTO != null) {
								boardView.searchView(boardDTO);
							} else {
								boardView.view("등록된 게시글이 없습니다");
							}
							break;
						case 2:
							boardDTOs = boardDAO.boardTitleSearch();
							if (boardDTOs.isEmpty()) {
								boardView.view("등록된 게시글이 없습니다");
							} else {
								boardView.searchView(boardDTOs);
							}
							break;
						default:
							readFlag = false;
						}

					}
					break;
				case 3:
					boolean myFlag = true;
					while (myFlag) {
						boardDTOs = boardDAO.myBoard(memberDTO);
						System.out.println("내가 쓴 글 : " + boardDTOs.size());
						if (boardDTOs.isEmpty()) {
							boardView.view("등록된 게시글이 없습니다");
						} else {
							boardView.view(boardDTOs);
						}
						System.out.println("1.글 삭제  2.글 수정  3.뒤로가기");
						num = sc.nextInt();

						switch (num) {
						case 1:
							message = boardDAO.myBoardDelete(memberDTO);
							boardView.view(message);
							break;
						case 2:
							message = boardDAO.myBoardUpdate(memberDTO);
							boardView.view(message);
							break;
						default:
							myFlag = false;
						}

					}
					break;
				case 4:
					boolean changeCheck = false;
					changeCheck = memberDAO.changePw(memberDTO);
					if (changeCheck) {
						boardView.view("비밀번호가 변경되었습니다 다시 로그인합니다");
						memberDTO = null;
					} else {
						boardView.view("새 비밀번호가 일치하지 않습니다");
					}
					break;
				case 5:
					if (memberDTO.getAdminflag() == 0) {
						boolean deleteCheck = false;
						deleteCheck = memberDAO.memDelete(memberDTO);
						if (deleteCheck) {
							boardView.view("계정삭제되었습니다 그동안 이용해주셔서 감사합니다");
							memberDTO = null;
						} else {
							boardView.view("비밀번호가 일치하지 않습니다");
						}
					} else {
						memberDTOs = adminDAO.memberRead();
						if (memberDTOs.isEmpty()) {
							boardView.view("가입되어 있는 회원이 없습니다");
						} else {
							boardView.memberView(memberDTOs);
						}
					}

					break;
				case 6:
					if (memberDTO.getAdminflag() == 0) {
						memberDTO = null;
						boardView.view("로그아웃되었습니다");
					} else {
						boolean deleteFlag = true;
						while (deleteFlag) {
							boardDTOs = boardDAO.boardRead();
							if (boardDTOs.isEmpty()) {
								boardView.view("등록된 게시글이 없습니다");
							} else {
								boardView.view(boardDTOs);
							}
							System.out.println("1.글 삭제  2.뒤로가기");
							num = sc.nextInt();
							switch (num) {
							case 1:
								message = adminDAO.boardDelete();
								boardView.view(message);
								break;
							default:
								deleteFlag = false;
							}
						}

					}
					break;
					default:
						memberDTO = null;
						boardView.view("로그아웃되었습니다");
						break;
				}
			}

		}
	}
}
