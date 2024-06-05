package dev.mvc.customerhistory;

import dev.mvc.dto.HistoryDTO;

import java.util.ArrayList;

public interface CustomerHistoryProcInter {
	/**
	 * 로그인 기록 생성
	 * @param historyVO
	 * @return
	 */
	public int create(CustomerHistoryVO historyVO);


	/**
	 * 로그인 내역 조회
	 * @param custno
	 * @return
	 */
	public ArrayList<HistoryDTO> selecthistory (int custno);
}
