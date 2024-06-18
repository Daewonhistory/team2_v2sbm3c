let slider = document.getElementById('slider');
let isDown = false;
let startX;
let walk;

		
		
slider.addEventListener('mousedown', (e) => {
  isDown = true;
  startX = e.pageX;
  walk = 0;
  slider.style.cursor = 'grabbing';
});

slider.addEventListener('mouseleave', () => {
  if (!isDown) return;
  isDown = false;
  slider.style.cursor = 'grab';
  if (walk < -50) {
    nextSlide();
  } else if (walk > 50) {
    prevSlide();
  } else {
    showSlide(currentIndex); // Reset to the current slide if not dragged far enough
  }
});

slider.addEventListener('mouseup', () => {
  if (!isDown) return;
  isDown = false;
  slider.style.cursor = 'grab';
  if (walk < -50) {
    nextSlide();
  } else if (walk > 50) {
    prevSlide();
  } else {
    showSlide(currentIndex); // Reset to the current slide if not dragged far enough
  }
});

slider.addEventListener('mousemove', (e) => {
  if (!isDown) return;
  e.preventDefault();
  const x = e.pageX;
  walk = x - startX;
  slides.forEach((slide, index) => {
    slide.style.transform = `translateX(${100 * (index - currentIndex) + (walk / slider.clientWidth) * 100}%)`;
  });
});

let currentIndex = 0;
const slides = document.querySelectorAll('.slide');
const radioButtonsContainer = document.getElementById('radio_buttons');

// Create radio buttons dynamically based on the number of slides
slides.forEach((slide, index) => {
  const radio = document.createElement('input');
  radio.type = 'radio';
  radio.className = 'radio_button';
  radio.name = 'slider';
  radio.id = `radio_${index + 1}`;
  if (index === 0) radio.checked = true;

  radio.addEventListener('change', () => {
    showSlide(index);
  });

  radioButtonsContainer.appendChild(radio);
});

function showSlide(index) {
  slides.forEach((slide, i) => {
    slide.style.transform = `translateX(${100 * (i - index)}%)`;
  });
  document.getElementById(`radio_${index + 1}`).checked = true;
  currentIndex = index;
}

function nextSlide() {
  currentIndex = (currentIndex + 1) % slides.length;
  showSlide(currentIndex);
}

function prevSlide() {
  currentIndex = (currentIndex - 1 + slides.length) % slides.length;
  showSlide(currentIndex);
}
document.getElementById('next').addEventListener('click', nextSlide);
document.getElementById('prev').addEventListener('click', prevSlide);

showSlide(currentIndex);