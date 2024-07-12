import React, { useState, useEffect } from "react";
import "./Nav.css";

const Nav = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isOwnerLoggedIn, setIsOwnerLoggedIn] = useState(false);

  useEffect(() => {
    // 세션 스토리지에서 로그인 상태 확인
    const isLoggedInSession = sessionStorage.getItem('isLoggedIn') === 'true';
    const isOwnerLoggedInSession = sessionStorage.getItem('isOwnerLoggedIn') === 'true';
    setIsLoggedIn(isLoggedInSession);
    setIsOwnerLoggedIn(isOwnerLoggedInSession);
  }, []);

  const handleProfileClick = () => {
    if (sessionStorage.getItem('isOwnerLoggedIn') === 'true') {
      window.location.href = '/owner/my_page'; // 사업자 페이지로 이동
    } else if (sessionStorage.getItem('isLoggedIn') === 'true') {
      window.location.href = '/customer/my_page'; // 일반 사용자 페이지로 이동
    } else {
      window.location.href = '/customer/login'; // 로그인 페이지로 이동
    }
  };

  return (
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
          <a className="nav-item profile" onClick={handleProfileClick}>
            <img src="/images/icon/Profile.svg" alt="Profile" className="icon" />
          </a>
        </nav>
      </aside>
  );
};

export default Nav;
