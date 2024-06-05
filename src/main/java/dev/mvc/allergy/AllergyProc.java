package dev.mvc.allergy;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.allergy.AllergyProc")
public class AllergyProc implements AllergyProcInter {
	@Autowired
	AllergyDAOInter allergyDAO;
	
	@Override
	public int create(AllergyVO allergyVO) {
		int cnt = this.allergyDAO.create(allergyVO);
		return cnt;
	}

	@Override
	public ArrayList<AllergyVO> list_by_ingredno(int ingredno) {
		ArrayList<AllergyVO> list = this.allergyDAO.list_by_ingredno(ingredno);
		return list;
	}

	@Override
	public int delete_by_allerno(int allerno) {
		int cnt = this.allergyDAO.delete_by_allerno(allerno);
		return cnt;
	}
	
  @Override
  public int delete_by_custno(int custno) {
    int cnt = this.allergyDAO.delete_by_custno(custno);
    return cnt;
  }

	@Override
	public AllergyVO search_by_ingredno_custno(Map<String, Object> map) {
		AllergyVO allergyVO = this.allergyDAO.search_by_ingredno_custno(map);
		return allergyVO;
	}

	@Override
	public int delete_by_ingredno(int ingredno) {
		int cnt = this.allergyDAO.delete_by_ingredno(ingredno);
		return cnt;
	}

  @Override
  public ArrayList<AllergyVO> list_by_custno(int custno) {
      return allergyDAO.list_by_custno(custno); // 이 메서드를 추가합니다.
  }
  
	

}
