import axios from 'axios';

const API_URL = '/restaurant/api/Near_Best_Restaurant_list'; // 상대 경로로 설정

// 카테고리 목록을 가져오는 함수 (백엔드 API 사용)
export const fetchIngreBest = async () => {
  try {
    let currentLat = 37.9699349; // Default latitude
    let currentLng = 127.3260396; // Default longitude

    if (navigator.geolocation) {
      // GeoLocation을 이용해서 접속 위치를 얻어옵니다
      const position = await new Promise((resolve, reject) => {
        navigator.geolocation.getCurrentPosition(resolve, reject, {
          enableHighAccuracy: true,
          timeout: 5000,
          maximumAge: 0
        });
      }).catch((error) => {
        console.log('위치조회에 실패했습니다. 광화문 위치로 설정합니다.');
        // 위치 조회 실패 시 광화문 좌표로 설정
        return {
          coordinates: {
            currentLat: 37.5729, // 광화문 위도
            currentLng: 126.9769 // 광화문 경도
          }
        };
      });

      currentLat = position.coords.latitude; // 위도
      currentLng = position.coords.longitude; // 경도
    } else {
      console.log('GeoLocation을 지원하지 않습니다. 광화문 위치로 설정합니다.');
      currentLat = 37.5729; // 광화문 위도
      currentLng = 126.9769; // 광화문 경도
    }

    const coordinates = {
      currentLat: currentLat,
      currentLng: currentLng
    };

    const response = await axios.post(API_URL, coordinates);

    if (response.data) {
      console.log(response.data);
      return response.data; // JSON 형태의 데이터 반환
    } else {
      throw new Error('No data found in response');
    }
  } catch (error) {
    console.error('Error fetching categories:', error);
    throw error;
  }
};
