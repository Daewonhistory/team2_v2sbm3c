import React from 'react';
import axios from 'axios';
import { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { setIsLogin, setSignInEmail, setSignInPw, setUserData } from '../store/loginStore';
import { setReservUserData } from '../store/reservStore';

// 예약 완료 로직
export default function PaymentLogic() {
    const isLogin = useSelector(state => state.loginReducer.isLogin)
    const navigate = useNavigate()
    const dispatch = useDispatch()

    const personnelData = JSON.parse(localStorage.getItem('personnel'))
    const calendarData = JSON.parse(localStorage.getItem('calendar'))

    const reservUserData = useSelector(state => state.reservReducer.reservUserData)
    console.log(isLogin);
    const paymentHandler = () => {
        let requestData = {};

        if (isLogin) {
            // isLogin이 true인 경우 reserve 객체만 요청 본문에 포함
            requestData = {
                reserve: {
                    reserve_date: calendarData.date.split(' ')[0].replaceAll('.', '-') + ' ' + calendarData.time,
                    adults_count: personnelData.adult,
                    children_count: personnelData.child,
                    infants_count: personnelData.baby
                }
            };
        } else {
            // isLogin이 false인 경우 greserve와 guest 객체를 요청 본문에 포함
            requestData = {
                greserve: {
                    reserve_date: calendarData.date.split(' ')[0].replaceAll('.', '-') + ' ' + calendarData.time,
                    adults_count: personnelData.adult,
                    children_count: personnelData.child,
                    infants_count: personnelData.baby
                },
                guest: {
                    guest_id: '',
                    name: reservUserData.name,
                    phone: reservUserData.phone
                }
            };
        }

        axios.post("http://localhost:8080/reservation/payment", requestData)
          .then((res) => {
              if (res.data.status === 1) {
                  alert('예약이 완료되었습니다.');
                  localStorage.removeItem('reservUserData');
                  localStorage.removeItem('reservData');
                  navigate('/my-page/reservation');
              } else if (res.data.status === 2) {
                  localStorage.removeItem('reservUserData');
                  localStorage.removeItem('reservData');
                  alert('예약이 완료되었습니다.');
                  navigate('/');
              } else {
                  alert('예약 오류');
              }
          })
          .catch((error) => {
              console.log(error);
          });
    };

    return {
        paymentHandler
    };
}
