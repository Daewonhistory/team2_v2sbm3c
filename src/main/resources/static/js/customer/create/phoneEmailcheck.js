// 모달과 관련된 요소들 가져오기
const modal = document.getElementById("myModal");
const checkInput = document.getElementById('checkInput');
const sendButton = document.getElementById('sendButton');
const resendButton = document.getElementById('resendButton');
const verifyButton = document.getElementById('verifyButton');
const verificationText = document.getElementById('verificationText');
let currentType = '';  // 현재 인증 타입을 저장하기 위한 변수
let verificationSent = false; // 인증번호 전송 여부를 저장하기 위한 변수

// 모달을 여는 함수
function openModal(type) {
  currentType = type;

  if (type === 'Phone') {
    const phone1 = document.getElementById("phone1").value;
    const phone2 = document.getElementById("phone2").value;
    const phone3 = document.getElementById("phone3").value;

    const phone_check = phone1 + phone2 + phone3;

    if (phone1.length !== 3 || phone2.length !== 4 || phone3.length !== 4) {
      alert("휴대폰 번호를 올바르게 입력하세요.");
      return;
    }

    verificationText.textContent = `휴대폰 번호: ${phone1}-${phone2}-${phone3}`;
    checkInput.placeholder = "인증 번호 입력";
    checkInput.type = "text";
    checkInput.style.width = "35%";
    checkInput.value = '';
    sendButton.textContent = "전송";

    if (verificationSent) {
      sendButton.style.display = "none";
      resendButton.style.display = "block";
      verifyButton.style.display = "block";
    } else {
      sendButton.style.display = "block";
      resendButton.style.display = "none";
      verifyButton.style.display = "none";
    }

    sendButton.onclick = function () {
      sendVerificationCode(phone_check);
    };
    resendButton.onclick = function () {
      sendVerificationCode(phone_check);
    };
    verifyButton.onclick = function () {
      verifyCode(phone_check, checkInput.value);
    };

  } else if (type === 'Email') {
    const email1 = document.getElementById("email1").value;
    const email = email1 + document.getElementById("email2").value;

    if (!email.includes('@') || email1 === '' || email1.length < 3) {
      alert("유효한 이메일 주소를 입력하세요.");
      return;
    }

    verificationText.textContent = `이메일: ${email}`;
    checkInput.placeholder = "인증 번호 입력";
    checkInput.style.width = "35%";
    checkInput.type = "text";
    checkInput.value = '';
    sendButton.textContent = "전송";

    if (verificationSent) {
      sendButton.style.display = "none";
      resendButton.style.display = "block";
      verifyButton.style.display = "block";
    } else {
      sendButton.style.display = "block";
      resendButton.style.display = "none";
      verifyButton.style.display = "none";
    }

    sendButton.onclick = function () {
      sendVerificationCode(email);
    };
    resendButton.onclick = function () {
      sendVerificationCode(email);
    };
    verifyButton.onclick = function () {
      verifyCode(email, checkInput.value);
    };
  }

  modal.style.display = "block";

  const closeButton = document.querySelector(".modal-content .close");
  closeButton.onclick = function () {
    modal.style.display = "none";
    // 모달이 닫힐 때 verificationSent 초기화
    verificationSent = false;
  };

  window.onclick = function (event) {
    if (event.target == modal) {
      modal.style.display = "none";
      // 모달이 닫힐 때 verificationSent 초기화
      verificationSent = false;
    }
  };
}

// 인증번호 전송 함수
function sendVerificationCode(contact) {
  let url = "";
  const requestData = {};

  if (currentType === 'Phone') {
    url = "/customer/send_phone";
    requestData.phone = contact;
  } else if (currentType === 'Email') {
    url = "/customer/send_email";
    requestData.email = contact;
  }

  fetch(url, {
    method: 'POST', headers: {
      'Content-Type': 'application/json'
    }, body: JSON.stringify(requestData)
  })
      .then(response => {
        if (response.status === 200) {
          alert("인증번호 전송완료");

          verificationSent = true;
          sendButton.style.display = "none";
          resendButton.style.display = "block";
          verifyButton.style.display = "block";
          setTimeout(() => {
            alert('인증코드가 만료되었습니다');
            verifyButton.style.display = "none";
            verificationSent = false;
          }, 5 * 60 * 1000);
        } else {
          alert("인증번호 전송 실패");
        }
      })
      .catch(error => {
        console.error('Error:', error);
      });
}

// 인증번호 확인 함수
function verifyCode(contact, authCode) {
  let url = "";
  const requestData = {};

  if (currentType === 'Phone') {
    url = "/customer/check_phone";
    requestData.phone = contact;
    requestData.auth = authCode;
  } else if (currentType === 'Email') {
    url = "/customer/check_email";
    requestData.email = contact;
    requestData.auth = authCode;
  }

  fetch(url, {
    method: 'POST', headers: {
      'Content-Type': 'application/json'
    }, body: JSON.stringify(requestData)
  })
      .then(response => response.json())
      .then(rdata => {
        if (rdata.success === 1) {
          alert("인증 성공");
          modal.style.display = "none";
          verificationSent = false; // 인증 성공 시 초기화
          if (currentType === 'Phone') {
            document.getElementById("phone2").setAttribute("readonly", true);
            document.getElementById("phone3").setAttribute("readonly", true);
          } else if (currentType === 'Email') {
            let btn_checkEmail = document.getElementById('btn_checkEmail');
            let email = document.getElementById('email');
            email.readOnly = true;
            btn_checkEmail.disabled = true;
          }
        } else {
          alert("인증 실패");
        }
      })
      .catch(error => {
        console.error('Error:', error);
      });
}
