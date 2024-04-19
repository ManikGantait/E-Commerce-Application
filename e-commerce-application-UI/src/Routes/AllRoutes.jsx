import React from 'react'
import { Route, Routes } from 'react-router-dom';
import App from '../App';
import Explore from '../Private/Customer/Explore';
import Cart from '../Private/Customer/Cart';
import AddProduct from '../Private/Seller/AddProduct';
import SellerDashBord from '../Private/Seller/SellerDashBord';
import AddAddress from '../Private/Common/AddAddress';
import EditProfile from '../Private/Common/EditProfile';
import Wishlist from '../Private/Customer/Wishlist';
import Home from '../Public/Home';
import Login from '../Public/Login';
import Register from '../Public/Register';
import OTPVerification from '../Public/OTPVerification';

const AllRoutes = () => {
 const user={
    role:"CUSTOMER",
    authenticated: false,
    accessExpiration:3600,
    refershExpiration:129600
 }
const{role, authenticated}=user;
let routes=[];

if(authenticated)
{
    // Private Routes

    routes.push(
        <Route key={'/addaddress'} path='/addaddress' element={ <AddAddress />}/>,
        <Route key={'/addaddress'}path='/editprofile' element={ <EditProfile />}/>
    )

    if(role==="CUSTOMER") 
    {
        // Private Customer Routes
        routes.push(             
            <Route key={'/wishlist'} path='/wishlist' element={ <Wishlist />}/>,
            <Route key={'/explore'} path='/explore' element={ <Explore />}/>,
            <Route key={'/cart'} path='/cart' element={ <Cart />}/>,
        ) 
    }
    else if(role==="SELLER")
    {
        // Private Seller Routes
        routes.push(
            <Route key={'/add-product'} path='/add-product' element={ <AddProduct />}/>,
            <Route key={'/seller-dashbord'} path='/seller-dashbord' element={ <SellerDashBord />}/>
        )
    }
}
else{

    // Public Routes
    routes.push(
        
        <Route key={'/'} path='/' element={ <Home/>}/>,
        <Route key={'/login'} path='/login' element={ <Login />}/>,
        <Route key={'/customer/register'} path='/customer/register' element={ <Register role={"CUSTOMER"} />}/>, 
        <Route key={'/seller/register'} path='/seller/register' element={ <Register role={"SELLER"} />}/>, 
        <Route key={'/explore'} path='/explore' element={ <Explore />}/>, 
        <Route key={'/otpverification'} path='/otpverification' element={ <OTPVerification />}/>,           
        
    ) ; 

}

return (
    <Routes> <Route path='/' element={<App/>}>{routes}</Route></Routes>

);

}

export default AllRoutes