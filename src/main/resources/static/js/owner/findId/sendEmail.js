function sendcode(type) {
  // Retrieve the input elements
  let oname_r = document.getElementById('oname2');
  let email_r = document.getElementById('email');
  let phone_r = document.getElementById('phone');

  if (type === 'email') {
    // Get the values from the input elements
    let oname = oname_r.value;
    let email = email_r.value;

    // Create the customer object
    const customerVO = {
      oname: oname,
      email: email
    };

    // URL for validating customer information based on email
    let url = './checkNameEmail';

    // Send the first fetch request to validate the customer information
    fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(customerVO)
    })
        .then(response => response.json())
        .then(rdata => {
          // Check if the provided information is valid
          if (rdata.cnt === 1) {
            // URL for sending the authentication code
            let url2 = './send_email';

            // Make the input fields read-only
            oname_r.readOnly = true;
            email_r.readOnly = true;

            // Create the object to be sent in the second request
            const authVO = {
              email: email
            };

            // Send the second fetch request to send the authentication code
            fetch(url2, {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json'
              },
              body: JSON.stringify(authVO)
            })
                .then(response => {
                  if (response.status === 200) {
                    let auth_btn = document.getElementById('auth_btn_email');
                    let auth_div = document.getElementById('auth_div_email');
                    auth_div.style.display = "block";

                    auth_btn.disabled = true;
                  }
                })
                .catch(error => {
                  console.error('Error sending authentication:', error);
                });
          } else {
            console.log('No matching information found for the provided name and email.');
          }
        })
        .catch(error => {
          console.error('Error:', error);
        });

  } else if (type === 'phone') {
    // Get the values from the input elements
    let oname = document.getElementById('oname').value;
    let phone = phone_r.value;

    // Create the customer object
    const customerVO = {
      oname: oname,
      phone: phone
    };

    // URL for validating customer information based on phone
    let url = './checkNamePhone';

    // Send the first fetch request to validate the customer information
    fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(customerVO)
    })
        .then(response => response.json())
        .then(rdata => {
          // Check if the provided information is valid
          if (rdata.cnt === 1) {
            // URL for sending the authentication code
            let url2 = './send_phone';

            // Make the input fields read-only
            phone_r.readOnly = true;

            // Create the object to be sent in the second request
            const authVO = {
              phone: phone
            };

            // Send the second fetch request to send the authentication code
            fetch(url2, {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json'
              },
              body: JSON.stringify(authVO)
            })
                .then(response => {
                  if (response.status === 200) {
                    let auth_btn = document.getElementById('auth_btn_phone');
                    let auth_div = document.getElementById('auth_div_phone');
                    auth_div.style.display = "block";

                    auth_btn.disabled = true;
                  }
                })
                .catch(error => {
                  console.error('Error sending authentication:', error);
                });
          } else {
            console.log('No matching information found for the provided name and phone.');
          }
        })
        .catch(error => {
          console.error('Error:', error);
        });
  }
}

// Example usage:
// sendcode('email');  // for sending the email authentication code
// sendcode('phone');  // for sending the phone authentication code
