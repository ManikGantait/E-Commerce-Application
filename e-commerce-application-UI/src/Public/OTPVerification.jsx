import axios from "axios";
import React, { useState } from "react";
import { Link, Navigate, useNavigate } from "react-router-dom";

const OTPVerification = () => {
  const [otp, setOTP] = useState("");
  
  const navigate=useNavigate();
  

  const handleOTPChange = (event) => {
    const inputOTP = event.target.value;
    // Allow only numbers
    const regex = /^[0-9]*$/;
    if (regex.test(inputOTP) && inputOTP.length <= 6) {
      setOTP(inputOTP);
    }
  };
  const VerifyOTP=async(e)=>
  {
    e.preventDefault();
    console.log(otp);
    const email=sessionStorage.getItem('email');
    sessionStorage.removeItem('email');
    const OtpData={email:email,otp:otp};
    if(otp)
    try {
      // Send registration request to the backend server

      const response = await  axios.post('http://localhost:8080/api/v1/verify-email', OtpData, {  headers: {'Content-Type': 'application/json'  } });
      console.log(response.status)
      console.log(response.status==="202")
      console.log(response.status==="200")
      if(response.status===201)
     {      
      //  alert(response.data.message);
       navigate('/login')
     }

    }
    catch(error){
      console.error("error",error );
    }

  }

  return (
    <section className="  mt-20 flex justify-center items-center">
      <div className=" shadow-md  h-40 w-80">
         <input type="text"  value={otp} onChange={handleOTPChange} maxLength={6} placeholder="Enter OTP" className="p-2 w-full border-b-2 outline-none focus:border-blue-700" />
      <div className='mt-8 rounded-sm bg-orange-500'>
              <Link onClick={VerifyOTP} className="  flex justify-center  h-12">
                <span className='mt-3  whitespace-nowrap   text-white font-bold'>Verify OTP</span>
              </Link>
      </div>
      </div>
     
    </section>
  );
};

export default OTPVerification;
