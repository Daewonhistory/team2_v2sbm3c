import React, { useEffect, useState, useRef } from "react";
import './IngredientRestaurants.css';
import { fetchIngreBest } from '../backend/IngreBestRestaurantProc.js';

function IngreBestRestaurants() {
  const [restaurants, setRestaurants] = useState([]);
  const wrapRef = useRef(null);
  const isDragging = useRef(false);
  const startX = useRef(0);
  const scrollLeft = useRef(0);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await fetchIngreBest();
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
      <section id="section_allergy">
        <h2 className="section-title">알러지 필터 인기 식당</h2>
        <div className="scroll-buttons">
          <button onClick={() => scrollByAmount(-300)} className="scroll-button">왼쪽</button>
          <button onClick={() => scrollByAmount(300)} className="scroll-button">오른쪽</button>
        </div>
        <div
            id="restaurant-wrap"
            ref={wrapRef}
            onMouseDown={onMouseDown}
            onMouseLeave={onMouseLeaveOrUp}
            onMouseUp={onMouseLeaveOrUp}
            onMouseMove={onMouseMove}
        >
          {restaurants.map((rest, index) => {
            return (
                <div className="restaurant-container" key={index}>
                  {rest.image1 && /\.(jpg|JPG|png|PNG|gif)$/i.test(rest.image1) && (
                      <img src={`/restaurant/storage/${rest.image1}`} alt={rest.name} className="restaurant-image"/>
                  )}
                  <div className="restaurant-info">
                    <span className="restaurant-name">{rest.name}</span>
                    <br />
                    <div className="rating-stars">
                      <div className="filled-stars" style={{width: `calc(${rest.rate} * 20%)`}}></div>
                    </div>
                  </div>
                  <div>
                    <span className="restaurant-area">{rest.address}</span>
                    <br />
                    <span className="restaurant-area">{rest.address1}</span>
                    <br />
                    <span className="rest-rate"> {index + 1} 위 </span>
                  </div>
                </div>
            );
          })}
        </div>
      </section>
  );
}

export default IngreBestRestaurants;
