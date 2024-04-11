import React, { useRef, useState } from "react";
import { Link } from "react-router-dom";
import Register from "../Public/Register";
import { VscAccount, VscChevronDown,VscChevronUp} from "react-icons/vsc";
import { GoSearch } from "react-icons/go";
import { FiGift } from "react-icons/fi";
import { RxDotsVertical } from "react-icons/rx";
import Wishlist from '../Private/Customer/Wishlist';
import { TiPlusOutline } from "react-icons/ti";
import { BsBoxSeam } from "react-icons/bs";
import { IoHeartOutline } from "react-icons/io5";
import { GoGift } from "react-icons/go";
import { AiOutlineCreditCard } from "react-icons/ai";
import { BsBell } from "react-icons/bs";
import { TfiHeadphoneAlt } from "react-icons/tfi";
import { HiArrowDownTray } from "react-icons/hi2";
import { HiArrowTrendingUp } from "react-icons/hi2";
import Login from "../Public/Login";





const Header = () => {
  const [isOpenLogin, setIsOpenLogin] = useState(false);
  const [isOpenNotifications, setIsOpenSeler] = useState(false);
  const [isDown, setIsDown]=useState(true);
 
  const handleDown=()=>{
    setIsDown(false);
  }
  const handleUp=()=>{
    setIsDown(true);
  }
  

  // Function to handle mouse enter event for login
  const handleMouseEnterLogin = () => {
    setIsOpenLogin(true);   
  };

 // Function to handle mouse leave event for login 
  let timeoutIdLogin;
  const handleMouseLeaveLogin = () => {
    timeoutIdLogin= setTimeout(() => {
      setIsOpenLogin(false);
    }, 200);
  };
 // Function to handle mouse enter event for seler
  const handleMouseEnterNotification = () => {
    setIsOpenSeler(true);
  };

  // Function to handle mouse leave event for seler
  let timeoutIdNotification;
  const handleMouseLeaveNotification = () => {
    timeoutIdNotification=setTimeout(() => {
      setIsOpenSeler(false);
    }, 200);;
  };
  const handleMouseEnterBoth = () => {
    handleMouseEnterLogin();
    handleDown();
  };
  const handleMouseLeavBoth = () => {
    handleMouseLeaveLogin();
    handleUp();
  };
  

  return (
    <section>
      <nav className="bg-white shadow-md rounded">
        <div className="flex justify-between ">
          <div className="flex w-1/2 ">
               <form className="flex items-center" action="">
                  <div className="p-5 ">
                    <img onClick={()=>window.location.href="/"} src="https://static-assets-web.flixcart.com/batman-returns/batman-returns/p/images/fkheaderlogo_exploreplus-44005d.svg" />
                  </div>
                  <div className=" flex relative bg-sky-50 rounded-md h-11">
                     <div className="p-3"> <GoSearch />  </div>
                     <input
                        type="text"
                        placeholder="Search for products, brands and more"
                        className=" px-4 py-2 w-80 sm:w-96 focus:outline-none focus:border-blue-500  bg-sky-50 rounded-md"
                       />
                  </div>
               </form>
          </div>

          <div className="flex justify-around  w-2/5 relative"   >
              <div  className="flex justify-between relative">

              <div className="flex items-center "  onMouseEnter={ handleMouseEnterBoth} onMouseLeave={handleMouseLeavBoth}>
                 <Link  className="flex p-3 rounded-lg hover:bg-blue-900 hover:text-white" onClick={()=>window.location.href="/login"}>
                 <VscAccount/>
                    <span className="pl-3">Login</span>
                     {isDown ?<VscChevronDown/>:<VscChevronUp/>}
                 </Link>
                 {isOpenLogin && (
                          <div className="absolute top-full left-0 bg-white shadow-md rounded  py-1 w-64"  onMouseEnter={handleMouseEnterBoth} onMouseLeave={handleMouseLeavBoth}> 
                          <Link href="" className="block px-4 py-2 text-gray-800 hover:bg-gray-200">New Customer? <span className="text-blue-600 pl-8">Sign Up</span></Link>
                          <Link href="#" className="flex block px-4 py-2 text-gray-800 hover:bg-gray-200" title="My Profile"><VscAccount/><span className="pl-7">My Profile</span></Link>
                          <Link href="#" className="flex block px-4 py-2 text-gray-800 hover:bg-gray-200" title="Flipkart Plus Zone"><TiPlusOutline/><span className="pl-7">Flipkart Plus Zone</span></Link>
                          <Link href="#" className="flex block px-4 py-2 text-gray-800 hover:bg-gray-200" title="Orders"><BsBoxSeam/><span className="pl-7">Orders</span></Link>
                          <Link onClick={()=>window.location.href="/wishlist"} className="flex block px-4 py-2 text-gray-800 hover:bg-gray-200" title="Wishlist"><IoHeartOutline/><span className="pl-7">Wishlist</span></Link>
                          <Link href="#" className="flex block px-4 py-2 text-gray-800 hover:bg-gray-200" title="Rewards"><GoGift/><span className="pl-7">Rewards</span></Link>
                          <Link href="#" className="flex block px-4 py-2 text-gray-800 hover:bg-gray-200" title="Gift Cards"><AiOutlineCreditCard/><span className="pl-7">Gift Cards</span></Link>
                        </div>
                      )}
              </div>
              
              </div>
              <div className="flex items-center " >
                   <button className="flex p-3 rounded-lg hover:bg-blue-900 hover:text-white">
                   <FiGift/>
                      <span className="pl-3" title="Become a Seller">Become a Seller</span>
                     </button>
              </div>
              <div  className="flex justify-between relative">

                 <div className="flex items-center "  onMouseEnter={ handleMouseEnterNotification} onMouseLeave={handleMouseLeaveNotification}>
                   <button className="flex p-3 rounded-lg hover:bg-blue-900 hover:text-white" title="dropdown with more help links">
                    <RxDotsVertical/>                  
                     </button>
                     {isOpenNotifications && (
                            <div className="absolute top-full right-0 bg-white shadow-xl rounded  py-1 w-64"  onMouseEnter={clearTimeout(timeoutIdNotification)} onMouseLeave={handleMouseLeaveNotification}> 
                            <Link href="#" className="flex justify-center block py-2 py-2 text-gray-800 hover:bg-gray-200" title="Notifications preferences" ><BsBell/><span className="text-base pl-7">Notifications preferences</span></Link>
                            <Link href="#" className="flex  block px-4 py-2 text-gray-800 hover:bg-gray-200 " title="24x7 Customer Care"><TfiHeadphoneAlt/><span className="pl-7">24x7 Customer Care</span> </Link>
                            <Link href="#" className="flex block px-4 py-2 text-gray-800 hover:bg-gray-200 " title="Advertise"> <HiArrowTrendingUp/><span className="pl-7">Advertise</span></Link>
                            <Link href="#" className="flex block px-4 py-2 text-gray-800 hover:bg-gray-200 " title="Download App"><HiArrowDownTray/><span className="pl-7">Download App</span></Link>
                          </div>
                        )}
                </div>
                
                </div>
             
          </div>
        </div>
      </nav>
    </section>
  );
};

export default Header;
