import React, { useEffect, useState } from 'react';

export default function AdminReserv() {
    const ReservData = [
        { reservId: 1, userId: 1, regDate: 230409, adult: 2, child: 1, baby: 0, comment: '창가 자리' },
        { reservId: 2, userId: 4, regDate: 230409, adult: 1, child: 1, baby: 0, comment: '창가 자리' },
        { reservId: 3, userId: 2, regDate: 230409, adult: 5, child: 0, baby: 0, comment: '창가 자리' },
    ]

    const [tableData, setTableData] = useState(ReservData)
    const [searchFilter, setSearchFilter] = useState('reservId')
    const [searchWord, setSearchWord] = useState('')

    useEffect(() => {
        setTableData(ReservData)
    }, [])

    const searchHandler = () => {
        const filters = {
            reservId: (v) => v.reservId == searchWord,
            userId: (v) => v.userId.toString().includes(searchWord),
            regDate: (v) => v.regDate.toString().includes(searchWord),
            comment: (v) => v.comment.toString().includes(searchWord)
        };
        
        setTableData(ReservData.filter(filters[searchFilter]));
        console.log(searchFilter)
    }

    return (
        <div className='manager-user'>
            <div className="manager-search">
                <select name="filter" id="" onChange={(e) => { setSearchFilter(e.target.value) }}>
                    <option value="reservId">예약ID</option>
                    <option value="userId">고객ID</option>
                    <option value="regDate">예약일</option>
                    <option value="comment">요청사항</option>
                </select>
                <input type="text" placeholder='검색어 입력' onChange={(e) => { setSearchWord(e.target.value) }} />
                <button onClick={() => { searchHandler() }}>검색</button>
            </div>
            <table className="manager-user-list">
                <thead>
                    <tr className='manager-header'>
                        <th>예약ID</th>
                        <th>고객ID</th>
                        <th>예약일</th>
                        <th>성인</th>
                        <th>어린이</th>
                        <th>유아</th>
                        <th>요청사항</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        tableData.map((a, i) => {
                            return (
                                <tr className="manager-data" key={a.reservId}>
                                    <td>{a.reservId}</td>
                                    <td>{a.userId}</td>
                                    <td>{a.regDate}</td>
                                    <td>{a.adult}</td>
                                    <td>{a.child}</td>
                                    <td>{a.baby}</td>
                                    <td>{a.comment}</td>
                                </tr>
                            )
                        })
                    }
                </tbody>
            </table>
        </div>
    );
}

