import axios from 'axios';
import React, { useState } from 'react'
import { Link, useParams } from 'react-router-dom';

const ContactFrom = () => {
    const[name,setName]=useState("");
    const[email,setEmail]=useState("");
    const[phoneNumber,setPhoneNumber]=useState("");
    const[priority,setPriority]= useState("PRIMARY")
    const[isValidPhoneNumber,setIsValidPhoneNumber]=useState(true);
    const { addressId } = useParams();

    

    const handlePriority = (event) => setPriority(event.target.value);
    const handelPhoneNumber=(e)=>{ 
        const numberRegx = /^\d{10}$/;
        const inputValue=e.target.value;
        setPhoneNumber(inputValue);
        setIsValidPhoneNumber(numberRegx.test(inputValue))    
       }
    
  
  const handleSubmit=async()=>{
    const formData=[
      {
        name:name,
        email:email,
        phoneNumber:phoneNumber,
        priority: priority,
      }
    ]
    const response=axios.post(`http://localhost:8080/api/v1/contact/${addressId}`,formData,{headers:{'Content-Type':'application/json'},withCredentials:true});
    if((await response).status===200)
    {
      navigator("/manageAddress")
     
    }
  }

    
  return (
    <section>
        <div className='flex justify-center align-middle'>
            <div className='border-4 mt-10 '>            
                <form action=""  >
                    <input onChange={(e)=>{setName(e.target.value)}}  type="text" value={name} placeholder='Enter Name'    className='mt-5 ml-5 mr-4 w-80  border-2 border-blue-500 rounded-lg shadow-lg outline-none p-5' /><br />
                    <input onChange={(e)=>{setEmail(e.target.value)}}  type="email" value={email} placeholder='Enter Email' className='mt-5 ml-5 w-80  border-2 border-blue-500 rounded-lg shadow-lg outline-none p-5'  /> <br />
                    <input onChange={handelPhoneNumber}  type="tel" value={phoneNumber} placeholder='Enter number' className='mt-5 ml-5 w-80  border-2 border-blue-500 rounded-lg shadow-lg outline-none p-5' /><br />
                    {!isValidPhoneNumber&&(<span className='text-red-500'>Plase enter valid number</span>)}<br />
                    <input  type="radio" value={"PRIMARY"}  checked={priority==="PRIMARY"} onChange={handlePriority} className='ml-5' />
                    <input  type="radio"  value={"ADDITIONAL"} checked={priority==="ADDITIONAL"} onChange={handlePriority} className='ml-5'/> <br />

                    {priority&&(<span className='ml-3'>{priority}</span>)}     
                    <div className='flex justify-center w-full  h-20 '>
                       <Link onClick={handleSubmit} className='mt-5 w-72 rounded-lg shadow-lg h-12 bg-orange-400 flex justify-center '> <span className='mt-3 text-white font-bold'>Add Contact</span> </Link>
                     </div>
                </form>          
            </div>
        </div>
    </section>
  )
}

export default ContactFrom