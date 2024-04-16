import React from "react";
import { Link } from "react-router-dom";

const Login = () => {

 
    const adjustLevel=()=>{
      document.getElementById("myLabel").classList.add('text-xs','text-gray-400','left-15','pb-15');
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
            <input placeholder="Enter Email" id='myInput'onFocus={adjustLevel} className="p-2  border-b-2  w-full outline-none focus:border-blue-700" type="text" />
            <div className='mt-8 text-xs'>By continuing, you agree to Flipkart's Terms of Use and Privacy Policy.</div>
            <div className='mt-10 w-full h-12 bg-orange-500'>
              <Link className=" flex justify-center w-full h-12">
                <span className='mt-3  w-28 whitespace-nowrap   text-white font-bold'>Sign In</span>
              </Link>
            </div>
            {/* <div className='mt-4 w-full h-12 bg-white shadow-lg'>
              <Link className=" flex justify-center w-full h-12">
                <span className='text-blue-500 mt-3 whitespace-nowrap font-bold'>Existing User? Login</span>
              </Link>
            </div> */}

          </form>
        </div>
        </div>
      
    </section>
  );
};

export default Login
