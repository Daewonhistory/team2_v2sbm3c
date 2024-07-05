function searchAddress() {
  let maps = document.getElementById('map');
  maps.style.display = "block";
  var mapContainer = document.getElementById('map'), // 지도를 표시할 div
      mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
      };

  // 지도를 생성합니다
  var map = new kakao.maps.Map(mapContainer, mapOption);

  // 주소-좌표 변환 객체를 생성합니다
  var geocoder = new kakao.maps.services.Geocoder();
  const midarea = document.getElementById("midAreaSelect");
  const midselectedOption = midarea.options[midarea.selectedIndex];
  const midvalue = midselectedOption.text;

  const botarea = document.getElementById("botarea");
  const botareaselectedOption = botarea.options[botarea.selectedIndex];
  const botvalue = botareaselectedOption.text;

  const addressvalue = document.getElementById('addressv').value;

  const allarea = midvalue + " " + botvalue + " " + addressvalue;
  const submitaddress = document.getElementById('alladdress');
  submitaddress.value = allarea;

  console.log(allarea);
  // 주소로 좌표를 검색합니다
  geocoder.addressSearch(allarea, function(result, status) {

    // 정상적으로 검색이 완료됐으면
    if (status === kakao.maps.services.Status.OK) {

      var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

      // 결과값으로 받은 위치를 마커로 표시합니다
      var marker = new kakao.maps.Marker({
        map: map,
        position: coords
      });

      // 인포윈도우로 장소에 대한 설명을 표시합니다
      var infowindow = new kakao.maps.InfoWindow({
        content: '<div style="width:150px;text-align:center;padding:6px 0;">사업자 식당</div>'
      });
      infowindow.open(map, marker);

      // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
      map.setCenter(coords);

      let lat = document.getElementById('lat');
      lat.value = result[0].y;

      let lng = document.getElementById('lng');
      lng.value = result[0].x;

      console.log(coords);
    }
  });
}
