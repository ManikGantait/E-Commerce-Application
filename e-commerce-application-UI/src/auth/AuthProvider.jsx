import React, {useState,useContext,createContext } from 'react'


//context holding the auth user details
export const authContext=createContext({});

//compponents that returns the AuthContext by enclosing its child components within the context
const AuthProvider = ({children}) => {
    const [user,setUser]=useState({
        userId:0,
        username:"",
        role:"CUSTOMER",
        authenticated: false,
        accessExpiration:0,
        refershExpiration:0
    })

    console.log(authContext)
  return (
    //returing the Authcontext  with values "user" and "setUser"
    //by enclosing the child components within it.
    
    <authContext.Provider value={{user,setUser}}>{children}</authContext.Provider>
  )
}

export default AuthProvider

export const useAuth=()=>useContext(authContext);