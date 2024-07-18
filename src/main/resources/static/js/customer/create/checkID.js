function checkID() {

  let id = document.getElementById('id');

  let id_msg = document.getElementById('id_msg');


  console.log(id);

  if (id.value.trim().length == 0) {
    id_msg.style.fontSize = "12px";
    id_msg.innerHTML = 'ID가 누락되었습니다. ID 입력은 필수 입니다.';
    id_msg.style.marginTop = "5px";
    id_msg.classList.add('span_warning');
    id.focus();
    return false;
  }
  if (id.value.trim().length < 4) {
    id_msg.innerHTML = 'ID(이메일)는 3자이상 권장합니다.';
    id_msg.style.marginTop = "5px";
    id_msg.classList.add('span_warning');
    id.focus();
    return false;
  } else {

    id_msg.classList.remove('span_warning');
    let url = './checkID?id=' + id.value;

    fetch(url, {
      method: 'POST'
    })
        .then(response => response.json())
        .then(rdata => {
          if (rdata.cnt > 0) {
            id_msg.innerHTML = '이미 사용중인 ID입니다. 다른 ID를 지정해주세요.';
            id_msg.classList.add('span_warning');
            id.focus();
            setTimeout(function () {
              id_msg.innerHTML = '';
            }, 5000);
          } else {
            id_msg.style.marginTop = "5px";
            id_msg.innerHTML = '사용 가능한 ID(이메일) 입니다.';
            id_msg.classList.add('span_info');


            const btn_checkID = document.getElementById('btn_checkID');
            const btn_checkEmail = document.getElementById('btn_checkEmail');

            if(btn_checkID.style.display !== 'none') {
              btn_checkID.disabled = true;
            }



          }
        })
        .catch(error => {
          console.error('Error:', error);
        });

    id_msg.innerHTML = "<img src='/images/progress.gif' style='width: 5%;'>";
  }
}