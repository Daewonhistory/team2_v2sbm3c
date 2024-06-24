import React from 'react';
import axios from 'axios';

// 캘린더에서 선택한 날짜에 예약가능한 시간대
export default function GetReservData() {

    const getReservTimeHandler = (date) => {
        axios
            .get("http://localhost:8080/admin/reserv")
            .then((res) => {
                console.log(res.data)
                
            })
            .catch((error) => {
                console.log(error)
            })

    }

    return {
        getReservTimeHandler
    }
}