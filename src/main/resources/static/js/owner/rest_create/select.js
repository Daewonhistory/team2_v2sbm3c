$(document).ready(function () {
  // Kakao Maps API 초기화
  kakao.maps.load(function () {
    // Select2 초기화
    $('select').select2({
      placeholder: '검색해주세요',
      allowClear: false,
      language: {
        noResults: function () {
          return '결과가 없습니다'; // 결과가 없을 때 표시할 메시지
        }
      }
    });
  });
});