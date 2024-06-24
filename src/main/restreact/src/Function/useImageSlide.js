import React, { useEffect, useState } from 'react';

// 이미지 배열을 받아서 이미지들을 순서대로 보여주는 슬라이드 기능
// 슬라이드 넘어가는거 보이는 기능은 없음
export default function useImageSlide(imageList) { 
    const [slideNum, setSlideNum] = useState(0) // 슬라이드에서 현재 보여주고 있는 이미지 번호
    const [nowImage, setNowImage] = useState(imageList[0]) // 슬라이드 이미지명
    const [imageOpacity, setImageOpacity] = useState(1) // 이미지 전환 시 투명도 조절
    const [animationOn, setAnimationOn] = useState(true) // 이미지 전환 시 텍스트 애니메이션 여부
    const [autoSlide, setAutoSlide] = useState(true)

    const nextImage = ()=>{ // 다음 이미지 보기
        setAnimationOn(false) // 이미지 전환 시작 시 애니메이션 일단 제거
        if(slideNum == imageList.length-1){
            setSlideNum(0)
        }else{
            setSlideNum(prev => prev + 1);
        }
    }

    const prevImage = ()=>{ // 이전 이미지 보기
        setAnimationOn(false) // 이미지 전환 시작 시 클래스명 일단 제거
        if(slideNum == 0){
            setSlideNum(imageList.length-1)
        }else{
            setSlideNum(prev => prev - 1);
        }
    }

    const selectImage = (i)=>{
        setAnimationOn(false)
        setSlideNum(i)
    }

    useEffect(()=>{ // 5초 마다 다음 이미지로 전환
        const interval = setInterval(() => {    
            if(autoSlide){
                nextImage()
            }
        }, 5000);

        return () => clearInterval(interval); 
    })

    useEffect(()=>{ // slideNum 바뀌면 바뀐 값에 따라 이미지 전환
        setImageOpacity(0.8)
        setTimeout(()=>{
            setImageOpacity(0.9)
            setNowImage(imageList[slideNum])
        },200)
    },[slideNum])

    useEffect(()=>{
        setAnimationOn(true) // 이미지 전환 완료 후 클래스명 추가해서 애니메이션 실행
    },[nowImage])

    return{
        nowImage, 
        imageOpacity, 
        animationOn, 
        autoSlide, 
        setAutoSlide, 
        prevImage, 
        nextImage,
        selectImage
    }
}

