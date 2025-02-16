import React, { useState, useEffect, useContext } from "react";
import { useParams } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";
import toast from "react-hot-toast";

function VolunteersList() {
  const { id } = useParams();
  const { accessToken, user } = useContext(AuthContext);
  const [volunteers, setVolunteers] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchVolunteers() {
      try {
        const res = await fetch(
          `http://localhost:8085/api/events/${id}/volunteers`,
          {
            method: "GET",
            headers: {
              "Content-type": "application/json",
              Authorization: `Bearer ${accessToken}`,
            },
          }
        );
        const data = await res.json();
        if (res.ok) {
          setVolunteers(data);
          console.log(data);
          
          console.log(volunteers);
        } else {
          toast.error("Failed to fetch volunteers");
        }
      } catch (error) {
        console.error(error);
        toast.error("Something went wrong");
      } finally {
        setLoading(false);
      }
    }
    fetchVolunteers();
  }, [id, accessToken]);

  return (
    <div className='min-h-screen bg-gray-100 p-6'>
      <h1 className='text-2xl font-bold text-center mb-6'>Volunteer List</h1>
      {loading ? (
        <div className='flex justify-center items-center h-40'>
          <div className='animate-spin rounded-full h-16 w-16 border-t-4 border-blue-500'></div>
        </div>
      ) : (
        <div className='overflow-x-auto'>
          <table className='min-w-full bg-white shadow-md rounded-lg'>
            <thead>
              <tr>
                <th className='py-3 px-5 bg-blue-100 text-left'>ID</th>
                <th className='py-3 px-5 bg-blue-100 text-left'>Username</th>
                <th className='py-3 px-5 bg-blue-100 text-left'>Name</th>
                <th className='py-3 px-5 bg-blue-100 text-left'>Email</th>
                <th className='py-3 px-5 bg-blue-100 text-left'>Phone</th>
                <th className='py-3 px-5 bg-blue-100 text-left'>Region</th>
              </tr>
            </thead>
            <tbody>
              {volunteers.length > 0 ? (
                volunteers.map((vol) => (
                  <tr key={vol.id} className='border-b hover:bg-gray-100'>
                    <td className='py-3 px-5'>{vol.id}</td>
                    <td className='py-3 px-5'>{vol.username}</td>
                    <td className='py-3 px-5'>{`${vol.firstName} ${vol.lastName}`}</td>
                    <td className='py-3 px-5'>{vol.email}</td>
                    <td className='py-3 px-5'>{vol.phoneNumber}</td>
                    <td className='py-3 px-5'>{vol.region}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan='6' className='text-center py-4'>
                    No volunteers found.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default VolunteersList;
