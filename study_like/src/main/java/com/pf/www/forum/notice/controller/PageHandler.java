package com.pf.www.forum.notice.controller;

public class PageHandler {
	
	int page;
	
	int size;
	
	int navSize = 10;
	
	int totalCnt;
	
	int totalPage;
	
	int beginPage;
	
	int endPage;
	
	int pageSize;
	
	boolean showPrev;
	
	boolean showNext;

	
	public PageHandler() {};
	
	public PageHandler(int page, int pageSize, int totalPage, int totalCnt, int beginPage,
			int endPage, int navSize, boolean showPrev, boolean showNext) {
		
		totalPage = (int)Math.ceil(totalCnt/(double)pageSize);
		beginPage = (page -1) / navSize * navSize + 1;
		endPage = Math.min(beginPage + navSize -1, totalPage);
		showPrev = beginPage != 1;
		showNext = endPage != totalPage;
	};
	
	public PageHandler(int page, int size, int navSize, int totalCnt,int totalPage, int beginPage,
			int endPage, int pageSize,boolean showPrev, boolean showNext) {
		this.page = page;
		this.size = size;
		this.navSize = navSize;
		this.totalCnt = totalCnt;
		this.totalPage = totalPage;
		this.beginPage = beginPage;
		this.endPage = endPage;
		this.pageSize = pageSize;
		this.showPrev = showPrev;
		this.showNext = showNext;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNavSize() {
		return navSize;
	}

	public void setNavSize(int navSize) {
		this.navSize = navSize;
	}

	public int getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getBeginPage() {
		return beginPage;
	}

	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isShowPrev() {
		return showPrev;
	}

	public void setShowPrev(boolean showPrev) {
		this.showPrev = showPrev;
	}

	public boolean isShowNext() {
		return showNext;
	}

	public void setShowNext(boolean showNext) {
		this.showNext = showNext;
	};
	
}
