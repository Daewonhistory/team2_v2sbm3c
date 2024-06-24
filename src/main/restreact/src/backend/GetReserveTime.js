import React from 'react';
import axios from 'axios';
import { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom';
import { setReservTimeList } from '../store/reservStore';

// 캘린더에서 선택한 날짜에 예약가능한 시간대
export default function GetReserveTime() {
    const navigate = useNavigate()


    const getReservTimeHandler = (date) => {
        const personnel = JSON.parse(localStorage.getItem('personnel'))
        console.log(date);
        axios
            .post("http://localhost:8080/reservation/time", {

                    total_count: personnel.adult + personnel.child + personnel.baby,
                    reserve_date : date



            })
            .then((res) => {
                console.log(res.data)
                // setReservTimeList(res.data)
            })
            .catch((error) => {
                console.log(error)
            })

    }

    return {
        getReservTimeHandler
    }
}