package dev.mvc.customerhistory;

import dev.mvc.dto.HistoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("dev.mvc.customerhistory.CustomerHistoryProc")
public class CustomerHistoryProc implements CustomerHistoryProcInter {

	@Autowired
	CustomerHistoryDAOInter historyDAO;

	/**
	 * 로그인 내역 생성
	 *
	 * @param historyVO
	 * @return
	 */
	@Override
	public int create(CustomerHistoryVO historyVO) {
		int cnt = this.historyDAO.create(historyVO);
		return cnt;
	}


	/**
	 * 로그인 내역 조회
	 *
	 * @param custno
	 * @return
	 */
	@Override
	public ArrayList<HistoryDTO> selecthistory(int custno) {
		return this.historyDAO.selecthistory(custno);
	}


}
