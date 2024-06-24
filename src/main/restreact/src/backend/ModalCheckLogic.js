import React from 'react';
import axios from 'axios';
import { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { setEmailCheck, setEmailDupli, setPhoneCheck, setSignUpEmail, setSignUpPw } from '../store/loginStore';

// 이메일, 휴대폰 인증 로직
export default function EmailDuplicateLogic() {
    const dispatch = useDispatch()
    const signUpEmail = useSelector(state => state.loginReducer.signUpEmail)
    const signUpPhone = useSelector(state => state.loginReducer.signUpPhone)

    const modalCheck = (type) => {
        if(type == '이메일'){
            // axios
            //     .post("http://localhost:8080/login/sign-in", {
            //     email: signUpEmail,
            // })
            //     .then((res) => {
            //         console.log(res.data)
            // })
            // .catch((error)=>{
            //     console.log(error)
            // })
            // dispatch(setEmailCheck(true))
        }else if(type == '휴대폰'){
            // axios
            //     .post("http://localhost:8080/login/sign-in", {
            //     phone: signUpPhone,
            // })
            //     .then((res) => {
            //         console.log(res.data)
            // })
            // .catch((error)=>{
            //     console.log(error)
            // })
            // dispatch(setPhoneCheck(true))
        }
    }

    return {
        modalCheck
    }
}