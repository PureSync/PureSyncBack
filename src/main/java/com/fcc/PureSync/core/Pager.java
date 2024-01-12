package com.fcc.PureSync.core;

public class Pager {
	
	public static String makePage( int pagesize, int totalCnt, int curPage) {
		String tag="";
		//pagesize : 한페이지에 몇개씩 보여줄지 
		//totalCnt : 전체 데이터 갯수
		//curPage : totalCnt / pagesize; 전체 페이지 갯수 => 나눠서 나오는 값은 무조건 올림으로 
		int pageTotal = (int)Math.ceil(totalCnt/pagesize);
		int pageGroupSize=5; // 한번에 페이지번호?를 10개 보여준당
		int pageGroupStart=0; // 시작 페이지 위치값
		int pageGroupEnd = 0; // 페이지 그룹 마지막값
		
		pageGroupStart = (curPage/pageGroupSize)*pageGroupSize; // 0,10,20,30
		pageGroupEnd =  pageGroupStart + pageGroupSize;

		if(pageGroupEnd>pageTotal) {
			pageGroupEnd = pageTotal + 1;
		}
		// 첫 페이지
		tag = tag + "<ul class=\"dm-pagination d-flex\">";
		if (curPage == 0) {
			tag = tag + "<li class=\"dm-pagination__item\"><span class=\"dm-pagination__link pagination-control\"><span class=\"la la-angle-left\"></span></span></li>";
		} else {
			tag = tag + "<li class=\"dm-pagination__item\"><a href=\"#\" class=\"dm-pagination__link pagination-control\" onclick=\"goPage('0')\"><span class=\"la la-angle-left\"></span></a></li>";
		}

		// 페이지 번호
		for (int i = pageGroupStart; i < pageGroupEnd; i++) {
			if (i == curPage) {
				tag = tag + "<li class=\"dm-pagination__item\"><a href=\"#\" class=\"dm-pagination__link active\"><span class=\"page-number\">" + (i + 1) + "</span></a></li>";
			} else {
				tag = tag + "<li class=\"dm-pagination__item\"><a href=\"#\" class=\"dm-pagination__link\" onclick=\"goPage('" + i + "')\"><span class=\"page-number\">" + (i + 1) + "</span></a></li>";
			}
		}

		// 다음 페이지
		if (curPage == pageTotal - 1) {
			tag = tag + "<li class=\"dm-pagination__item\"><span class=\"dm-pagination__link pagination-control\"><span class=\"la la-angle-right\"></span></span></li>";
		} else {
			tag = tag + "<li class=\"dm-pagination__item\"><a href=\"#\" class=\"dm-pagination__link pagination-control\" onclick=\"goPage('" + (curPage + 1) + "')\"><span class=\"la la-angle-right\"></span></a></li>";
		}

		tag = tag + "</ul>";
		return tag;
	}

}
