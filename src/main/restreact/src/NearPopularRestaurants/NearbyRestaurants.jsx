import React, { useEffect, useState, useRef } from 'react';
import './NearbyRestaurants.css';
import { fetchNearBestRest } from '../backend/NearBestRestaurantProc.js';

function NearbyRestaurants() {
  const [restaurants, setRestaurants] = useState([]);
  const wrapRef = useRef(null);
  const isDragging = useRef(false);
  const startX = useRef(0);
  const scrollLeft = useRef(0);


  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await fetchNearBestRest();
        setRestaurants(data); // 최고의 인기 식당 데이터를 상태에 저장
      } catch (error) {
        console.error('Error fetching best restaurants:', error);
      }
    };
    fetchData();
  }, []); // 컴포넌트가 처음 렌더링될 때 한 번만 실행

  const onMouseDown = (e) => {
    isDragging.current = true;
    startX.current = e.pageX - wrapRef.current.offsetLeft;
    scrollLeft.current = wrapRef.current.scrollLeft;
  };

  const onMouseLeaveOrUp = () => {
    isDragging.current = false;
  };

  const onMouseMove = (e) => {
    if (!isDragging.current) return;
    e.preventDefault();
    const x = e.pageX - wrapRef.current.offsetLeft;
    const walk = (x - startX.current) * 2; // 스크롤 속도 조정
    wrapRef.current.scrollLeft = scrollLeft.current - walk;
  };

  const scrollByAmount = (amount) => {
    wrapRef.current.scrollTo({
      left: wrapRef.current.scrollLeft + amount,
      behavior: 'smooth'
    });
  };

  return (
      <section id="section_nearby">
        <h2 className="near_section-title">근처 인기 식당</h2>
        <div className="scroll-buttons">
          <button onClick={() => scrollByAmount(-300)} className="scroll-button">왼쪽</button>
          <button onClick={() => scrollByAmount(300)} className="scroll-button">오른쪽</button>
        </div>
        <div id="restaurant-wrap" ref={wrapRef}
             onMouseDown={onMouseDown}
             onMouseLeave={onMouseLeaveOrUp}
             onMouseUp={onMouseLeaveOrUp}
             onMouseMove={onMouseMove}>
          {restaurants.map((rest, index) => {
            return (
                <div className="Nerestaurant-container" key={index}>
                  {rest.image1 && /\.(jpg|JPG|png|PNG|gif)$/i.test(rest.image1) && (
                      <img src={`/restaurant/storage/${rest.image1}`} alt={rest.name} className="Nerestaurant-image"/>
                  )}
                  <div className="Nerestaurant-info">
                    <span className="Nerestaurant-name">{rest.name}</span>
                    <div className="Nerating-stars">
                      <div className="Nefilled-stars" style={{ width: `${rest.rate * 20}%` }}></div>
                    </div>
                  </div>
                  <div>
                    <span className="restaurant-area">{rest.address}</span>
                    <br />
                    <span className="restaurant-area">{rest.address1}</span>
                    <br />
                    <span className="rest_rate">{index + 1} 위</span>
                  </div>
                </div>
            );
          })}
        </div>
      </section>
  );
}

export default NearbyRestaurants;
