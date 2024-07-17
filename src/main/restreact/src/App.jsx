import React, { useEffect } from "react";
import "./App.css";
import Header from "./header/Header";
import Slider from "./slider/Slider";
import CategoryShortcut from "./category/CategoryShortcut";
import LocationShortcut from "./location/LocationShortcut";
import BestRestaurants from "./bestRestaurant/BestRestaurants";
import NearPopularRestaurants from "./NearPopularRestaurants/NearbyRestaurants";
import IngredientRestaurants from "./ingredientRest/IngredientRestaurants";
import Nav from "./nav/Nav";
import FilterToggle from "./filter/FilterToggle";
import axios from "axios";

const App = () => {
  useEffect(() => {
    // 서버에 일반 사용자 로그인 상태를 확인하는 API 요청
    axios.post("/customer/api/check-login-status")
        .then(response => {
          const isLoggedIn = response.data.isLoggedIn;

          // 세션 스토리지에 일반 사용자 로그인 상태 저장
          sessionStorage.setItem('isLoggedIn', isLoggedIn);

          // 만약 일반 사용자가 로그인하지 않았다면 사업자 로그인 상태를 확인
          if (!isLoggedIn) {
            axios.post("/owner/api/check-login-status")
                .then(response => {
                  const isOwnerLoggedIn = response.data.isOwnerLoggedIn;
                  // 사업자 로그인 상태를 세션 스토리지에 저장
                  sessionStorage.setItem('isOwnerLoggedIn', isOwnerLoggedIn);
                })
                .catch(error => {
                  console.error('Error checking owner login status:', error);
                });
          }
        })
        .catch(error => {
          console.error('Error checking login status:', error);
        });

    // 네비게이션 아이템에 active 클래스 추가
    const navItems = document.querySelectorAll(".nav-item");
    navItems.forEach((item) => {
      if (item.href === window.location.href) {
        item.classList.add("active");
      }
    });
  }, []);

  return (
      <div id="wrapper">
        <Header />
        <main>
          <Slider />
          <CategoryShortcut />
          <LocationShortcut />
          <BestRestaurants />
          <NearPopularRestaurants />
          <IngredientRestaurants />
        </main>
        <div style={{ height: "80px" }}></div>
        <Nav />
        <FilterToggle />
      </div>
  );
};

export default App;
