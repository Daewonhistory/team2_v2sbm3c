
fetch("/category/catelist", {
  method: 'GET'
})
    .then(response => response.json())
    .then(rdata => {
      const selectElement = document.getElementById('categoryno');

      // 받은 데이터에서 categoryno 값을 옵션으로 추가
      rdata.forEach(item => {
        const option = document.createElement('option');
        option.value = item.categoryno;
        option.text = item.name; // 이 부분을 변경하셔서 원하는 텍스트로 설정하세요.
        selectElement.appendChild(option);
      });
    });