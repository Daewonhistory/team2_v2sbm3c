function check_auth(type) {
  // Retrieve the input elements
  let email_r = document.getElementById('email');
  let phone_r = document.getElementById('phone');
  let auth_p = document.getElementById('phone_auth');
  let auth_e = document.getElementById('email_auth');

  if (type === 'email') {
    // Get the values from the input elements
    let authe  = auth_e.value;
    let email = email_r.value;

    // Create the email authentication object
    const emailAuthVO = {
      email: email,
      auth: authe
    };

    // URL for validating email authentication code
    let url = './check_email';

    // Send the fetch request to validate the email authentication code
    fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(emailAuthVO)
    })
        .then(response => response.json())
        .then(rdata => {
          // Check if the provided information is valid
          if (rdata.success === 1) {
            let nexte = document.getElementById('next-email');
            nexte.style.display = "flex";
            let auth_btn_email = document.getElementById('auth_btn_email');
            auth_btn_email.disabled = true;
            let oname2 = document.getElementById('oname2');
            oname2.readOnly = true;
            email_r.readOnly = true;
            console.log('Email authentication successful');
            alert('인증이 완료되었습니다 다음을 눌러주세요')
            // Additional actions upon successful email authentication can be added here
          } else {
            console.log('Invalid email authentication code');
          }
        })
        .catch(error => {
          console.error('Error checking email authentication:', error);
        });

  } else if (type === 'phone') {
    // Get the values from the input elements
    let authp = auth_p.value;
    let phone = phone_r.value;

    // Create the phone authentication object
    const phoneVO = {
      phone: phone,
      auth: authp
    };

    // URL for validating phone authentication code
    let url2 = './check_phone';

    // Send the fetch request to validate the phone authentication code
    fetch(url2, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(phoneVO)
    })
        .then(response => response.json())
        .then(rdata => {
          // Check if the provided information is valid
          if (rdata.success === 1) {
            console.log('Phone authentication successful');
            alert('인증이 완료되었습니다 다음을 눌러주세요')
            // Disable the button if authentication is successful
            let auth_btn_phone = document.getElementById('auth_btn_phone');
            let onamer = document.getElementById('oname');
            let nextp = document.getElementById('next-phone');
            nextp.style.display = "flex";
            phone_r.readOnly = true;
            onamer.readOnly = true;

            auth_btn_phone.disabled = true;
            // Additional actions upon successful phone authentication can be added here
          } else {
            console.log('Invalid phone authentication code');
          }
        })
        .catch(error => {
          console.error('Error checking phone authentication:', error);
        });
  }
}

// Example usage:
// check_auth('email');  // for checking the email authentication code
// check_auth('phone');  // for checking the phone authentication code
