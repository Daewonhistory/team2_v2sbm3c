package dev.mvc.notice;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NoticeVO {
	private int noticeno;
	private String title;
	private String content;
	private int restno;
	private String restname;
}
