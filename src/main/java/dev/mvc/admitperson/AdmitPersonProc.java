package dev.mvc.admitperson;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.admitperson.AdmitPersonProc")
public class AdmitPersonProc implements AdmitPersonProcInter{
	@Autowired
	private AdmitPersonDAOInter admitPersonDAO;

	@Override
	public ArrayList<AdmitPersonVO> list_all() {
		ArrayList<AdmitPersonVO> list = this.admitPersonDAO.list_all();
		return list;
	}

	@Override
	public ArrayList<AdmitPersonVO> admit_list(Map<String, Object> map) {
		ArrayList<AdmitPersonVO> list = this.admitPersonDAO.admit_list(map);
		return list;
	}

	@Override
	public AdmitPersonVO read(int admitpersonno) {
		AdmitPersonVO admitPersonVO = this.admitPersonDAO.read(admitpersonno);
		return admitPersonVO;
	}

	@Override
	public int update_curr_person(int admitpersonno) {
		int cnt = this.admitPersonDAO.update_curr_person(admitpersonno);
		return cnt;
	}

	@Override
	public int update_admit_person(int admitpersonno) {
		int cnt = this.admitPersonDAO.update_admit_person(admitpersonno);
		return cnt;
	}
	
	
}
