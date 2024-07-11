import axios from 'axios';

const API_URL = '/restaurant/api/Best_Restaurant_list'; // 상대 경로로 설정

// 카테고리 목록을 가져오는 함수 (백엔드 API 사용)
export const fetchBestRest = async () => {
  try {
    const response = await axios.get(API_URL);
    if (response.data) {
      console.log(response.data)
      return response.data; // JSON 형태의 데이터 반환
    } else {
      throw new Error('No data found in response');
    }
  } catch (error) {
    console.error('Error fetching categories:', error);
    throw error;
  }
};
