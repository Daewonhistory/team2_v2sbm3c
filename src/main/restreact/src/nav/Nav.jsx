import React from "react";

const Nav = () => (
    <aside id="dock">
      <nav className="bottom-nav">
        <a href="/" className="nav-item home">
          <img src="/images/icon/home.svg" alt="Home" className="icon" />
        </a>
        <a href="/restaurant/search" className="nav-item search">
          <img src="/images/icon/search.svg" alt="Search" className="icon" />
        </a>
        <a href="/favorite/favorite_list_mobile" className="nav-item favorite">
          <img src="/images/icon/favorite.svg" alt="Likes" className="icon" />
        </a>
        <a href="/customer/login" className="nav-item profile">
          <img src="/images/icon/Profile.svg" alt="Profile" className="icon" />
        </a>
      </nav>
    </aside>
);

export default Nav;
