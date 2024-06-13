function sendAuth() {
  // Retrieve the input elements
  let cname_r = document.getElementById('cname');
  let phone_r = document.getElementById('phone');

  // Get the values from the input elements
  let cname = cname_r.value;
  let phone = phone_r.value;

  // Create the customer object
  const customerVO = {
    cname: cname,
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
          let auth_div = document.getElementById('auth_div_phone');

          // Show the authentication div
          auth_div.style.display = "block";

          // Make the input fields read-only
          cname_r.readOnly = true;
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
                  let auth_btn = document.getElementById('auth_btn');
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
