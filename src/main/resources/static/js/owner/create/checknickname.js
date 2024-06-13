function checknickname() {
  let nickname = document.getElementById('nickname');
  let nickname_msg = document.getElementById('nickname_msg');

  if (nickname.value.trim().length == 0) {
    nickname_msg.style.fontSize = "12px";
    nickname_msg.innerHTML = '닉네임이 누락되었습니다. 닉네임 입력은 필수 입니다.';
    nickname_msg.style.marginTop = "5px";
    nickname_msg.classList.add('span_warning');
    nickname.focus();
    return false;
  }
  if (nickname.value.trim().length < 3) {
    nickname_msg.innerHTML = '닉네임은 2자이상 권장합니다.';
    nickname_msg.style.marginTop = "5px";
    nickname_msg.classList.add('span_warning');
    nickname.focus();
    return false;
  } else {
    nickname_msg.classList.remove('span_warning');
    let url = './checkNickName?nickname=' + nickname.value;

    fetch(url, {
      method: 'POST'
    })
        .then(response => response.json())
        .then(rdata => {
          if (rdata.cnt > 0) {
            nickname_msg.innerHTML = '이미 사용중인 닉네임 입니다. 다른 닉네임을 지정해주세요.';
            nickname_msg.classList.add('span_warning2');
            nickname.focus();
          } else {
            nickname_msg.style.marginTop = "5px";
            nickname_msg.innerHTML = '사용 가능한 닉네임 입니다.';
            nickname_msg.classList.add('span_info2');

            const btnCheckNickname = document.getElementById('btn_checkNickname');
            btnCheckNickname.disabled = true;
            btnCheckNickname.classList.add('disabled-button');
            nickname.setAttribute("readonly",true);
            document.getElementById('address1').focus();
          }
        })
        .catch(error => {
          console.error('Error:', error);
        });

    nickname_msg.innerHTML = "<img src='/images/progress.gif' style='width: 5%;'>";
  }
}