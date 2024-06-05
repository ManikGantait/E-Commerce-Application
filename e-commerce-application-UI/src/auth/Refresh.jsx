import axios from "axios";
import { useEffect } from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const RefreshAuth=()=>{

    const navigate=useNavigate();
    const [auth,setAuth]=useState({
        userId:0,
        username:"",
        role:"CUSTOMER",
        authenticated: false,
        accessExpiration:0,
        refershExpiration:0
    });
    const doRefresh= async()=>{
        try{
            const response= axios.post('http://localhost:8080/api/v1/refreshlogin',{},
                                    {
                                        headers:{"Content-Type":"application/json"},
                                        withCredentials:true
                                    });

        console.log((await response).status)

        if((await response).status===200)
        {     
            console.log((await response).data.data)
            const userData={
              userId:(await response).data.data.userId,
              username:(await response).data.data.username,
              role:(await response).data.data.role,
              authenticated: true,
              accessExpiration:new Date(new Date().getTime()+(await response).data.data.accessExpiration),
              refreshExpiration:new Date(new Date().getTime()+(await response).data.data.refreshExpiration),
          };
          localStorage.setItem("authResponse",JSON.stringify((userData)));
          setAuth(userData);           
          
           navigate("/");           
            
          }         
        }
        catch(error)
        {
            console.log(error);

        }

    }
    const handleRefresh=()=>{
        const authResponse= localStorage.getItem("authResponse");
        console.log(authResponse)
       
        if(authResponse!=null)
        { 
            const userData=JSON.parse(authResponse); 
            const refreshExpiration=new Date(userData.refreshExpiration);  
            const accessExpiration=new Date(userData.accessExpiration);
             
            if(refreshExpiration>new Date())
             {
                if(accessExpiration>new Date())
                {                    
                    setAuth(userData);
                }else
                {
                    doRefresh();
                }
             }
           
        }
       }
       let refreshing=false;
       useEffect(()=>{
        if(!refreshing)
        {
            refreshing=true;
            handleRefresh();
        }
       },[])

    return {auth};
}
export default RefreshAuth;