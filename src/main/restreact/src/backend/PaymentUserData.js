import React from 'react';
import axios from 'axios';
import { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { setIsLogin, setSignInEmail, setSignInPw, setUserData } from '../store/loginStore';
import { setReservUserData } from '../store/reservStore';

export default function PaymentUserData() {
    const navigate = useNavigate()
    const dispatch = useDispatch()

    const reservUserData = useSelector(state => state.reservReducer.reservUserData)
    const reservData = useSelector(state => state.reservReducer.reservData)

    const getUserData = ()=>{
        axios
            .post("http://localhost:8080/reservation/user-data", {

            })
            .then((res) => {
                console.log(res.data)
                if (res.data) {
                    localStorage.setItem('reservUserData', JSON.stringify({'name': res.data.username, 'email': res.data.email, 'phone': res.data.phone, 'point': res.data.point}))
                } else if (!res.data) {

                }
            })
            .catch((error) => {
                console.log(error)
            })
    }

    useEffect(()=>{
        console.log(reservUserData)
    },[reservUserData])

    return {
        getUserData
    }
}