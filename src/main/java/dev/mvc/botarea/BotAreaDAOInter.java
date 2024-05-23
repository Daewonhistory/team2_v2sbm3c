package dev.mvc.botarea;

import java.util.ArrayList;

public interface BotAreaDAOInter {
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
}
