import React, { useContext } from "react";
import { RiLogoutCircleRLine } from "react-icons/ri";
import { AuthContext } from "../context/AuthContext";

const Navbar = () => {
  const { user, logout } = useContext(AuthContext);

  const handleLogout = () => {
    logout();
  };

  return (
    <nav className='bg-white shadow-md w-full '>
      <div className='max-w-[95%] mx-auto px-4 sm:px-6 lg:px-8'>
        <div className='flex justify-between items-center h-16'>
          <div className='flex-shrink-0 flex items-center text-2xl text-blue-800 font-medium'>
            KarizmaVolunteers
          </div>

          {user && (
            <div className='flex items-center gap-2'>
              <p className=" font-medium">{user.firstName} {user.lastName}</p>
              <button
                className='cursor-pointer inline-flex items-center px-4 py-2  hover:scale-105 text-2xl text-blue-800 transition-all duration-200'
                onClick={handleLogout}
              >
                <RiLogoutCircleRLine />
              </button>
            </div>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
