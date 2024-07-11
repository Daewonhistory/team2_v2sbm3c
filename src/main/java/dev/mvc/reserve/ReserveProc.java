package dev.mvc.reserve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.mvc.dto.ReserveDTO;
import dev.mvc.restaurant.RestaurantDAOInter;
import dev.mvc.restaurant.RestaurantVO;

@Service("reserveProc")
public class ReserveProc implements ReserveProcInter {

    @Autowired
    private ReserveDAOInter reserveDAO;
    
    @Autowired
    private RestaurantDAOInter restaurantDAO;

    @Override
    public int create(ReserveVO reserve) {
        return reserveDAO.create(reserve);
    }

    @Override
    public ArrayList<ReserveVO> list_all() {
        return reserveDAO.list_all();
    }

    @Override
    public ArrayList<ReserveVO> list_search_by_custno(int custno) {
        return reserveDAO.list_search_by_custno(custno);
    }

    @Override
    public ArrayList<ReserveVO> list_search_by_reserve_date(Map<String, Object> params) {
        return reserveDAO.list_search_by_reserve_date(params);
    }

    @Override
    public int delete(int reserveno) {
        return reserveDAO.delete(reserveno);
    }

    @Override
    public ArrayList<ReserveDTO> list_reserve_paging(int now_page, int record_per_page) {
        int begin_of_page = (now_page - 1) * record_per_page;
        int start_num = begin_of_page + 1;
        int end_num = begin_of_page + record_per_page;

        Map<String, Object> map = new HashMap<>();
        map.put("start_num", start_num);
        map.put("end_num", end_num);
        return reserveDAO.list_reserve_paging(map);
    }
    
    @Override
    public ArrayList<ReserveDTO> list_owner_paging(int ownerno, String reserve_date, int now_page, int record_per_page) {
        int begin_of_page = (now_page - 1) * record_per_page;
        int start_num = begin_of_page + 1;
        int end_num = begin_of_page + record_per_page;

        Map<String, Object> map = new HashMap<>();
        map.put("ownerno", ownerno);
        map.put("reserve_date", reserve_date);
        map.put("start_num", start_num);
        map.put("end_num", end_num);

        return reserveDAO.list_owner_paging(map);
    }

    @Override
    public int count_by_owner(int ownerno, String reserve_date) {
        Map<String, Object> map = new HashMap<>();
        map.put("ownerno", ownerno);
        map.put("reserve_date", reserve_date);

        return reserveDAO.count_by_owner(map);
    }
    

    


    @Override
    public int count_all() {
        return reserveDAO.count_all();
    }

    @Override
    public String pagingBox(int now_page, String list_file, int total_count, int record_per_page, int page_per_block) {
        int total_page = (int) (Math.ceil((double) total_count / record_per_page));
        int total_grp = (int) (Math.ceil((double) total_page / page_per_block));
        int now_grp = (int) (Math.ceil((double) now_page / page_per_block));

        int start_page = ((now_grp - 1) * page_per_block) + 1;
        int end_page = (now_grp * page_per_block);

        StringBuilder str = new StringBuilder();
        str.append("<nav aria-label='...'>");
        str.append("<ul class='pagination justify-content-center'>");

        int _now_page = (now_grp - 1) * page_per_block;
        if (now_grp >= 2) {
            str.append("<li class='page-item'><a class='page-link' href='")
                .append(list_file).append("?now_page=").append(_now_page)
                .append("'>이전</a></li>");
        }

        for (int i = start_page; i <= end_page; i++) {
            if (i > total_page) {
                break;
            }
            if (now_page == i) {
                str.append("<li class='page-item active'><a class='page-link'>")
                    .append(i).append("</a></li>");
            } else {
                str.append("<li class='page-item'><a class='page-link' href='")
                    .append(list_file).append("?now_page=").append(i)
                    .append("'>").append(i).append("</a></li>");
            }
        }

        _now_page = (now_grp * page_per_block) + 1;
        if (now_grp < total_grp) {
            str.append("<li class='page-item'><a class='page-link' href='")
                .append(list_file).append("?now_page=").append(_now_page)
                .append("'>다음</a></li>");
        }
        str.append("</ul>");
        str.append("</nav>");

        return str.toString();
    }




    
}
