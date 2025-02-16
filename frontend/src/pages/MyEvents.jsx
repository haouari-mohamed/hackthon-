import React, { useContext, useEffect, useState } from "react";
import MyEventCard from "../Components/MyEventCard";
import { AuthContext } from "../context/AuthContext";

function MyEvents() {
  const { user, accessToken } = useContext(AuthContext);
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);

  async function fetchEvents() {
    try {
      let res;
      if (user.role === "VOLUNTEER") {
        res = await fetch(`http://localhost:8085/api/users/@me/events`, {
          method: "GET",
          headers: {
            "Content-type": "application/json",
            Authorization: `Bearer ${accessToken}`,
          },
        });
      } else {
        res = await fetch(`http://localhost:8085/api/events/organizer/${user.id}`, {
          method: "GET",
          headers: {
            "Content-type": "application/json",
            Authorization: `Bearer ${accessToken}`,
          },
        });
      }

      const data = await res.json();
      if (res.ok) {
        setEvents(data);
      } else {
        console.error("Error in fetching events");
        toast.error("Error in submiting participation");
      }
    } catch (error) {
      toast.error("Something went wrong");
      console.error(error);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    fetchEvents();
  }, []);
  return (
    <>
      <div className='h-screen  bg-gray-100 mb-2 p-6 overflow-y-auto '>
        <h1 className=' text-2xl font-bold text-center mb-6'>
          {user.role === "VOLUNTEER"
            ? "Last attended events"
            : "Organised events"}
        </h1>
        {loading === true ? (
          <div className='h-400px w-full animate-pulse border-2 rounded-md shadow-md'></div>
        ) : (
          events.map((event) => (
            <MyEventCard user={user} key={event["idEvent"]} myEvent={event} />
          ))
        )}
      </div>
    </>
  );
}

export default MyEvents;
