import React, {useState,useContext,createContext, useEffect } from 'react'
import RefreshAuth from './Refresh';


//context holding the auth user details
export const authContext=createContext({});

//compponents that returns the AuthContext by enclosing its child components within the context
const AuthProvider = ({children}) => {
  const {auth}=RefreshAuth();

    const [user,setUser]=useState({
        userId:0,
        username:"",
        role:"CUSTOMER",
        authenticated: false,
        accessExpiration:0,
        refershExpiration:0
    })

    useEffect(()=>{
      setUser(auth)
    },[auth])

    
  return (
    //returing the Authcontext  with values "user" and "setUser"
    //by enclosing the child components within it.
    
    <authContext.Provider value={{user,setUser}}>{children}</authContext.Provider>
  )
}

export default AuthProvider

export const useAuth=()=>useContext(authContext);