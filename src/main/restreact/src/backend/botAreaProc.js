// 클라이언트 코드
import axios from 'axios';

// 선택된 중간 지역을 기반으로 하여 하위 지역 목록을 가져오는 함수
export const selectMidArea = async (selectedMidArea) => {
  console.log(selectedMidArea.midareano);
  try {
    const requestBody = {
      midareano: selectedMidArea.midareano
    };

    const response = await axios.post(
        '/botarea/api/botarea_list',
        requestBody,
        { headers: { 'Content-Type': 'application/json' } }
    );

    return response.data; // 서버에서 받은 하위 지역 목록 반환
  } catch (error) {
    console.error('하위 지역 목록을 가져오는 중 오류 발생:', error);
    throw error;
  }
};

// 선택된 하위 지역을 기반으로 한 작업을 처리하는 함수 (예시)
export const selectBotarea = (selectedBotArea) => {
  console.log('선택된 하위 지역:', selectedBotArea.textContent);
  // 여기에 선택된 하위 지역을 기반으로 한 작업을 추가할 수 있습니다.
};
