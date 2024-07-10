import React, { useEffect } from "react";
import "./App.css";
import Header from "./header/Header";
import Slider from "./slider/Slider";
import CategoryShortcut from "./category/CategoryShortcut";
import LocationShortcut from "./location/LocationShortcut";
import BestRestaurants from "./bestRestaurant/BestRestaurants";
import PopularRestaurants from "./popular/PopularRestaurants";
import IngredientRestaurants from "./ingredientRest/IngredientRestaurants";
import Nav from "./nav/Nav";
import FilterToggle from "./filter/FilterToggle";

const App = () => {
  useEffect(() => {
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
          <PopularRestaurants />
          <IngredientRestaurants />
        </main>
        <div style={{ height: "80px" }}></div>
        <Nav />
        <FilterToggle />
      </div>
  );
};

export default App;
