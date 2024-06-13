function sendid() {
  // Retrieve the input elements
  let cname_r = document.getElementById('cname2');
  let id_r = document.getElementById('id');

  // Get the values from the input elements
  let cname = cname_r.value;
  let id = id_r.value;

  // Create the customer object
  const customerVO = {
    cname: cname,
    id: id
  };

  // URL for validating customer information based on id
  let url = './checkNameid';

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
          cname_r.readOnly = true;
          id_r.readOnly = true;

          // Create the object to be sent in the second request
          const authVO = {
            id: id
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
                  let auth_btn = document.getElementById('auth_btn_id');
                  auth_btn.disabled = true;
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
        console.error('Error:', error);
      });
}
