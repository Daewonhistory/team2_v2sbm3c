package dev.mvc.botarea;

import java.util.ArrayList;
import java.util.HashMap;

public interface BotAreaProcInter {
	/**
	 * 소분류 지역 생성
	 * @param midAreaVO
	 * @return
	 */
	public int create(BotAreaVO botAreaVO);
	
	/**
	 * 소분류 지역 조회
	 * @param midareano
	 * @return
	 */
	public BotAreaVO read(int botareano);
	
	/**
	 * 모든 소분류 지역 리스트 출력
	 * @return
	 */
	public ArrayList<BotAreaVO> list_all();
	
	/**
	 * 지역명 검색 리스트 출력
	 * @param word
	 * @return
	 */
	public ArrayList<BotAreaVO> search_list(String word);
	
	/**
	 * 중분류 지역에 속한 소분류 리스트 출력
	 * @param midareano
	 * @return
	 */
	public ArrayList<BotAreaVO> search_by_midareano(int midareano);
	
	/**
	 * 소분류 지역명 수정
	 * @param midAreaVO
	 * @return
	 */
	public int update(BotAreaVO botAreaVO);
	
	/**
	 * 소분류 지역 삭제
	 * @param midareano
	 * @return
	 */
	public int delete(int botareano);
	
	public ArrayList<BotAreaVO> list_search_paging(HashMap<String, Object> map);
	
	/** 
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
     * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
     *
     * @param cateno          카테고리번호 
     * @param now_page      현재 페이지
	* @param word 검색어
	* @param list_file 목록 파일명
	* @param search_count 검색 레코드수   
	* @return 페이징 생성 문자열
	*/ 
	public String pagingBox(int now_page, String word, String list_file, int search_count, int record_per_page,
      int page_per_block);

	public int list_search_count(HashMap<String, Object> map);
}
