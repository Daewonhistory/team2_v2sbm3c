import React, { useEffect, useState } from 'react';
import './NearbyRestaurants.css';
import axios from 'axios';

function NearbyRestaurants() {
  const [restaurants, setRestaurants] = useState([]);

  useEffect(() => {
    // Fetch nearby restaurants data from server
    axios.get('/api/nearby-restaurants')
        .then(response => {
          setRestaurants(response.data);
        })
        .catch(error => {
          console.error('There was an error fetching the restaurants!', error);
        });
  }, []);

  return (
      <section id="section_nearby">
        <h2>근처에 있는 식당</h2>
        <div id="wrap-horizontal">
          {restaurants.map((rest, index) => (
              <div className="image-container" key={index}>
                {rest.image1 && /\.(jpg|JPG|png|PNG|gif)$/i.test(rest.image1) && (
                    <img src={`/restaurant/storage/${rest.image1}`} alt={rest.name} style={{ width: '120px', height: '100px' }} />
                )}
                <div className="restinfo">
                  <span>{rest.name}</span><br />
                  <div className="stars">
                    <div className="full-stars" style={{ width: `calc(${rest.rate} * 20%)` }}></div>
                  </div>
                  <span className="rest-area">서울시 강남구</span><br />
                  <span className="rest-time">영업시간: {rest.opentime}~{rest.closetime}</span>
                </div>
              </div>
          ))}
        </div>
      </section>
  );
}

export default NearbyRestaurants;
