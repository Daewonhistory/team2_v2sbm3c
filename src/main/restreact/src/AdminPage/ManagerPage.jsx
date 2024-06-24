import React, { useState } from 'react';
import './ManagerPage.css';
import ManagerUser from './ManagerUser';
import ManagerReserv from './ManagerReserv';
import { useLocation, useNavigate } from 'react-router-dom';

export default function ManagerPage() {
  const [menuActive, setMenuActive] = useState(false);
  const location = useLocation();
  const subMenu = location.pathname.split('/').pop();
  const navigate = useNavigate();

  const toggleMenu = () => {
    setMenuActive(!menuActive);
  };

  return (
    <div className='manager-container'>
      <div className="manager-side">
        <div className={`manager-title ${menuActive ? 'active' : ''}`} onClick={toggleMenu}>Eat Days 관리자 페이지</div>
        <ul className={`dropdown-menu ${menuActive ? 'active' : ''}`}>
          <li className='manager-menu' onClick={() => { navigate('/Manager/user'); setMenuActive(false); }}>회원 목록</li>
          <li className='manager-menu' onClick={() => { navigate('/Manager/reserv'); setMenuActive(false); }}>예약 목록</li>
        </ul>
      </div>
      <div className="manager-main">
        <h1 className="manager-main-title">{subMenu === 'user' ? '회원 목록' : subMenu === 'reserv' ? '예약 목록' : '관리자 페이지'}</h1>
        <div className="manager-main-detail">
          {subMenu === 'user' ? <ManagerUser /> : subMenu === 'reserv' ? <ManagerReserv /> : null}
        </div>
      </div>
    </div>
  );
}
