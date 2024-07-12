package dev.mvc.notice;

import java.util.ArrayList;
import java.util.HashMap;

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
	 * 페이징 + 검색
	 * @param map
	 * @return
	 */
	public ArrayList<NoticeVO> list_search_paging(HashMap<String, Object> map);
	
	/**
	 * 식당별 검색 수
	 * @param map
	 * @return
	 */
	public int list_by_restno_search_count(HashMap<String, Object> map);
	
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
