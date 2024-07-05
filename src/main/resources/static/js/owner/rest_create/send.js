function validateForm() {
  const name = document.getElementById('name').value;
  const reserveRange = document.getElementById('reserve_range').value;
  const tel = document.getElementById('tel').value;
  const map = document.getElementById('map');

  if (name.length < 2) {
    alert('식당 이름은 2글자 이상이어야 합니다.');
    return false;
  }

  if (!/^\d+$/.test(reserveRange)) {
    alert('예약 범위는 숫자만 입력 가능합니다.');
    return false;
  }

  if (!/^\d+$/.test(tel)) {
    alert('전화번호는 숫자만 입력 가능합니다.');
    return false;
  }

  if (map.style.display === 'none') {
    alert('식당 위치를 확인해주세요.');
    return false;
  }

  return true;
}

function send() {
  if (validateForm()) {
    document.getElementById('frm').submit();
  }
}