import { configureStore } from '@reduxjs/toolkit'
import loginReducer from './loginStore'
import reservReducer from './reservStore';

export default configureStore({
    reducer: {
        loginReducer: loginReducer,
        reservReducer: reservReducer,
    }
});