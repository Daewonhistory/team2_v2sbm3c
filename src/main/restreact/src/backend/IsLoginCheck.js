import React from 'react';
import axios from 'axios';
import { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { setIsLogin, setSignInEmail, setSignInPw, setUserData } from '../store/loginStore';
import { Cookies } from "react-cookie";
import getToken from '../Function/getToken';

// 로그인 판별 로직
export default function IsLoginCheck() {
    const navigate = useNavigate()
    const dispatch = useDispatch()
    const cookies = new Cookies();

    // 로그인 판별 함수
    const loginCheckHandler = () => {
        axios.defaults.headers.common['Authorization'] = `Bearer ${cookies.get('accessToken')}`; // API 요청하는 콜마다 헤더에 accessToken 담아 보내도록 설정
        if (axios.defaults.headers.common['Authorization']) { 
            axios
                .post("http://localhost:8080/login/login-check", {
                    // 헤더에 담겨있는 엑세스토큰을 전송
                })
                .then((res) => {
                    console.log(res.data)
                    if (res.data != -1) { // 로그인 중임
                        console.log('로그인 중임')
                        dispatch(setIsLogin(true))
                        localStorage.setItem('isLogin', true)
                    } else if (res.data == -1) { // 로그인 중 아님
                        console.log('로그인 중 아님')
                        dispatch(setIsLogin(false))
                        localStorage.setItem('isLogin', false)
                    }
                })
                .catch((error) => {
                    console.log(error)
                })
        }
    }

    return {
        loginCheckHandler
    }
}