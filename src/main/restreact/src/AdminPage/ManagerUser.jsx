import React, { useEffect, useState } from 'react';
import axios from 'axios';

export default function AdminUser() {
  const [tableData, setTableData] = useState([]);
  const [searchFilter, setSearchFilter] = useState('100');
  const [searchWord, setSearchWord] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const recordsPerPage = 10;

  // 백엔드에서 데이터를 가져오는 함수
  const fetchData = () => {
    axios.get(`http://localhost:9093/api/manager/customer`, {
      params: {
        type: searchFilter,
        word: searchWord,
        nowPage: currentPage,
        recordPerPage: recordsPerPage
      }
    })
        .then((res) => {
          console.log(res.data);
          if (res.data && res.data.users) {
            setTableData(res.data.users); // 데이터가 있는 경우 테이블 데이터 설정
            setTotalPages(res.data.totalPages); // 전체 페이지 수 설정
          } else {
            setTableData([]); // 데이터가 없는 경우 빈 배열 설정
            setTotalPages(1); // 전체 페이지 수를 1로 설정 (데이터가 없는 경우도 페이지는 최소 1 페이지로 처리)
          }
        })
        .catch((error) => {
          console.error("데이터를 불러오는 데 실패했습니다.", error);
        });
  };

  // 데이터, 검색 필터, 페이지가 변경될 때 데이터를 다시 가져옴
  useEffect(() => {
    fetchData();
  }, [currentPage, searchFilter, searchWord]);

  // 검색 처리 함수
  const searchHandler = () => {
    setCurrentPage(1); // 검색할 때 페이지를 1로 리셋
    fetchData();
  };

  // 페이지 변경 처리 함수
  const handlePageChange = (page) => {
    setCurrentPage(page);
  };

  return (
      <div className="manager-user">
        <div className="manager-search">
          <select name="filter" value={searchFilter} onChange={(e) => setSearchFilter(e.target.value)}>
            <option value="id">고객ID</option>
            <option value="email">이메일</option>
            <option value="cname">이름</option>
            <option value="phone">전화번호</option>
          </select>
          <input
              type="text"
              placeholder="검색어 입력"
              value={searchWord}
              onChange={(e) => setSearchWord(e.target.value)}
          />
          <button onClick={searchHandler}>검색</button>
        </div>
        <table className="manager-user-list">
          <thead>
          <tr className="manager-header">
            <th>고객ID</th>
            <th>이메일</th>
            <th>이름</th>
            <th>전화번호</th>
          </tr>
          </thead>
          <tbody>
          {tableData.length > 0 ? (
              tableData.map((user) => (
                  <tr className="manager-data" key={user.id}>
                    <td>{user.id}</td>
                    <td>{user.email}</td>
                    <td>{user.cname}</td>
                    <td>{user.phone}</td>
                  </tr>
              ))
          ) : (
              <tr>
                <td colSpan="4">데이터가 없습니다.</td>
              </tr>
          )}
          </tbody>
        </table>
        <div className="pagination-container">
          <button
              className="page-button"
              disabled={currentPage === 1}
              onClick={() => currentPage > 1 && handlePageChange(currentPage - 1)}
          >
            이전
          </button>
          {Array.from({ length: totalPages }).map((_, index) => (
              <button
                  key={index}
                  className={`page-button ${currentPage === index + 1 ? 'active' : ''}`}
                  onClick={() => handlePageChange(index + 1)}
              >
                {index + 1}
              </button>
          ))}
          <button
              className="page-button"
              disabled={currentPage === totalPages}
              onClick={() => currentPage < totalPages && handlePageChange(currentPage + 1)}
          >
            다음
          </button>
        </div>
      </div>
  );
}
