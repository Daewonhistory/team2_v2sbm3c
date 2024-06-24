import React, { useEffect, useState } from 'react';
import './css/Header.css'
import HeaderLeft from './HeaderLeft';
import { IoMenu } from "react-icons/io5";
import { useNavigate } from 'react-router-dom';
import useMousePosition from '../Function/useMousePosition';

export default function Header() {
    const HeaderMenuList = ['About', 'Menu', 'Contact', 'Social']
    const [headerOn, setHeaderOn] = useState(true)
    const [headerLeftOn, setHeaderLeftOn] = useState(false)
    const [headerMouseOn, setHeaderMouseOn] = useState(false) // 마우스가 헤더메뉴에 있음
    const navigate = useNavigate()
    
    const {mousePosition} = useMousePosition()
    const [prevScrollPos, setPrevScrollPos] = useState(window.scrollY);

    useEffect(() => {
        if(!headerLeftOn){// 스크롤에 따라 헤더 조정 : 스크롤 내릴때 헤어 안보임, 올리면 다시 보임
            const handleScroll = () => {
                const currentScrollPos = window.scrollY;
                const isScrollingDown = prevScrollPos < currentScrollPos;
    
                setPrevScrollPos(currentScrollPos);
    
                if (isScrollingDown) {
                    setHeaderOn(false)
                } else if (!isScrollingDown) {
                    setHeaderOn(true)
                }
            };
    
            window.addEventListener('scroll', handleScroll);
    
            return () => {
                window.removeEventListener('scroll', handleScroll);
            };
        }
    });

    // 헤더 올라갈 때 더보기 접기
    useEffect(()=>{
        if(headerMouseOn){
            setHeaderMouseOn(false)
        }
    },[headerOn])

    useEffect(()=>{
        if(mousePosition.y > 300){ // 마우스 일정 위치 보내 아래면 header 더보기 닫기
            setHeaderMouseOn(false)
        }
    },[mousePosition])

    return (
        <div id='header' className={headerOn ? 'header-show' : 'header-hide'}>
            <div className="header-logo" onClick={() => { navigate('/') }}>
                <a href="" className='header-logo-text'>WooDy</a>
            </div>
            <div className="header-menu" >
                <ul className='header-menu-list' onMouseEnter={() => { setHeaderMouseOn(true) }} onMouseLeave={() => { setHeaderMouseOn(false) }}>
                    {
                        HeaderMenuList.map((a, i) => {
                            return (
                                <HeaderMenu key={i} menu={a} headerMouseOn={headerMouseOn} setHeaderMouseOn={setHeaderMouseOn} />
                            )
                        })
                    }
                </ul>
            </div>
            <div className="header-btn">
                <IoMenu className='all-menu-btn' onClick={() => { setHeaderLeftOn(!headerLeftOn) }} />
            </div>
            <div className="header-menu-more-container" onMouseEnter={() => { setHeaderMouseOn(true) }} style={{ display: headerMouseOn ? 'block' : 'none' }}>
                <div className='header-menu-more'>
                    <div className="more-menu-area" onMouseLeave={() => { setHeaderMouseOn(false) }}></div>
                </div>
            </div>
            {
                headerLeftOn ? <HeaderLeft headerLeftOn={headerLeftOn} setHeaderLeftOn={setHeaderLeftOn} /> : null
            }
        </div>
    );
}

function HeaderMenu(props) {
    const MoreMenuList = { 'About': ['레스토랑 소개', '쉐프 소개'], 'Menu': ['Lunch', 'Dinner', '단품 메뉴'], 'Contact': ['오시는 길', '예약'], 'Social': ['인스타그램', '유튜브'] }
    const MoreMenuLink = { 'About': ['/about/intro', '/about/chef'], 'Menu': ['/menu/lunch', '/menu/dinner', '/menu/additional'], 'Contact': ['/contact/map', '/reservation'], 'Social': ['/', '/'] }
    const navigate = useNavigate()

    return (
        <li>
            <a href="" className='menu' onClick={() => { navigate(MoreMenuLink[props.menu][0]); props.setHeaderMouseOn(false) }}>{props.menu}</a>
            <div className='more-menu-list' style={{ display: props.headerMouseOn ? '' : 'none' }} >
                {
                    MoreMenuList[props.menu].map((a, i) => {
                        return (
                            <a href="" className='more-menu' key={i} onClick={() => { navigate(MoreMenuLink[props.menu][i]); props.setHeaderMouseOn(false) }}>{a}</a>
                        )
                    })
                }
            </div>
        </li>
    )
}

