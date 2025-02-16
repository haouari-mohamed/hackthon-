import React, { useContext, useState } from "react";
import { RiDashboardLine, RiUserLine } from "react-icons/ri";
import { Link, Outlet } from "react-router";
import { MdEvent } from "react-icons/md";
import { AuthContext } from "../context/AuthContext";
import { BiSolidBookAdd } from "react-icons/bi";

const Sidebar = () => {
  const { user, loading } = useContext(AuthContext);
  const [activeItem, setActiveItem] = useState("Available Events");

  let isOrganisation;
  if (user) {
    isOrganisation = user.role === "ORGANIZATION";
  }

  return (
    <>
      {user && !isOrganisation && (
        <div className='min-h-screen h-full w-1/6 bg-white'>
          <div className='flex flex-col py-4'>
            <Link
              to='/events'
              onClick={() => setActiveItem("Available Events")}
              className={`flex items-center px-6 py-3 transition-colors duration-200 text-gray-800 ${
                activeItem === "Available Events"
                  ? "bg-blue-800 text-white"
                  : "hover:bg-blue-600 hover:text-white"
              }`}
            >
              <span className='inline-flex items-center justify-center'>
                <RiDashboardLine />
              </span>
              <span className='ml-3 text-sm font-medium'>Available Events</span>
            </Link>

            <Link
              to='/my-events'
              onClick={() => setActiveItem("My Events")}
              className={`flex items-center px-6 py-3 transition-colors duration-200 text-gray-800 ${
                activeItem === "My Events"
                  ? "bg-blue-800 text-white"
                  : "hover:bg-blue-800 hover:text-white"
              }`}
            >
              <span className='inline-flex items-center justify-center'>
                <MdEvent />
              </span>
              <span className='ml-3 text-sm font-medium'>My Events</span>
            </Link>

            <Link
              to='/profile'
              onClick={() => setActiveItem("Profile")}
              className={`flex items-center px-6 py-3 transition-colors duration-200 text-gray-800 ${
                activeItem === "Profile"
                  ? "bg-blue-800 text-white"
                  : "hover:bg-blue-800 hover:text-white"
              }`}
            >
              <span className='inline-flex items-center justify-center'>
                <RiUserLine />
              </span>
              <span className='ml-3 text-sm font-medium'>Profile</span>
            </Link>

            <Link
              to='/newEvent'
              onClick={() => setActiveItem("New Event")}
              className={`flex items-center px-6 py-3 transition-colors duration-200 text-gray-800 ${
                activeItem === "New Event"
                  ? "bg-blue-800 text-white"
                  : "hover:bg-blue-800 hover:text-white"
              } ${isOrganisation ? "block" : "hidden"}`}
            >
              <span className='inline-flex items-center justify-center'>
                <BiSolidBookAdd />
              </span>
              <span className='ml-3 text-sm font-medium'>New Event</span>
            </Link>
          </div>
        </div>
      )}

      {user && isOrganisation && (
        <div className='min-h-screen h-full w-1/6 bg-white'>
          <div className='flex flex-col py-4'>
            <Link
              to='/my-events'
              onClick={() => setActiveItem("My Events")}
              className={`flex items-center px-6 py-3 transition-colors duration-200 text-gray-800 ${
                activeItem === "My Events"
                  ? "bg-blue-800 text-white"
                  : "hover:bg-blue-800 hover:text-white"
              }`}
            >
              <span className='inline-flex items-center justify-center'>
                <MdEvent />
              </span>
              <span className='ml-3 text-sm font-medium'>My Events</span>
            </Link>

            <Link
              to='/newEvent'
              onClick={() => setActiveItem("New Event")}
              className={`flex items-center px-6 py-3 transition-colors duration-200 text-gray-800 ${
                activeItem === "New Event"
                  ? "bg-blue-800 text-white"
                  : "hover:bg-blue-800 hover:text-white"
              }`}
            >
              <span className='inline-flex items-center justify-center'>
                <BiSolidBookAdd />
              </span>
              <span className='ml-3 text-sm font-medium'>New Event</span>
            </Link>

            <Link
              to='/profile'
              onClick={() => setActiveItem("Profile")}
              className={`flex items-center px-6 py-3 transition-colors duration-200 text-gray-800 ${
                activeItem === "Profile"
                  ? "bg-blue-800 text-white"
                  : "hover:bg-blue-800 hover:text-white"
              }`}
            >
              <span className='inline-flex items-center justify-center'>
                <RiUserLine />
              </span>
              <span className='ml-3 text-sm font-medium'>Profile</span>
            </Link>
          </div>
        </div>
      )}
    </>
  );
};

export default Sidebar;
