import React from 'react'

const Login = () => {
  return (
   <section className='flex justify-center'>
    <div className='bg-blue'>

    </div>
     <div className="flex justify-center items-center h-screen bg-gray-100">
      <div className="bg-white p-8 rounded shadow-md w-96">
        <h2 className="text-2xl font-semibold mb-4">Login to Flipkart</h2>
        <form method='post'>
          <div className="mb-4">
            <label htmlFor="email" className="block text-sm font-medium text-gray-600">Email</label>
            <input type="email" id="email" name="email" className="mt-1 p-2 w-full border rounded-md" />
          </div>
          <div className="mb-6">
            <label htmlFor="password" className="block text-sm font-medium text-gray-600">Password</label>
            <input type="password" id="password" name="password" className="mt-1 p-2 w-full border rounded-md" />
          </div>
          <button type="submit" className="bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600">Login</button>
        </form>
      </div>
    </div>
   </section>
  )
}

export default Login