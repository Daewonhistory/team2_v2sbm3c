import React from 'react';
import axios from 'axios';
import {useEffect, useState} from "react";
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { setSignUpEmail, setSignUpPw } from '../store/loginStore';

// 회원가입 로직
export default function SignUpLogic() {
    const navigate = useNavigate()
    const signUpEmail = useSelector(state=>state.loginReducer.signUpEmail)
    const signUpPw = useSelector(state=>state.loginReducer.signUpPw)
    const signUpName = useSelector(state=>state.loginReducer.signUpName)
    const signUpPhone = useSelector(state=>state.loginReducer.signUpPhone)

    const emailCheck = useSelector(state => state.loginReducer.emailCheck)// 이메일 인증 완료
    const phoneCheck = useSelector(state => state.loginReducer.phoneCheck) // 휴대폰 인증 완료

    const signUpHandeler = () => {
        console.log(emailCheck, phoneCheck)
        if (emailCheck && phoneCheck) {
            axios
                .post("http://localhost:8080/login/sign-up", {
                email: signUpEmail,
                password: signUpPw,
                username: signUpName,
                phone: signUpPhone
            })
                .then((res) => {
                    console.log(res.data)
                    if(res.data == 1 ){ // 회원가입 성공
                        navigate('/')
                        alert('회원가입 성공')
                    }else { // 실패
                        alert('회원가입 실패')
                    }
            })
            .catch((error)=>{
                console.log(error)
            })
        } else if (!emailCheck) {
            alert('이메일 인증을 완료 해주세요')
        } else if (!phoneCheck) {
            alert('휴대폰 인증을 완료 해주세요')
        }
    }

    return {
        signUpHandeler
    }
}