function checkAuth() {
  let auth_btn = document.getElementById('auth_btn');
  let phone = document.getElementById('phone');
  let auth = document.getElementById('auth');
  if (auth_btn.disabled === true) {
    const phoneVO = {
      phone: phone,
      auth: auth
    };

    fetch(url2, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(phoneVO)
    })
        .then(response => {
          if (response.success === 1) {
            let auth_btn = document.getElementById('auth_btn');
            auth_btn.disabled = true;
          }
        })
        .catch(error => {
          console.error('Error sending email:', error);
        });
  }

}
