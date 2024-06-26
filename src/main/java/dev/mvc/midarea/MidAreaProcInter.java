package dev.mvc.midarea;

import java.util.ArrayList;
import java.util.HashMap;

public interface MidAreaProcInter {
	/**
	 * 중분류 지역 생성
	 * @param midAreaVO
	 * @return
	 */
	public int create(MidAreaVO midAreaVO);
	
	/**
	 * 중분류 지역 조회
	 * @param midareano
	 * @return
	 */
	public MidAreaVO read(int midareano);
	
	/**
	 * 모든 중분류 지역 리스트 출력
	 * @return
	 */
	public ArrayList<MidAreaVO> list_all();
	
	/**
	 * 지역명 검색 리스트 출력
	 * @param word
	 * @return
	 */
	public ArrayList<MidAreaVO> search_list(String word);
	
	/**
	 * 중분류 지역명 수정
	 * @param midAreaVO
	 * @return
	 */
	public int update(MidAreaVO midAreaVO);
	
	/**
	 * 중분류 지역 삭제
	 * @param midareano
	 * @return
	 */
	public int delete(int midareano);
	
	public ArrayList<MidAreaVO> list_paging(HashMap<String, Object> map);
	
	public String pagingBox(int now_page, int search_count, int record_per_page, int page_per_block);
	
	public int list_count();
}
