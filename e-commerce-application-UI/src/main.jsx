import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import AllRoutes from './Routes/AllRoutes.jsx'
import AuthProvider, { authContext } from './auth/AuthProvider';



ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <BrowserRouter>
       <AuthProvider children={<AllRoutes/>} />      
    </BrowserRouter>
  </React.StrictMode>,
)
