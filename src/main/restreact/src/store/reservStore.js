import { combineReducers, createSlice } from "@reduxjs/toolkit";

const reservUserData = createSlice({
    name: 'reservUserData',
    initialState: JSON.parse(localStorage.getItem('reservUserData')),
    reducers: {
        setReservUserData(state, action) {
            return action.payload; 
        }
    }
});

export const { setReservUserData } = reservUserData.actions;

const reservData = createSlice({
    name: 'reservData',
    initialState: '',
    reducers: {
        setReservData(state, action) {
            return action.payload; 
        }
    }
});

export const { setReservData } = reservData.actions;

const guestPhone = createSlice({
    name: 'guestPhone',
    initialState: '',
    reducers: {
        setGuestPhone(state, action) {
            return action.payload; 
        }
    }
});

export const { setGuestPhone } = guestPhone.actions;

const guestReservConfirm = createSlice({
    name: 'guestReservConfirm',
    initialState: '',
    reducers: {
        setGuestReservConfirm(state, action) {
            return action.payload; 
        }
    }
});

export const { setGuestReservConfirm } = guestReservConfirm.actions;

// 날짜별 예약 가능 시간을 담은 배열
const reservTimeList = createSlice({
    name: 'reservTimeList',
    initialState: ["11:00"],
    reducers: {
        setReservTimeList(state, action) {
            return action.payload; 
        }
    }
});

export const { setReservTimeList } = reservTimeList.actions;


const reservReducer = combineReducers({
    reservUserData: reservUserData.reducer,
    reservData: reservData.reducer,
    guestPhone: guestPhone.reducer,
    guestReservConfirm: guestReservConfirm.reducer,
    reservTimeList: reservTimeList.reducer,
});

export default reservReducer;