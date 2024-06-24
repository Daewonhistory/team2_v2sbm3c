import React, { useEffect } from 'react';
import './css/HeaderLeft.css';
import { GrClose } from 'react-icons/gr';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import IsLoginCheck from '../backend/IsLoginCheck';
import { setUserData } from '../store/loginStore';
import axios from 'axios'; // axios 라이브러리 임포트
export default function HeaderLeft(props) {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const { loginCheckHandler } = IsLoginCheck();

    const isLogin = useSelector(state => state.loginReducer.isLogin);
    const userData = useSelector(state => state.loginReducer.userData);

    useEffect(() => {
        loginCheckHandler(); // 로그인 판별 함수
    }, []);
    function deleteAllCookies() {
        // 각 쿠키를 얻어서 삭제
        document.cookie.split(";").forEach(cookie => {
            // 쿠키 이름을 얻기 위해 문자열을 분할
            const name = cookie.split("=")[0].trim();
            // 쿠키를 과거 날짜로 설정하여 삭제
            document.cookie = name + "=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/";
        });
    }
    const navigateHandler = (link) => {
        navigate(link);
        props.setHeaderLeftOn(false);
    };

    const handleLogout = async () => {
        try {
            const response = await axios.post('http://localhost:8080/user/logout', {
                // 로그아웃 요청에 필요한 데이터를 추가하세요.
            });

            if (response.data.stat == 1) {
                deleteAllCookies();
                console.log('로그아웃 요청이 성공적으로 완료되었습니다.');
                navigateHandler('/');
            } else {
                console.log(response.data);
                console.error('로그아웃 요청이 실패하였습니다.');
            }
        } catch (error) {
            console.error('오류가 발생하였습니다:', error);
        }
    };

    return (
      <div id="header-left">
          <div className="header-left-container">
              <div className="header-left-inner">
                  <GrClose
                    className="header-left-close"
                    onClick={() => props.setHeaderLeftOn(false)}
                  />
                  <div className="inner-top">
                      {isLogin ? (
                        <>
                            <div className="sign-in">{localStorage.getItem('userName')}</div>
                            <div className="sign-up" onClick={handleLogout}>
                                로그아웃
                            </div>
                        </>
                      ) : (
                        <>
                            <div className="sign-in" onClick={() => navigateHandler('/login/sign-in')}>
                                로그인
                            </div>
                            <div className="sign-up" onClick={() => navigateHandler('/login/sign-up')}>
                                회원가입
                            </div>
                        </>
                      )}
                  </div>
                  <div className="line"></div>
                  <div className="inner-middle">
                      <div className="user-menu">
                          <h5>Member</h5>
                          <p onClick={() => navigateHandler('/reservation')}>예약</p>
                          <p
                            onClick={() => {
                                if (isLogin) {
                                    navigateHandler('/my-page/reservation');
                                } else {
                                    navigateHandler('/my-page/reservGuest');
                                }
                            }}
                          >
                              예약확인
                          </p>
                          <p>고객문의</p>
                      </div>
                      <div className="company">
                          <h5>Restaurant</h5>
                          <p>회사소개</p>
                          <p>가맹점모집</p>
                          <p>인재채용</p>
                      </div>
                      <div className="information">
                          <h5>운영시간</h5>
                          <div className="box">
                              <h6>Open</h6>
                              <p>AM11:00</p>
                          </div>
                          <div className="box">
                              <h6>Close</h6>
                              <p>PM10:00</p>
                          </div>
                          <div className="box">
                              <h6>Break Time</h6>
                              <p>PM3:30~PM5:00</p>
                          </div>
                          <div className="box">
                              <p>매주 월요일 휴무</p>
                          </div>
                      </div>
                  </div>
                  <div className="inner-bottom">
                      <p>본 사이트는 포트폴리오 용도로 만들어진 사이트입니다.</p>
                  </div>
              </div>
          </div>
      </div>
    );
}
