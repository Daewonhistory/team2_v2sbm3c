import React, { useState, useEffect } from 'react';
import { fetchBestRest } from '../backend/BestRestaurantProc.js';
import './BestRestaurants.css'; // CSS 파일을 임포트합니다.

const BestRestaurants = () => {
  const [bestRestaurants, setBestRestaurants] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await fetchBestRest();
        setBestRestaurants(data); // 최고의 인기 식당 데이터를 상태에 저장
      } catch (error) {
        console.error('Error fetching best restaurants:', error);
      }
    };
    fetchData();
  }, []); // 컴포넌트가 처음 렌더링될 때 한 번만 실행

  useEffect(() => {
    const wrapHorizontal = document.getElementById('wrap-horizontal');

    let isDown = false;
    let startX;
    let scrollLeft;

    const mouseDownHandler = (e) => {
      isDown = true;
      wrapHorizontal.classList.add('active');
      startX = e.pageX - wrapHorizontal.offsetLeft;
      scrollLeft = wrapHorizontal.scrollLeft;
    };

    const mouseLeaveHandler = () => {
      isDown = false;
      wrapHorizontal.classList.remove('active');
    };

    const mouseUpHandler = () => {
      isDown = false;
      wrapHorizontal.classList.remove('active');
    };

    const mouseMoveHandler = (e) => {
      if (!isDown) return;
      e.preventDefault();
      const x = e.pageX - wrapHorizontal.offsetLeft;
      const walk = (x - startX) * 3; // Adjust scroll speed
      wrapHorizontal.scrollLeft = scrollLeft - walk;
    };

    wrapHorizontal.addEventListener('mousedown', mouseDownHandler);
    wrapHorizontal.addEventListener('mouseleave', mouseLeaveHandler);
    wrapHorizontal.addEventListener('mouseup', mouseUpHandler);
    wrapHorizontal.addEventListener('mousemove', mouseMoveHandler);

    return () => {
      wrapHorizontal.removeEventListener('mousedown', mouseDownHandler);
      wrapHorizontal.removeEventListener('mouseleave', mouseLeaveHandler);
      wrapHorizontal.removeEventListener('mouseup', mouseUpHandler);
      wrapHorizontal.removeEventListener('mousemove', mouseMoveHandler);
    };
  }, []);

  return (
      <section id="section_best">
        <h2>최고 인기 순위 식당</h2>
        <div id="wrap-horizontal">
          {bestRestaurants.length > 0 ? (
              bestRestaurants.map((restFullData, index) => (
                  <div key={index} className="image-container">
                    {restFullData.image1 && (restFullData.image1.endsWith('jpg') || restFullData.image1.endsWith('JPG') || restFullData.image1.endsWith('png') || restFullData.image1.endsWith('PNG') || restFullData.image1.endsWith('gif')) ? (
                        <img src={`/restaurant/storage/${restFullData.image1}`} style={{ width: '120px', height: '100px' }} alt={restFullData.name} />
                    ) : null}
                    <div className="restinfo">
                      <span>{restFullData.name}</span><br />
                      <div className="stars">
                        <div className="full-stars" style={{ width: `calc(${restFullData.rate} * 20%)` }}></div>
                      </div>
                      <span className="rest-area">서울시 강남구</span><br />
                      <span className="ranking">{` ${index + 1}위`}</span>
                    </div>
                  </div>
              ))
          ) : (
              <p>인기 식당 데이터를 불러오는 중입니다...</p>
          )}
        </div>
      </section>
  );
};

export default BestRestaurants;
