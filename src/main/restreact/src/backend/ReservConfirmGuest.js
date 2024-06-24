import React from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { setGuestReservConfirm } from '../store/reservStore';
import { useNavigate } from 'react-router-dom';

// 예약 확인 로직
export default function ReservConfirmGuest() {
    const guestPhone = useSelector(state=>state.reservReducer.guestPhone)
    const dispatch = useDispatch()
    const navigate = useNavigate()

    const confirmGuestHandler = () => {
        axios 
            .post("http://localhost:8080/my-page/reservGuest", {
                phone: guestPhone
            })
            .then((res) => {
                console.log(res.data)
                if(res.data) {
                    dispatch(setGuestReservConfirm({
                        date : res.data[0].reg_date,
                        detail_date : res.data[0].reserve_date, 
                        detail_adults : res.data[0].adults_count ,
                        detail_children : res.data[0].children_count ,
                        detail_baby : res.data[0].infants_count,
                        name : res.data[0].name,
                        phone : res.data[0].phone
                    }))
                    navigate('/my-page/reservation')
                } else {
                    alert('예약 정보가 없습니다')
                }
            })
            .catch((error) => {
                console.log(error)
            })
    }

    return {
        confirmGuestHandler
    }
}