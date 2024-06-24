import { combineReducers, createSlice } from "@reduxjs/toolkit";

// 현재 로그인 중인지 여부
const isLogin = createSlice({
    name: 'isLogin',
    initialState: localStorage.getItem('isLogin'),
    reducers: {
        setIsLogin(state, action) {
            return action.payload; 
        }
    }
});

export const { setIsLogin } = isLogin.actions;

// 로그인한 사용자 정보
const userData = createSlice({
    name: 'userData',
    initialState: {'name': localStorage.getItem('userName'), 'email':'', 'phone' : '', 'point': ''},
    reducers: {
        setUserData(state, action) {
            return action.payload; 
        }
    }
});

export const { setUserData } = userData.actions;

// 로그인 시 이메일
const signInEmail = createSlice({
    name: 'signInEmail',
    initialState: '',
    reducers: {
        setSignInEmail(state, action) {
            return action.payload; 
        }
    }
});

export const { setSignInEmail } = signInEmail.actions;

// 로그인 시 패스워드
const signInPw = createSlice({
    name: 'signInPw',
    initialState: '',
    reducers: {
        setSignInPw(state, action) {
            return action.payload; 
        }
    }
});

export const { setSignInPw } = signInPw.actions;

// 회원가입 시 이메일
const signUpEmail = createSlice({
    name: 'signUpEmail',
    initialState: '',
    reducers: {
        setSignUpEmail(state, action) {
            return action.payload; 
        }
    }
});

export const { setSignUpEmail } = signUpEmail.actions;

// 회원가입 시 패스워드
const signUpPw = createSlice({
    name: 'signUpPw',
    initialState: '',
    reducers: {
        setSignUpPw(state, action) {
            return action.payload; 
        }
    }
});

export const { setSignUpPw } = signUpPw.actions;

// 회원가입 시 휴대폰
const signUpPhone = createSlice({
    name: 'signUpPhone',
    initialState: '',
    reducers: {
        setSignUpPhone(state, action) {
            return action.payload; 
        }
    }
});

export const { setSignUpPhone } = signUpPhone.actions;

// 회원가입 시 이름
const signUpName = createSlice({
    name: 'signUpName',
    initialState: '',
    reducers: {
        setSignUpName(state, action) {
            return action.payload; 
        }
    }
});

export const { setSignUpName } = signUpName.actions;

// 회원가입 이메일 중복 체크
const emailDupli = createSlice({
    name: 'emailDupli',
    initialState: false,
    reducers: {
        setEmailDupli(state, action) {
            return action.payload; 
        }
    }
});

export const { setEmailDupli } = emailDupli.actions;

// 회원가입 이메일 인증 완료 여부
const emailCheck = createSlice({
    name: 'emailCheck',
    initialState: false,
    reducers: {
        setEmailCheck(state, action) {
            return action.payload; 
        }
    }
});

export const { setEmailCheck } = emailCheck.actions;

// 회원가입 휴대폰 인증 완료 여부
const phoneCheck = createSlice({
    name: 'phoneCheck',
    initialState: false,
    reducers: {
        setPhoneCheck(state, action) {
            return action.payload; 
        }
    }
});

export const { setPhoneCheck } = phoneCheck.actions;

// 이메일, 휴대전화로 받은 인증문자
const receiveText = createSlice({ 
    name: 'receiveText',
    initialState: '1234',
    reducers: {
        setReceiveText(state, action) {
            return action.payload; 
        }
    }
});

export const { setReceiveText } = receiveText.actions;

const loginReducer = combineReducers({
    isLogin: isLogin.reducer,
    userData: userData.reducer,
    signInEmail: signInEmail.reducer,
    signInPw: signInPw.reducer,

    signUpEmail: signUpEmail.reducer,
    signUpPw: signUpPw.reducer,
    signUpName: signUpName.reducer,
    signUpPhone: signUpPhone.reducer,

    emailDupli: emailDupli.reducer,
    emailCheck: emailCheck.reducer,
    phoneCheck: phoneCheck.reducer,
    receiveText: receiveText.reducer
});

export default loginReducer;