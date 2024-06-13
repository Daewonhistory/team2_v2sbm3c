function send() { // 회원 가입 처리
  let id = document.getElementById('id').value;

  let idfocus = document.getElementById('id');
  let id_msg = document.getElementById('id_msg');

  if (id == null || id.trim() == null) {
    alert('ID를 입력해주세요');
    return false; // 중복 확인을 먼저 하지 않았으므로 회원 가입 진행 중지
  }

  if (id.trim().length == 0) {
    id_msg.style.fontSize = "12px";
    id_msg.innerHTML = 'ID가 누락되었습니다. ID 입력은 필수 입니다.';
    id_msg.classList.add('span_warning');    // class 적용
    idfocus.focus();

    return false;  // 회원 가입 진행 중지

  }
  if (id.trim().length < 4) {
    id_msg.innerHTML = ' ID(이메일)는 3자이상 권장합니다.';
    id_msg.style.marginTop = "5px";
    id_msg.classList.add('span_warning');    // class 적용
    id.focus();

    return false;  // 회원 가입 진행 중지
  }

  // 패스워드를 정상적으로 2번 입력했는지 확인
  let passwd = document.getElementById('passwd').value;

  if (passwd == null || passwd.trim() == null) {
    alert('비밀번호를  입력해주세요');
    return false;
  }

  if (passwd.trim().length == 0) {
    alert('패스워드가 누락되었습니다. 패스워드는 입력은 필수 입니다.');
    return false;
  }


  document.getElementById('frm').submit(); // required="required" 작동 안됨.
}