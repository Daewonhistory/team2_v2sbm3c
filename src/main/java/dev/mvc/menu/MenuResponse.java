package dev.mvc.menu;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MenuResponse {
    private ArrayList<MenuVO> menuList;
    private String paging; // 예시로 추가 정보를 문자열로 추가


}