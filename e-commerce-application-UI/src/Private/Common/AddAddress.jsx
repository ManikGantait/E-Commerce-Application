import axios from 'axios';
import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'

const AddAddress = () => {

  const[streetAddress, setStreetAddesss]=useState('');
  const[streetAddressAdditonal ,setStreetAddressAdditonal ]=useState("");
  const[city, setCity]=useState("");
  const[state,setState]=useState("");
  const[pincode,setPincode]=useState(0);
  const[addressType,setAddressType]=useState("");
  const navigate=useNavigate();

  const handleChangeStreetAddress=(event)=>{
    setStreetAddesss(event.target.value);
  }
  const handleChangeStreetAddressAdditonal=(event)=>{
    setStreetAddressAdditonal(event.target.value);
  }
  const handleCity=(event)=>{
    setCity(event.target.value);
  }
  const handleState=(event)=>{
    setState(event.target.value);
  }
  const handlePincode=(event)=>{
    setPincode(event.target.value);
  }
  const handleAddressType=(event)=>{
    setAddressType(event.target.value);
  }


  const handleSubmit= async(e)=>{

    const formData={
      streetAddress:streetAddress,
      streetAddressAdditonal:streetAddressAdditonal,
      city:city,
      state:state,
      pincode:pincode,
      addressType:addressType
    }
    console.log(formData);

    const response=axios.post("http://localhost:8080/api/v1/address", formData,{ headers:{'Content-Type':'application/json'},withCredentials:true});
    if((await response).status===200)
    {
      console.log(response);
      console.log((await response).data.data.addressId);
      const addressId=(await response).data.data.addressId;

      navigate(`/addcontact/${addressId}`)
    }

    
  }

  return (
   <section>
    <div className='flex justify-center align-middle '>
      <div className='  mt-20 mb-20  border shadow-xl'>
        <form action="" method="post">
            <span className='font-bold text-blue-500 ml-60'>Manage Address</span> <br />
            <input onChange={handleChangeStreetAddress} value={streetAddress}  type="text" className='mt-5 ml-7 w-3/4  border-2 border-blue-500 rounded-lg shadow-lg outline-none p-5' placeholder='Enter Street Address (required*)' /> <br />
            <input onChange={handleChangeStreetAddressAdditonal} value={streetAddressAdditonal} type="text" className='mt-5 ml-7 w-3/4 border-2 border-blue-500 rounded-lg shadow-lg outline-none p-5' placeholder='Enter Street Address Additional (required*)' /> <br />
            <input onChange={handleCity} value={city} type="text" className='mt-5 ml-7 w-3/4 border-2 border-blue-500 rounded-lg shadow-lg outline-none p-5' placeholder='Enter city (required*) ' /> <br />
            <input onChange={handleState} value={state} type="text" className='mt-5 ml-7 w-3/4 border-2 border-blue-500 rounded-lg shadow-lg outline-none p-5' placeholder='Enter State (required*) ' /><br />
            <input onChange={handlePincode} value={pincode} type="text" className='mt-5 ml-7 border-2 border-blue-500 rounded-lg shadow-lg outline-none p-5' placeholder='Enter Pincode (required*)' />
            <input onChange={handleAddressType} value={addressType} type="text" className='mt-5 ml-7 mr-3 border-2 border-blue-500 rounded-lg shadow-lg outline-none p-5' placeholder='Enter address Type (required*)' /> <br />
            <div className='flex justify-center w-full  h-20 '>
              <Link onClick={handleSubmit} className='mt-5 w-72 rounded-lg shadow-lg h-12 bg-orange-400 flex justify-center '> <span className='mt-3 text-white font-bold'>Save Address</span> </Link>
            </div>            
        </form>
        
        
      </div>
    </div>
   </section>
  )
}

export default AddAddress