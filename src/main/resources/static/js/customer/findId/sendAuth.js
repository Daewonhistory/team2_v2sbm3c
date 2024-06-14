function sendid() {
  // 입력 요소 가져오기
  let cname_r = document.getElementById('cname2');
  let id_r = document.getElementById('id');

  // 입력 요소에서 값 가져오기
  let cname = cname_r.value;
  let id = id_r.value;

  // 고객 객체 생성
  const customerVO = {
    cname: cname,
    id: id
  };

  // 고객 정보를 확인하는 URL
  let url = './checkNameid';

  // 첫 번째 fetch 요청을 보내 고객 정보를 확인
  fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(customerVO)
  })
      .then(response => response.json())
      .then(rdata => {
        // 제공된 정보가 유효한지 확인
        if (rdata.cnt === 1) {
          // 인증 코드를 보내는 URL
          let url2 = './send_email';

          // 입력 필드를 읽기 전용으로 설정
          cname_r.readOnly = true;
          id_r.readOnly = true;

          // 두 번째 요청에 보낼 객체 생성
          const authVO = {
            id: id
          };

          // 두 번째 fetch 요청을 보내 인증 코드 전송
          fetch(url2, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(authVO)
          })
              .then(response => {
                if (response.status === 200) {
                  let auth_btn = document.getElementById('auth_btn_id');
                  auth_btn.disabled = true;
                } else {
                  console.error('Failed to send authentication code. Status:', response.status);
                }
              })
              .catch(error => {
                console.error('Error sending authentication:', error);
              });
        } else {
          console.log('No matching information found for the provided name and id.');
        }
      })
      .catch(error => {
        console.error('Error validating customer information:', error);
      });
}
