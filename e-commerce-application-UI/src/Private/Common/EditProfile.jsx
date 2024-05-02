import React from 'react'
import { Link } from 'react-router-dom'

const EditProfile = () => {
  return (
    <div>
      <Link onClick={()=>window.location.href="/addaddress"} >Add Address</Link>
    </div>
  )
}

export default EditProfile