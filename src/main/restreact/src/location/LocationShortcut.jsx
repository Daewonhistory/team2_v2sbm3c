import React, { useEffect, useState } from 'react';
import { fetchMidArea } from '../backend/midAreaProc.js';
import { selectMidArea, selectBotarea } from '../backend/botAreaProc.js'; // botAreaProc.js에서 selectBotarea도 import 해줘야 합니다.

const LocationShortcut = () => {
  const [midAreas, setMidAreas] = useState([]);
  const [botAreas, setBotAreas] = useState([]);
  const [selectedMidArea, setSelectedMidArea] = useState(null);
  const [activeButton, setActiveButton] = useState(null); // 활성화된 버튼 상태 추가

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await fetchMidArea();
        setMidAreas(data); // 중간 지역 데이터를 상태에 저장
      } catch (error) {
        console.error('Error fetching mid areas:', error);
      }
    };
    fetchData();
  }, []);

  // 중간 지역 버튼 클릭 시 호출되는 함수
  const handleButtonClick = async (area, index) => {
    setSelectedMidArea(area); // 선택된 중간 지역 설정
    setActiveButton(index); // 활성화된 버튼 설정
    const botAreasData = await selectMidArea(area); // 하위 지역 목록을 가져옴
    setBotAreas(botAreasData); // 하위 지역 데이터를 상태에 저장
  };

  // 모든 지역 버튼 클릭 시 호출되는 함수
  const handleAllSelect = async (selectedMidArea) => {
    const botAreasData = await selectMidArea(selectedMidArea);
    setBotAreas(botAreasData);
  };

  // 하위 지역 버튼 클릭 시 호출되는 함수
  const handleBotAreaClick = async (botArea) => {
    // 여기서 botArea는 선택된 하위 지역을 의미합니다.
    // 선택된 하위 지역에 기반한 작업을 수행할 수 있습니다.
    selectBotarea(botArea);

    // midareano 값이 있는지 확인 후 사용합니다.
    if (botArea.midareano) {
      // midareano 값으로 원하는 작업을 수행합니다.
      console.log('Selected bot area with midareano:', botArea.midareano);
    }
  };

  return (
      <section id="loca_shortcut_section">
        <h2>위치기반 숏컷</h2>
        <div id="midarea_div">
          {midAreas.map((area, index) => (
              <button
                  key={index}
                  value={area.midareano}
                  className={`button ${activeButton === index ? 'button_selected' : ''}`}
                  type="button"
                  onClick={() => handleButtonClick(area, index)}
              >
                {area.name}
              </button>
          ))}
        </div>
        {selectedMidArea && (
            <div id="botarea_div" style={{ marginTop: '30px' }}>
              <h4>지역 소분류 선택</h4>
              <div>
                <button type="button" className="btn_botarea" onClick={() => handleAllSelect(selectedMidArea)}>
                  모든 지역
                </button>
                {botAreas.map((botArea, index) => (
                    <button key={index} className="btn_botarea" type="button" onClick={() => handleBotAreaClick(botArea)}>
                      {botArea.name}
                    </button>
                ))}
              </div>
            </div>
        )}
      </section>
  );
};

export default LocationShortcut;
