window.onload = function () {

  document.querySelector('#id').addEventListener('keypress', (event) => {

    let id = document.getElementById('id').value;


    if (id !== "" && event.key === 'Enter') {
      document.getElementById('passwd').focus();
    } else if (passwd === "" && event.key === 'Enter') {
      document.getElementById('passwd').focus();
    }
  });

  document.querySelector('#passwd').addEventListener('keypress', (event) => {
    let passwd = document.getElementById('passwd').value.trim();
    if (passwd !== "" && event.key === 'Enter') {
      document.getElementById('btn_send').focus();
    } else if (passwd === "" && event.key === 'Enter') {
      document.getElementById('passwd').focus();
    }

  });


}
