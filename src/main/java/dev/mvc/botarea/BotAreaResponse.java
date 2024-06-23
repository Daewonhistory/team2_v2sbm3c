package dev.mvc.botarea;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BotAreaResponse {
    private ArrayList<BotAreaVO> botAreaList;
    private String paging; // 예시로 추가 정보를 문자열로 추가


}