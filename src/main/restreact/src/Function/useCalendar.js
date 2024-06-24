import React, { useEffect, useState } from 'react';

// 캘린더 생성, 날짜 선택시 여러 기능 구현
export default function useCalendar() {

    // 현재 날짜
    const [currentDate, setCurrentDate] = useState(new Date());
    const [currentYear, setCurrentYear] = useState(currentDate.getFullYear());
    const [currentMonth, setCurrentMonth] = useState(currentDate.getMonth() + 1);
    const [currentDay, setCurrentDay] = useState(currentDate.getDate());

    // 캘린더 상단에서 선택된 날짜 정보
    const [selectedDate, setSelectedDate] = useState(''); // ex) 2024.02.29(목) 
    const [selectedYear, setSelectedYear] = useState(''); // ex) 2024
    const [selectedMonth, setSelectedMonth] = useState(''); // ex) 02

    // 캘린더에 표시될 날짜 정보
    const [startDate, setStartDate] = useState([]) // 해당 월 시작 날짜
    const [endDate, setEndDate] = useState([]) // 해당 월 마지막 날짜
    const [calendarDate, setCalendarDate] = useState([]) // 해당 월 전체 날짜 배열

    // 캘린더 요일
    const weekList = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'] // 요일 표시에 사용
    const [nowClickDate, setNowClickDate] = useState([]) // 캘린더에서 클릭한 날짜(형식은 [날짜,요일] -> 숫자로 표현)

    // 년, 월 선택 드롭다운 관련 state
    const [dropDownOn, setDropDownOn] = useState(false)
    const [dropDownYear, setDropDownYear] = useState()
    const [dropDownMonth, setDropDownMonth] = useState()

    // 년, 월 선택 드롭다운 현재 선택 년, 월에 적용
    const changeSelectMonth = ()=>{
        
    }

    // 년, 월 선택 드롭다운 현재 선택 년, 월에 적용
    useEffect(()=>{
        if (dropDownMonth < currentMonth) { // 이전 달
            alert('예약 기간이 지난 날짜입니다.')
            setDropDownMonth(currentMonth)
        }else{
            setSelectedYear(dropDownYear)
            setSelectedMonth(dropDownMonth)
            setDropDownOn(false)
        }
    },[dropDownMonth])


    // 요일 한글표시에 사용
    const setDOW = (d) => { // DOW : day of week : 요일
        if (d == 0) {
            return '(일)'
        } else if (d == 1) {
            return '(월)'
        } else if (d == 2) {
            return '(화)'
        } else if (d == 3) {
            return '(수)'
        } else if (d == 4) {
            return '(목)'
        } else if (d == 5) {
            return '(금)'
        } else if (d == 6) {
            return '(토)'
        }
    }

    // 캘린더 상단의 년, 월 선택 구현
    const changeMonth = (e) => {
        if (e === 1) { // 다음 월
            if (selectedMonth === 12) { // 현재 선택된게 12월이면
                setSelectedMonth(Number(1))
                setSelectedYear(prev => prev + 1)
            }else{
                setSelectedMonth(prev => prev + 1)
            }
        }

        if (e === -1) { // 이전 월
            if (selectedMonth === 1) { // 현재 선택된게 1월이면
                setSelectedMonth(Number(12))
                setSelectedYear(prev => prev - 1)
            } else if (selectedMonth == currentMonth) { // 이전 달
                alert('예약 기간이 지난 날짜입니다.')
            } else {
                setSelectedMonth(prev => prev - 1)
            }
        }
    }

    // 처음 현재 날짜 정보 가져왔을 때 그 정보를 캘린더 초기 년, 월 정보로 사용
    useEffect(() => {
        if (currentMonth) {
            setSelectedYear(Number(currentYear))
            setSelectedMonth(Number(currentMonth.toString().padStart(2, '0')))
            setDropDownYear(Number(currentYear))
            setDropDownMonth(Number(currentMonth.toString().padStart(2, '0')))
        }
    }, [currentMonth])

    // 캘린더에서 선택된 년, 월 정보에 따른 해당 월의 날짜 정보 생성
    useEffect(() => {
        // 해당 월 시작 날짜, 마지막 날짜 설정
        const options = { weekday: 'short', month: 'short', day: '2-digit' };
        // 처음 로딩 시 selectedYear(선택된 연도)가 없어서 이상한 날짜 정보가 가져와지는 상황을 방지
        // selectedYear 없으면 현재 시간 기준으로 newStartDate 가져오도록 설정
        const newStartDate = selectedYear !== '' ? new Date(selectedYear, selectedMonth - 1, 1) : new Date(currentYear, currentMonth - 1, 1);
        const newEndDate = selectedYear !== '' ? new Date(selectedYear, selectedMonth, 0) : new Date(currentYear, currentMonth, 0);

        const formattedStartDate = newStartDate.toLocaleDateString('en-US', options).replace(',', '').split(' ');
        const formattedEndDate = newEndDate.toLocaleDateString('en-US', options).replace(',', '').split(' ');

        setStartDate(formattedStartDate)
        setEndDate(formattedEndDate)

        // 해당 월의 전체 날짜 배열 생성
        const newCalendarDate = [];
        for (let i = 1; i <= newEndDate.getDate(); i++) {
            const newCurrentDate = new Date(selectedYear, selectedMonth - 1, i);
            const formattedDate = newCurrentDate.getDate(); // 해당 월의 날짜
            const formattedDay = newCurrentDate.getDay(); // 해당 월의 요일
            newCalendarDate.push([formattedDate, formattedDay]); // 형식은 마찬가지로 [날짜, 요일]을 숫자로 표시
        }
        setCalendarDate(newCalendarDate);

    }, [selectedMonth])

    // 생성된 해당 월의 날짜 배열을 요일에 맞게 캘린더에 표시하기 위해 
    // 첫째날(1일)이 원하는 요일 위치에 오도록 날짜 배열 앞에 null 넣음
    useEffect(() => {
        if (startDate.length == 0) {
            return
        }

        if (calendarDate[0][0] !== null && isNaN(calendarDate[0][0])) {
            const index = weekList.indexOf(startDate[0])
            const arr = new Array(index).fill([null, null]) // 원래 calendarDate 배열 요소 형식에 맞게  채움
            setCalendarDate(prev => [...arr, ...prev])
        }

    }, [calendarDate])

    // 배너에 보여질 selectedDate 설정
    useEffect(() => {
        if (nowClickDate.length !== 0) {
            const year = selectedYear
            const month = selectedMonth.toString().length === 1 ? selectedMonth.toString().padStart(2, '0') : selectedMonth
            const day = nowClickDate[0] // 클릭하여 선택한 날짜 배열([날짜, 요일])의 첫번째 요소
            const DOW = setDOW(nowClickDate[1]) // 요일

            setSelectedDate(`${year}.${month}.${day} ${DOW}`)
        } else {
            setSelectedDate('')
        }
    }, [nowClickDate])

    // 현재 날짜 기준으로 지난 날짜의 불투명도 조절
    const dayStyle = (a) => {
        return {
            opacity: currentMonth == selectedMonth && a[0] < currentDay ? 0.5 : 1,
            cursor: currentMonth == selectedMonth && a[0] < currentDay ? 'default' : 'pointer',
            color: a[1] === 0 ? 'red' : a[1] === 6 ? 'blue' : ''
        }
    }

    // 날짜 배열을 받아서 현재 클릭한 날짜를 저장 -> 클릭한 날짜의 style 변경 위해
    const clickDate = (a) => {
        //받아온 a 형식 : [날짜,요일]
        if (currentMonth == selectedMonth && a[0] < currentDay) {
            return
        } else {
            if (nowClickDate === a) {
                setNowClickDate('')
            } else {
                setNowClickDate(a)
            }

        }
    }

    return {
        selectedDate,
        selectedYear,
        selectedMonth,
        calendarDate,
        nowClickDate,
        changeMonth,
        dayStyle,
        clickDate,
        dropDownOn,
        setDropDownOn,
        dropDownYear,
        dropDownMonth,
        setDropDownYear,
        setDropDownMonth,
        changeSelectMonth
    }
}

