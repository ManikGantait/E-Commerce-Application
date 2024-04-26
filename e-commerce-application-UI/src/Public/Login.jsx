import axios from "axios";
import React, { useState } from "react";
import { Link, useNavigate,  } from "react-router-dom";
import { useAuth } from "../auth/AuthProvider";

const Login = () => {

  const [username, setUsername]=useState("");
  const [password,setPassword]=useState("");
  const[isValidPassword, setIsValidPassword]=useState(true);
  
  const navigate= useNavigate();
  
  const {user, setUser} =useAuth();
  
  const handlePasswordChange=(event)=>
  {
    const inputpassword = event.target.value;
    setPassword(inputpassword);

    // At least 8 characters, at least one uppercase letter, one lowercase letter, one number, and one special character
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    setIsValidPassword(passwordRegex.test(inputpassword));
  }
  const handleChangeusername=(e)=>{
    setUsername(e.target.value);
  }
 
   
  const handleSubmit=async(e)=>{
    e.preventDefault();
    const loginData={ username:username, password:password }
    console.log(loginData)
    if(isValidPassword && loginData)
    { 
      
        try {
          // Send registration request to the backend server
          const response = axios.post('http://localhost:8080/api/v1/login',
                                           loginData, 
                                           {  headers: {'Content-Type': 'application/json'  },
                                              withCredentials:'true'
                                           });
         
          console.log((await response).status)
          if((await response).status===200)
          {
            // setUser((await response).data.data);
            setUser({
              userId:(await response).data.data.userId,
              username:(await response).data.data.username,
              role:(await response).data.data.role,
              authenticated: true,
              accessExpiration:(await response).data.data.accessExpiration,
              refershExpiration:(await response).data.data.refershExpiration,
          })
           
          //  consoZle.log((await response).data.data)
          
           navigate("/");           
            
          }

         
        }
        catch(error){
          console.error("Message:",error);
        }
      
    }
    
  }

  return (
    <section className="mt-8 flex justify-center">
      <div className="flex-col justify-end   bg-blue-700 h-[500px] w-80">
        <div className=" flex-col h-[300px]">
          <div className="mt-8 px pl-10 text-white text-3xl">Login</div>
          <div className="mt-8 pl-10 text-gray-400 text-lg">
            Get access to your Orders, Wishlist and Recommendations
          </div>
        </div>

        <div className="">
          <img
            className=" pl-12"
            src="https://static-assets-web.flixcart.com/fk-p-linchpin-web/fk-cp-zion/img/login_img_c4a81e.png"
          />
        </div>
      </div>
      <div className=" h-[500px] border-2 border-red-100 w-[500px]">
        <div className=" mt-8 pl-3 pr-2 ">
          <form action="post" className=''>          
          <input onChange={handleChangeusername} placeholder='Enter Your Email'  type="email" value={username}   className='mt-2 p-2 border-b-2  w-full outline-none focus:border-blue-700' />          
             
             <input type="text" placeholder='Enter Your Password' value={password} onChange={handlePasswordChange} className={!isValidPassword?'mt-2 p-2 border-b-2  w-full outline-none focus:border-red-700':'mt-2 p-2 border-b-2  w-full outline-none focus:border-blue-700' }/>
             {!isValidPassword && (<p className="text-red-500 text-sm">At least 8 characters, at least one uppercase letter, one lowercase letter, one number, and one special character</p> )}
            
            <div className='mt-8 text-xs'>By continuing, you agree to Flipkart's Terms of Use and Privacy Policy.</div>
            <div className='mt-10 w-full h-12 bg-orange-500'>
              <Link onClick={handleSubmit} className=" flex justify-center w-full h-12">
                <span className='mt-3  w-28 whitespace-nowrap   text-white font-bold'>Sign In</span>
              </Link>
            </div>
           
          </form>
        </div>
        </div>
      
    </section>
  );
};

export default Login
