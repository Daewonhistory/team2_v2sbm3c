import React from 'react';
import axios from 'axios';
import { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { setEmailDupli, setSignUpEmail, setSignUpPw } from '../store/loginStore';

// 이메일 중복 체크 로직
export default function EmailDuplicateLogic() {
    const dispatch = useDispatch()
    const signUpEmail = useSelector(state => state.loginReducer.signUpEmail)

    const emailDuplicateCheck = () => {
        const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

        if (emailPattern.test(signUpEmail)) {
            axios
                .post("http://localhost:8080/login/sign-up/duplicate", {
                    email: signUpEmail,
                })
                .then((res) => {
                    console.log(res.data) // ture or false
                    if (res.data) { // 중복 없음
                        dispatch(setEmailDupli(true))
                        alert('사용 가능한 이메일 입니다.')
                    } else if (!res.data) { // 이미 있는 이메일
                        alert('이미 존재하는 이메일 입니다')
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
        emailDuplicateCheck
    }
}