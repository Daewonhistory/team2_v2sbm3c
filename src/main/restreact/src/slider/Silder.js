import React, { useState, useEffect, useRef } from 'react';
import './Slider.css';

const Slider = () => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const [isDown, setIsDown] = useState(false);
  const [startX, setStartX] = useState(0);
  const [walk, setWalk] = useState(0);
  const sliderRef = useRef(null);
  const slidesRef = useRef([]);

  useEffect(() => {
    const slider = sliderRef.current;
    const slides = slidesRef.current;

    const mouseDownHandler = (e) => {
      setIsDown(true);
      setStartX(e.pageX);
      setWalk(0);
      slider.style.cursor = 'grabbing';
    };

    const mouseLeaveHandler = () => {
      if (!isDown) return;
      setIsDown(false);
      slider.style.cursor = 'grab';
      if (walk < -50) {
        nextSlide();
      } else if (walk > 50) {
        prevSlide();
      } else {
        showSlide(currentIndex); // Reset to the current slide if not dragged far enough
      }
    };

    const mouseUpHandler = () => {
      if (!isDown) return;
      setIsDown(false);
      slider.style.cursor = 'grab';
      if (walk < -50) {
        nextSlide();
      } else if (walk > 50) {
        prevSlide();
      } else {
        showSlide(currentIndex); // Reset to the current slide if not dragged far enough
      }
    };

    const mouseMoveHandler = (e) => {
      if (!isDown) return;
      e.preventDefault();
      const x = e.pageX;
      setWalk(x - startX);
      slides.forEach((slide, index) => {
        slide.style.transform = `translateX(${100 * (index - currentIndex) + (walk / slider.clientWidth) * 100}%)`;
      });
    };

    slider.addEventListener('mousedown', mouseDownHandler);
    slider.addEventListener('mouseleave', mouseLeaveHandler);
    slider.addEventListener('mouseup', mouseUpHandler);
    slider.addEventListener('mousemove', mouseMoveHandler);

    return () => {
      slider.removeEventListener('mousedown', mouseDownHandler);
      slider.removeEventListener('mouseleave', mouseLeaveHandler);
      slider.removeEventListener('mouseup', mouseUpHandler);
      slider.removeEventListener('mousemove', mouseMoveHandler);
    };
  }, [isDown, startX, walk, currentIndex]);

  useEffect(() => {
    showSlide(currentIndex);
  }, [currentIndex]);

  const showSlide = (index) => {
    slidesRef.current.forEach((slide, i) => {
      slide.style.transform = `translateX(${100 * (i - index)}%)`;
    });
  };

  const nextSlide = () => {
    setCurrentIndex((currentIndex + 1) % slidesRef.current.length);
  };

  const prevSlide = () => {
    setCurrentIndex((currentIndex - 1 + slidesRef.current.length) % slidesRef.current.length);
  };

  return (
      <section id="home" style={{ height: "400px" }}>
        <div className="slider" id="slider" ref={sliderRef}>
          <div className="slide" ref={(el) => slidesRef.current[0] = el}>
            <a href="#">
              <img src="/main/storage/한식당.png" alt="Slide 1" />
            </a>
          </div>
          <div className="slide" ref={(el) => slidesRef.current[1] = el}>
            <img src="/main/storage/중식당.png" alt="Slide 2" />
          </div>
          <div className="slide" ref={(el) => slidesRef.current[2] = el}>
            <img src="/main/storage/양식당.png" alt="Slide 3" />
          </div>
          <button className="arrow left" onClick={prevSlide}>&lt;</button>
          <button className="arrow right" onClick={nextSlide}>&gt;</button>
        </div>
        <div
            style={{
              height: "15%",
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
        >
          <div className="radio_buttons" id="radio_buttons">
            {slidesRef.current.map((_, index) => (
                <input
                    key={index}
                    type="radio"
                    className="radio_button"
                    name="slider"
                    id={`radio_${index + 1}`}
                    checked={currentIndex === index}
                    onChange={() => showSlide(index)}
                />
            ))}
          </div>
        </div>
      </section>
  );
};

export default Slider;
