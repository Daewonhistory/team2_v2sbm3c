function search() {
  now_page_value = 1;
  send();
}

// 페이지 이동
function pageMove(movePage) {
  now_page_value =  parseInt(movePage.dataset.page);
  send();
}

function pageGrpMove(movePageGrp){
	now_page_value =  parseInt(movePage.dataset.page);
  send();
}