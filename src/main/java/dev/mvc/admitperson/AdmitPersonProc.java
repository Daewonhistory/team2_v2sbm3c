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
	public int update_admit_person() {
		int cnt = this.admitPersonDAO.update_admit_person();
		return cnt;
	}
	
	
}
