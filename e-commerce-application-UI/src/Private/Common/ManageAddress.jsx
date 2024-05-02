import axios from 'axios'
import React from 'react'

const ManageAddress = () => {
    const response =axios.get("http://localhost:8080/api/v1/address",{},{withCredentials:true});

  return (
    <section>
      <p>{response.data.data}</p>
    </section>
  )
}

export default ManageAddress