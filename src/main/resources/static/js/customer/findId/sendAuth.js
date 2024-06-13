function sendAuth() {
  let cname = document.getElementById('cname').value;
  let phone = document.getElementById('phone').value;

  const customerVO = {
    phone: phone,
    cname: cname
  };

  let url = './checkNamePhone';

  fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(customerVO)
  })
      .then(response => response.json())
      .then(rdata => {
        if (rdata.cnt == 1) {
          let url2 = './send_phone';
          let auth_div = document.getElementById('auth_div');
          auth_div.style.display = "block";

          const phoneVO = {
            phone: phone
          };

          fetch(url2, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(phoneVO)
          })
              .then(response => {
                if (response.status === 200) {
                  let auth_btn = document.getElementById('auth_btn');
                  auth_btn.disabled = true;
                }
              })
              .catch(error => {
                console.error('Error sending email:', error);
              });
        } else {
          console.log('이름과 휴대폰 번호가 일치하지 않는 정보가 없습니다.');
        }
      })
      .catch(error => {
        console.error('Error:', error);
      });
}