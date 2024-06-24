import React from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { Cookies } from "react-cookie";

// 로그인 로직, 엑세스토큰 유효기간 얼마 안남았을 때 리프레시 토큰 보내서 엑세스토큰 재발급
export default function SignInLogic() {
    const navigate = useNavigate()
    const cookies = new Cookies();

    const signInEmail = useSelector(state => state.loginReducer.signInEmail)
    const signInPw = useSelector(state => state.loginReducer.signInPw)

    const sendRefreshToken = (Token) => {
        axios
            .post("http://localhost:8080/login/refreshToken", {
                refreshToken: Token
            })
            .then((res) => {
                console.log(res.data)
                if (res.data.status == 1) { // 엑세스토큰 재발급 성공
                    loginSuccess(res.data)
                    localStorage.setItem('isLogin', true)
                } else if (res.data.status == -1) { // 리프레시토큰 만료
                    navigate('/login/sign-in')
                    alert('다시 로그인 필요')
                }
            })
            .catch((error) => {
                console.log(error)
            })
    }
    
    // 로그인 성공시 로직
    const loginSuccess = (data) => {
        cookies.set('accessToken', data.accessToken) 
        
        axios.defaults.headers.common['Authorization'] = `Bearer ${data.accessToken}`; // API 요청하는 콜마다 헤더에 accessToken 담아 보내도록 설정
        localStorage.setItem('userName', data.name)
        console.log(data.refreshToken)
        navigate('/')

        setTimeout(() => { // accessTokenTime 유효기간 만료 30초 전에 실행
            sendRefreshToken(data.refreshToken)
        }, data.accessTokenExpiration - 30000);
    }

    // 로그인 요청
    const signInHandler = () => {
        const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (emailPattern.test(signInEmail) && signInEmail != null) {
            console.log(signInEmail, signInPw)
            axios
                .post("http://localhost:8080/login/sign-in", {
                    email: signInEmail,
                    password: signInPw
                })
                .then((res) => {
                    console.log(res.data)
                    if (res.data.status == 1) { // 로그인 성공
                        loginSuccess(res.data)
                    } else if (res.data.status == -1) { // 이메일 없음
                        navigate('/login/sign-in')
                        alert('이메일 오류')
                    } else if (res.data.status == 0) { // 비번 틀림
                        navigate('/login/sign-in')
                        alert('비밀번호 오류')
                    }
                })
                .catch((error) => {
                    console.log(error)
                })
        } else {
            alert('잘못된 이메일 형식입니다.')
        }
    }

    return {
        signInHandler
    }
}