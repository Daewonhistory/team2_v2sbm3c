import React from "react";

const Slider = () => (
    <section id="home" style={{ height: "400px" }}>
      <div className="slider" id="slider">
        <div className="slide">
          <a href="https://www.naver.com">
            <img src="/main/storage/hansikdang.png" alt="Slide 1" />
          </a>
        </div>
        <div className="slide">
          <img src="/main/storage/중식당.png" alt="Slide 2" />
        </div>
        <div className="slide">
          <img src="/main/storage/양식당.png" alt="Slide 3" />
        </div>
        <button className="arrow left" id="prev">
          &lt;
        </button>
        <button className="arrow right" id="next">
          &gt;
        </button>
      </div>
      <div
          style={{
            height: "15%",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
      >
        <div className="radio_buttons" id="radio_buttons"></div>
      </div>
    </section>
);

export default Slider;
