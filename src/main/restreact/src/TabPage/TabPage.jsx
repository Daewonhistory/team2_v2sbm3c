import React, { useEffect, useState } from 'react';
import './TabPage.css'
import { useNavigate, useParams } from 'react-router-dom';

export default function TabPage(props) {
    const navigate = useNavigate()
    const { subMenu } = useParams()

    useEffect(() => {
        // 페이지 로드 후 스크롤을 맨 위로 이동
        window.scrollTo(0, 0);
    }, []);

    const tabStyle = (i)=>{
        return{
            backgroundColor : subMenu === props.tabLink[i] ? 'rgb(194,122,52)' : ''
        }
    }

    return (
        <div id='tab-page'>
            <div className="tab-page-container">
                <div className="tab-page-image">
                    <img src="/image/login1.png" alt="" />
                    <div className="image-text">{subMenu.charAt(0).toUpperCase() + subMenu.slice(1)}</div>
                </div>
                <div className="tab-page-inner">
                    <div className="tab-wrap">
                        <ul className="tab-list">
                            {props.tabMenu.map((a,i) => {
                                    return (
                                        <li className='tab-item' key={i} onClick={()=>{navigate(`/${props.menu}/${props.tabLink[i]}`)}} style={tabStyle(i)}>
                                            <a className="tab-link" onClick={()=>{navigate(`/${props.menu}/${props.tabLink[i]}`)}}>{a}</a>
                                        </li>
                                    )
                                })}
                        </ul>
                    </div>
                    <div className="tab-page-main">
                        {props.subMenu}
                    </div>
                </div>
            </div>
        </div>
    );
}

