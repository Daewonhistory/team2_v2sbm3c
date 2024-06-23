import React, { useEffect } from 'react';
import { Route, Routes, useLocation } from 'react-router-dom';
import './App.css'
import "./index.css";








import AdminPage from './AdminPage/ManagerPage';

export default function App() {
  const location = useLocation();
  const isAdminPage = location.pathname.startsWith('/admin');

  return (
    <div>
   
      <Routes>
        <Route path='/manager/*' element={<AdminPage />} />
       

      </Routes>

    </div>
  );
}


