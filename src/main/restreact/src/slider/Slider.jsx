import React, { useState, useRef, useEffect } from 'react';
import "./Slider.css";

const Slider = () => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const [isDown, setIsDown] = useState(false);
  const [startX, setStartX] = useState(0);
  const [walk, setWalk] = useState(0);
  const sliderRef = useRef(null);

  const slides = [
    { id: 1, src: '/main/storage/한식당.png', alt: 'Slide 1', link: ' ' },
    { id: 2, src: '/main/storage/중식당.png', alt: 'Slide 2', link: ' ' },
    { id: 3, src: '/main/storage/양식당.png', alt: 'Slide 3', link: ' ' },
    // Add more slides as needed
  ];

  const handleMouseDown = (e) => {
    setIsDown(true);
    setStartX(e.pageX);
    setWalk(0);
    sliderRef.current.style.cursor = 'grabbing';
  };

  const handleMouseLeave = () => {
    if (!isDown) return;
    setIsDown(false);
    sliderRef.current.style.cursor = 'grab';
    if (walk < -50) {
      nextSlide();
    } else if (walk > 50) {
      prevSlide();
    } else {
      showSlide(currentIndex);
    }
  };

  const handleMouseUp = () => {
    if (!isDown) return;
    setIsDown(false);
    sliderRef.current.style.cursor = 'grab';
    if (walk < -50) {
      nextSlide();
    } else if (walk > 50) {
      prevSlide();
    } else {
      showSlide(currentIndex);
    }
  };

  const handleMouseMove = (e) => {
    if (!isDown) return;
    e.preventDefault();
    const x = e.pageX;
    const newWalk = x - startX;
    setWalk(newWalk);
    slides.forEach((_, index) => {
      document.querySelector(`#slide_${index}`).style.transform = `translateX(${100 * (index - currentIndex) + (newWalk / sliderRef.current.clientWidth) * 100}%)`;
    });
  };

  const showSlide = (index) => {
    slides.forEach((_, i) => {
      document.querySelector(`#slide_${i}`).style.transition = 'transform 0.5s ease-in-out';
      document.querySelector(`#slide_${i}`).style.transform = `translateX(${100 * (i - index)}%)`;
    });
    setCurrentIndex(index);
  };

  const nextSlide = () => {
    const newIndex = (currentIndex + 1) % slides.length;
    setCurrentIndex(newIndex);
    showSlide(newIndex);
  };

  const prevSlide = () => {
    const newIndex = (currentIndex - 1 + slides.length) % slides.length;
    setCurrentIndex(newIndex);
    showSlide(newIndex);
  };

  useEffect(() => {
    const interval = setInterval(() => {
      nextSlide();
    }, 5000);

    return () => {
      clearInterval(interval); // 컴포넌트가 unmount 되면 interval 정리
    };
  }, [currentIndex]);

  return (
      <div>
        <div className="slider" id="slider" ref={sliderRef}
             onMouseDown={handleMouseDown}
             onMouseLeave={handleMouseLeave}
             onMouseUp={handleMouseUp}
             onMouseMove={handleMouseMove}
             style={{ height: '400px', position: 'relative' }}>
          {slides.map((slide, index) => (
              <div className="slide" key={index} id={`slide_${index}`}
                   style={{
                     position: 'absolute',
                     top: 0,
                     left: 0,
                     width: '100%',
                     height: '100%',
                     display: 'flex',
                     justifyContent: 'center',
                     alignItems: 'center',
                     transition: 'transform 0.5s ease-in-out'
                   }}>
                {slide.link ? (
                    <a href={slide.link}>
                      <img src={slide.src} alt={slide.alt} style={{ maxWidth: '100%', maxHeight: '100%' }} />
                    </a>
                ) : (
                    <img src={slide.src} alt={slide.alt} style={{ maxWidth: '100%', maxHeight: '100%' }} />
                )}
              </div>
          ))}
          <button className="arrow left" id="prev" onClick={prevSlide}>
            &lt;
          </button>
          <button className="arrow right" id="next" onClick={nextSlide}>
            &gt;
          </button>
        </div>
        <div style={{ height: '15%', display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
          <div className="radio_buttons" id="radio_buttons">
            {slides.map((_, index) => (
                <input
                    key={index}
                    type="radio"
                    className="radio_button"
                    name="slider"
                    checked={currentIndex === index}
                    onChange={() => showSlide(index)}
                    style={{
                      appearance: 'none',
                      width: '10px',
                      height: '10px',
                      borderRadius: '50%',
                      backgroundColor: currentIndex === index ? '#333' : '#ccc',
                      margin: '0 5px',
                      cursor: 'pointer'
                    }}
                />
            ))}
          </div>
        </div>
      </div>
  );
};

export default Slider;
