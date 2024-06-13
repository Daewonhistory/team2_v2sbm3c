let id1 = document.getElementById('id1');
let email = document.getElementById('email');
let btn_checkID = document.getElementById('btn_checkID');
let btn_email = document.getElementById('btn_checkEmail');

// ID나 이메일이 변경될 때마다 중복 확인을 다시 요청하는 함수
function watchInputChange() {
  id1.addEventListener('input', handleInputChange);
  email.addEventListener('input', handleInputChange);
}

// ID나 이메일이 변경될 때 호출되는 함수
function handleInputChange() {
  // 중복 확인 버튼 활성화
  btn_checkID.disabled = false;
  btn_checkID.style.display ="block";
  // 이메일 확인 버튼 숨기기
  btn_email.style.display = "none";
}

// 페이지 로드 시 입력란 변경 감지 시작
window.onload = watchInputChange;