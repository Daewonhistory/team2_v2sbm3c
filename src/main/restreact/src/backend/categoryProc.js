// backend/categoryProc.js

const API_URL = '/category/api/categoryList'; // 상대 경로로 설정

// 카테고리 목록을 가져오는 함수 (백엔드 API 사용)
export const fetchCategories = async () => {
  try {
    const response = await fetch(API_URL);
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    return await response.json(); // JSON 형태의 데이터 반환
  } catch (error) {
    console.error('Error fetching categories:', error);
    throw error;
  }
};
