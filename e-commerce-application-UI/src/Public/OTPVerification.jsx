import React, { useState } from "react";
import { Link } from "react-router-dom";

const OTPVerification = () => {
  const [otp, setOTP] = useState("");

  const handleOTPChange = (event) => {
    const inputOTP = event.target.value;
    // Allow only numbers
    const regex = /^[0-9]*$/;
    if (regex.test(inputOTP) && inputOTP.length <= 6) {
      setOTP(inputOTP);
    }
  };

  return (
    <section className="  mt-20 flex justify-center items-center">
      <div className=" shadow-md  h-40 w-80">
         <input type="text" value={otp} onChange={handleOTPChange} maxLength={6} placeholder="Enter OTP" className="p-2 w-full border-b-2 outline-none focus:border-blue-700" />
      <div className='mt-8 rounded-sm bg-orange-500'>
              <Link className="  flex justify-center  h-12">
                <span className='mt-3  whitespace-nowrap   text-white font-bold'>Verify OTP</span>
              </Link>
      </div>
      </div>
     
    </section>
  );
};

export default OTPVerification;
