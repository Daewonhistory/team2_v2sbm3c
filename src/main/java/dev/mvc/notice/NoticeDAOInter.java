package dev.mvc.notice;

import java.util.ArrayList;

public interface NoticeDAOInter {
	/**
	 * 공지 생성
	 * @param noticeVO
	 * @return
	 */
	public int create(NoticeVO noticeVO);
	
	/**
	 * 공지 조회
	 * @param restno
	 * @return
	 */
	public NoticeVO read(int noticeno);
	
	/**
	 * 모든 공지 조회
	 * @return
	 */
	public ArrayList<NoticeVO> list_all();
	
	/**
	 * 가게별 공지 조회
	 * @param restno
	 * @return
	 */
	public ArrayList<NoticeVO> list_by_restno(int restno);
	
	/**
	 * 공지 수정
	 * @return
	 */
	public int update(NoticeVO noticeVO);
	
	
	/**
	 * 공지 삭제
	 * @return
	 */
	public int delete(int noticenno);
}
