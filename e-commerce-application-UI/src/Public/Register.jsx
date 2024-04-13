import React, { useState } from 'react'
import { Link } from 'react-router-dom';

const Register = () => {
  const adjustLevel=()=>{
    document.getElementById("myLabel").classList.add('text-xs','text-gray-400','left-15','pb-15');
  }
  const [email, setEmail] = useState('');
  const [isValidEmail, setIsValidEmail] = useState(true);
  const [password, setPassword] = useState('');
  const [isValidPassword, setIsValidPassword] = useState(true);
  const [username, setUsername] = useState('');

  const handleChangeEmail = (event) => {
      const inputValue = event.target.value;
      setEmail(inputValue);

      // Regular expression for email validation
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      setIsValidEmail(emailRegex.test(inputValue));
  };
  const handleChangePassword = (event) => {
    const inputValue = event.target.value;
    setPassword(inputValue);

    // At least 8 characters, at least one uppercase letter, one lowercase letter, one number, and one special character
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    setIsValidPassword(passwordRegex.test(inputValue));
  };  

  const handleChangeName = (event) => {
      // Convert the entered name to uppercase
      const inputValue = event.target.value.toUpperCase();
      setUsername(inputValue);
  };
  const handleSubmit=(event)=>{
    console.log("submit")
    if(isValidEmail&&isValidPassword)
    {
      console.log(email,password,username)
    }
    else{
      console.log("invalid")
    }
  }

  return (
    <section className="mt-8 flex justify-center">
      <div className="flex-col justify-end   bg-blue-700 h-[500px] w-80">
        <div className=" flex-col h-[300px]">
          <div className="mt-8 pl-8 text-white text-3xl">Looks like you're new here!</div>
          <div className="mt-8 pl-8 text-gray-400 text-lg">
          Sign up with your email  to get started
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
        <div className="relative mt-8 pl-3 pr-2 ">
          <form  onSubmit={handleSubmit} >   
            <input type="text" placeholder='Enter your Name' value={username} onChange={handleChangeName} className='p-2 border-b-2  w-full outline-none focus:border-blue-700'/>
            
            <input placeholder='Enter Your Email'  type="email" value={email}  onChange={handleChangeEmail} className={!isValidEmail ? 'mt-2 p-2 border-b-2  w-full outline-none border-red-500' : 'mt-2 p-2 border-b-2  w-full outline-none focus:border-blue-700'} />          
            {!isValidEmail && (<p className="text-red-500 text-sm">Please enter a valid email address</p> )}
             
             <input type="text" placeholder='Enter Your Password' value={password} onChange={handleChangePassword} className={!isValidPassword?'mt-2 p-2 border-b-2  w-full outline-none focus:border-red-700':'mt-2 p-2 border-b-2  w-full outline-none focus:border-blue-700' }/>
             {!isValidPassword && (<p className="text-red-500 text-sm">At least 8 characters, at least one uppercase letter, one lowercase letter, one number, and one special character</p> )}
            
            <div className='mt-8 text-xs'>By continuing, you agree to Flipkart's Terms of Use and Privacy Policy.</div>
            <div className='mt-4 w-full h-12 bg-orange-500' >
              <Link onClick={handleSubmit} className=" flex justify-center w-full h-12" >
                <span className='mt-3  w-28 whitespace-nowrap   text-white font-bold'>Sign Up</span>
              </Link>
            </div>
            <div className='mt-4 w-full h-12 bg-white shadow-lg'>
              <Link onClick={()=>window.location.href="./login"} className=" flex justify-center w-full h-12">
                <span className='text-blue-500 mt-3 whitespace-nowrap font-bold'>Existing User? Login</span>
              </Link>
            </div>

          </form>
        </div>
      
      </div>
    </section>
  )
}

export default Register