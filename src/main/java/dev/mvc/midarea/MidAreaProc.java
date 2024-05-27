package dev.mvc.midarea;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.midarea.MidAreaProc")
public class MidAreaProc implements MidAreaProcInter {
	@Autowired
	MidAreaDAOInter midAreaDAO;
	
	@Override
	public int create(MidAreaVO midAreaVO) {
		int cnt = this.create(midAreaVO);
		return cnt;
	}

	@Override
	public MidAreaVO read(int midareano) {
		MidAreaVO midAreaVO = this.read(midareano);
		return midAreaVO;
	}

	@Override
	public ArrayList<MidAreaVO> list_all() {
		ArrayList<MidAreaVO> list = this.list_all();
		return list;
	}

	@Override
	public ArrayList<MidAreaVO> search_list(String word) {
		ArrayList<MidAreaVO> list = this.search_list(word);
		return list;
	}

	@Override
	public int update(MidAreaVO midAreaVO) {
		int cnt = this.update(midAreaVO);
		return cnt;
	}

	@Override
	public int delete(int midareano) {
		int cnt = this.delete(midareano);
		return cnt;
	}

}
