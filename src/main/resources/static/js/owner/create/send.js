function send() {
  let id1 = document.getElementById('id1');
  let email = document.getElementById('email');
  let id = id.value + email.value;

  let id_msg = document.getElementById('id_msg');
  let cname = document.getElementById('cname');
  let cname_msg = document.getElementById('cname_msg');
  let nickname = document.getElementById('nickname');
  let nickname_msg = document.getElementById('nickname_msg');
  let address1 = document.getElementById('address1');
  let address1_msg = document.getElementById('address1_msg');
  let address2 = document.getElementById('address2');
  let address2_msg = document.getElementById('address2_msg');
  let phone1 = document.getElementById('phone1').value;
  let phone2 = document.getElementById('phone2').value;
  let phone3 = document.getElementById('phone3').value;
  let phone = phone1 + phone2 + phone3;

  if (id_msg.innerText.startsWith("이미")) {
    alert('ID 중복입니다.');
    return false;
  }
  if (!id_msg.classList.contains('span_info')) {
    alert('ID 중복 확인을 먼저 진행해주세요.');
    return false;
  }

  if (id1.value.trim().length == 0) {
    id_msg.style.fontSize = "12px";
    id_msg.innerHTML = 'ID가 누락되었습니다. ID 입력은 필수 입니다.';
    id_msg.classList.add('span_warning');
    id.focus();
    return false;
  }
  if (id1.value.trim().length < 4) {
    id_msg.innerHTML = 'ID(이메일)는 3자이상 권장합니다.';
    id_msg.style.marginTop = "5px";
    id_msg.classList.add('span_warning');
    id.focus();
    return false;
  }

  if (cname.value.trim().length == 0) {
    cname_msg.style.fontSize = "12px";
    cname_msg.innerHTML = '이름이 누락되었습니다. 이름 입력은 필수 입니다.';
    cname_msg.classList.add('span_warning');
    cname.focus();
    return false;
  }

  if (!nickname_msg.innerText.startsWith("사용")) {
    alert('닉네임 중복 확인을 먼저 진행해주세요.');
    return false;
  }

  if (nickname.value.trim().length == 0) {
    nickname_msg.style.fontSize = "12px";
    nickname_msg.innerHTML = '닉네임이 누락되었습니다. 닉네임 입력은 필수 입니다.';
    nickname_msg.classList.add('span_warning');
    nickname.focus();
    return false;
  }
  if (nickname.value.trim().length < 3) {
    nickname_msg.innerHTML = '닉네임은 3자이상 권장합니다.';
    nickname_msg.style.marginTop = "5px";
    nickname_msg.classList.add('span_warning');
    nickname.focus();
    return false;
  }

  let passwd = document.getElementById('passwd');
  let passwd2 = document.getElementById('passwd2');
  let passwd2_msg = document.getElementById('passwd2_msg');

  if (passwd.value.trim().length == 0) {
    passwd2_msg.style.fontSize = "12px";
    passwd2_msg.innerHTML = '비밀번호가 누락되었습니다. 비밀번호 입력은 필수 입니다.';
    passwd2_msg.classList.add('span_warning');
    passwd.focus();
    return false;
  }

  if (passwd2.value.trim().length == 0) {
    passwd2_msg.style.fontSize = "12px";
    passwd2_msg.innerHTML = '비밀번호 확인이 누락되었습니다. 비밀번호 입력은 필수 입니다.';
    passwd2_msg.classList.add('span_warning');
    passwd2.focus();
    return false;
  }

  if (passwd.value != passwd2.value) {
    passwd2_msg.innerHTML = '비밀번호가 동일하지 않습니다. 다시 입력해 주세요.';
    passwd2_msg.classList.add('span_warning');
    passwd2.focus();
    return false;
  }

  if (document.getElementById('openModalButton').disabled == false) {
    alert('휴대폰 인증을 완료해주세요.');
    return false;
  }


  if (phone.length < 10) {
    alert('연락처가 올바르지 않습니다.');
    return false;
  }

  if (address1.value.trim().length == 0) {
    address1_msg.style.fontSize = "12px";
    address1_msg.innerHTML = '주소가 누락되었습니다. 주소 입력은 필수 입니다.';
    address1_msg.classList.add('span_warning');
    address1.focus();
    return false;
  }

  if (address2.value.trim().length == 0) {
    address2_msg.style.fontSize = "12px";
    address2_msg.innerHTML = '상세 주소가 누락되었습니다. 상세 주소 입력은 필수 입니다.';
    address2_msg.classList.add('span_warning');
    address2.focus();
    return false;
  }

  document.getElementById('phone').value = phone;
  document.getElementById('id').value = id;
  document.getElementById('login-form').submit();
}