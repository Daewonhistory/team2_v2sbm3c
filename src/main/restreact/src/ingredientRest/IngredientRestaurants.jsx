import React from "react";

const IngredientRestaurants = () => (
    <section id="section1">
      <h2>취향저격 식당</h2>
      <div id="wrap-vertical">
        <div className="image-container">
          <img src="/images/1.jpg" alt="Image 1" />
          <div className="restinfo">
            식당명<br />
            평점:5.0<span className="rest-area">서울시 강남구</span>
          </div>
        </div>
        {/* Additional images can be added similarly */}
      </div>
    </section>
);

export default IngredientRestaurants;
