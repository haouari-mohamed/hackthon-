import React, { useContext, useEffect, useState } from "react";
import EventCard from "./EventCard";
import { AuthContext } from "../context/AuthContext";
import { regions } from "./Auth/Register";

function BrowseEvents() {
  const [allEvents, setAllEvents] = useState([]);
  const [filteredEvents, setFilteredEvents] = useState(allEvents);
  const [filterBy, setFilterBy] = useState("");
  const { accessToken, user } = useContext(AuthContext);
  const [loading, setLoading] = useState(true);

  async function fetchEvents() {
    try {
      const res = await fetch(`http://localhost:8085/api/events`, {
        method: "GET",
        headers: {
          "Content-type": "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
      });

      const data = await res.json();
      if (res.ok) {
        setAllEvents(data);
		
      } else {
        console.error("Error in fetching all events");
        toast.error("Error in fetching all events");
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


  useEffect(() => {
    const filtered = allEvents.filter(
      (event) => event.eventRegion === filterBy || !filterBy
    );
    setFilteredEvents(filtered);
  }, [filterBy, allEvents]);
  return (
    <section className='p-6 h-screen'>
      <h2 className='text-2xl font-bold text-center mb-6'>Available events</h2>
      <div className='flex gap-4 my-2 items-center'>
        <p className='text-lg '>filter by region</p>
        <select
          name='region'
          className='p-2 border rounded-lg mt-1 focus:ring focus:ring-blue-300'
          required
          onChange={(e) => {
            setFilterBy(e.target.value);
            console.log(filterBy);
          }}
        >
          <option value=''>Any</option>
          {regions.map((region, index) => (
            <option key={index} value={region}>
              {region}
            </option>
          ))}
        </select>
      </div>
      {filteredEvents.length ? (
        <div className='text-sm mb-1'>
          Found {filteredEvents.length} event(s)
        </div>
      ) : null}
      <div className='grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6'>
        {filteredEvents.length ? (
          filteredEvents.map((e, index) => <EventCard key={index} event={e} />)
        ) : (
          <div className='text-sm'>
            No event is currently available in {filterBy}
          </div>
        )}
      </div>
    </section>
  );
}

export default BrowseEvents;
