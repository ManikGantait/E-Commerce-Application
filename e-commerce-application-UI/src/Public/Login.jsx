import React from "react";

const Login = () => {
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
      <div className="h-[500px] bg-red-200 w-[500px]">
        <div className="bg-green-200 mt-8 pl-8">
          <form action="post">
            <input className="p-2 border-none	  w-full outline-none	" type="text" />
            <label className="absolute translate-y-(50%) origin-left 	left-25	">Enter Email/Mobile number</label>
          </form>
        </div>
      
      </div>
    </section>
  );
};

export default Login;
