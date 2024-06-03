package dev.mvc.location;

import java.util.ArrayList;
import java.util.HashMap;

public interface LocationProcInter {
	/**
	 * 위치정보 생성
	 * @param locationVO
	 * @return
	 */
	public int create(LocationVO locationVO);
	
	/**
	 * 위치정보 조회
	 * @param restno
	 * @return
	 */
	public LocationVO read(int restno);
	
	/**
	 * 위치정보 모두 조회
	 * @return
	 */
	public ArrayList<LocationVO> list_all();
	
	/**
	 * 위치정보 세부주소로 검색 
	 * @param word
	 * @return
	 */
	public ArrayList<LocationVO> search_list(String word);
	
	/**
	 * 위치정보 중분류지역으로 조회
	 * @param midareano
	 * @return
	 */
	public ArrayList<LocationVO> list_by_midareano(int midareano);
	
	/**
	 * 위치정보 소분류지역으로 조회
	 * @param botareano
	 * @return
	 */
	public ArrayList<LocationVO> list_by_botareano(int botareano);
	
	/** 
	 * 위치정보 수정
	 * @param map <address botareano, lat, lng>
	 * @return
	 */
	public int update(HashMap<String, Object> map);
	public int delete(int restno);
}
