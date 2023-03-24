import React from 'react'
import { Outlet, Navigate } from 'react-router-dom'
import { ifTokenExpired } from '../components/service/userService'

const ProtectedRoutes = () => {
    return(
        sessionStorage.getItem('Authorization') && ifTokenExpired() ? <Outlet/> : <Navigate to="/"/>
    )
}
export default ProtectedRoutes
