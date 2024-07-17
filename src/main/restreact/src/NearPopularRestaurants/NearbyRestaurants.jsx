import React, { useEffect, useState } from 'react';
import './NearbyRestaurants.css';
import { fetchNearBestRest } from '../backend/NearBestRestaurantProc.js';

function NearbyRestaurants() {
  const [restaurants, setRestaurants] = useState([]);

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

  return (
      <section id="section_nearby">
        <h2 className="section-title">근처 인기 식당</h2>
        <div id="restaurant-wrap">
          {restaurants.map((rest, index) => {
            const addressParts = rest.address.split(' '); // 주소를 공백 기준으로 나눔
            const firstLine = addressParts[0]; // 첫 번째 줄
            const secondLine = addressParts.slice(1).join(' '); // 두 번째 줄

            return (
                <div className="restaurant-container" key={index}>
                  {rest.image1 && /\.(jpg|JPG|png|PNG|gif)$/i.test(rest.image1) && (
                      <img src={`/restaurant/storage/${rest.image1}`} alt={rest.name} className="restaurant-image"/>
                  )}
                  <div className="restaurant-info">
                    <span className="restaurant-name"> {rest.name}</span><br/> {/* 순위 표시 */}
                    <div className="rating-stars">
                      <div className="filled-stars" style={{ width: `calc(${rest.rate} * 20%)` }}></div>
                    </div>

                  </div>
                  <div>
                  <span className="restaurant-area">{firstLine}</span><br/> {/* 첫 번째 줄 주소 */}
                  <span className="restaurant-area">{secondLine}</span><br/> {/* 두 번째 줄 주소 */}
                  <span className="rest_rate"> {index + 1} 위 </span>
                  </div>
                </div>
            );
          })}
        </div>
      </section>
  );
}

export default NearbyRestaurants;
