window.onload = function () {
  document.querySelector('#id').addEventListener('keypress', (event) => {
    if (event.key === 'Enter') {
      document.getElementById('btn_checkID').focus();
    }
  });

  document.querySelector('#passwd').addEventListener('keypress', (event) => {
    let passwd = document.getElementById('passwd').value.trim();
    if (passwd !== "" && event.key === 'Enter') {
      document.getElementById('passwd2').focus();
    } else if (passwd === "" && event.key === 'Enter') {
      document.getElementById('passwd').focus();
    }
  });

  document.querySelector('#passwd2').addEventListener('keypress', (event) => {
    let passwd2 = document.getElementById('passwd2').value.trim();
    if (passwd2 !== "" && event.key === 'Enter') {
      document.getElementById('cname').focus();
    } else if (passwd2 === "" && event.key === 'Enter') {
      document.getElementById('passwd2').focus();
    }
  });

  document.querySelector('#cname').addEventListener('keypress', (event) => {
    let cname = document.getElementById('cname').value.trim();
    if (cname !== "" && event.key === 'Enter') {
      document.getElementById('phone').focus();
    } else if (cname === "" && event.key === 'Enter') {
      document.getElementById('cname').focus();
    }
  });

  document.querySelector('#phone').addEventListener('keypress', (event) => {
    if (event.key === 'Enter') {
      document.getElementById('btn_DaumPostcode').focus();
    }
  });

  document.querySelector('#address2').addEventListener('keypress', (event) => {
    if (event.key === 'Enter') {
      document.getElementById('btn_send').focus();
    }
  });
}

   