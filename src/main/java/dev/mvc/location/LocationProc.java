package dev.mvc.location;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.location.LocationProc")
public class LocationProc implements LocationProcInter{
	@Autowired
	private LocationDAOInter locationDAO;
	@Override
	public int create(LocationVO locationVO) {
		int cnt = this.locationDAO.create(locationVO);
		return cnt;
	}

	@Override
	public LocationVO read(int restno) {
		LocationVO locationVO = this.locationDAO.read(restno);
		return locationVO;
	}

	@Override
	public ArrayList<LocationVO> list_all() {
		ArrayList<LocationVO> list = this.locationDAO.list_all();
		return list;
	}

	@Override
	public ArrayList<LocationVO> search_list(String word) {
		ArrayList<LocationVO> list = this.locationDAO.search_list(word);
		return list;
	}

	@Override
	public ArrayList<LocationVO> list_by_midareano(int midareano) {
		ArrayList<LocationVO> list = this.locationDAO.list_by_midareano(midareano);
		return list;
	}

	@Override
	public ArrayList<LocationVO> list_by_botareano(int botareano) {
		ArrayList<LocationVO> list = this.locationDAO.list_by_botareano(botareano);
		return list;
	}

	@Override
	public int update(HashMap<String, Object> map) {
		int cnt = this.locationDAO.update(map);
		return cnt;
	}

	@Override
	public int delete(int restno) {
		int cnt = this.locationDAO.delete(restno);
		return cnt;
	}

}
