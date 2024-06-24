import React, { useEffect, useState } from 'react';
import './css/AutoSlide.css'

// width, height, slideList 받아옴 
export default function AutoSlide(props) {
    const [slideOn, setSlideOn] = useState(true)
    
    return (
        <div className="auto-slide" style={{ width: `${props.width * props.slideList.length}px`, height: `${props.height}px` }} onMouseEnter={() => { setSlideOn(false) }} onMouseLeave={() => { setSlideOn(true) }}>
            <ul className={`slide-list original ${!slideOn ? 'stop' : ''}`}>
                {props.slideList.map((a, i) => {
                    return (
                        <li className="slide" style={{ width: `${props.width}px` }} key={i}>
                            <img src={`/image/${a}.png`} alt="" />
                        </li>
                    )
                })}
            </ul>
            <ul className={`slide-list clone ${!slideOn ? 'stop' : ''}`}>
                {props.slideList.map((a, i) => {
                    return (
                        <li className="slide" style={{ width: `${props.width}px` }} key={i}>
                            <img src={`/image/${a}.png`} alt="" />
                        </li>
                    )
                })}
            </ul>
        </div>
    )
}

