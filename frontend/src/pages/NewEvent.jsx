import React, { useState, useContext } from "react";
import toast from "react-hot-toast";
import { regions } from "../Components/Auth/Register";
import { AuthContext } from "../context/AuthContext";

function NewEvent() {
  const { accessToken } = useContext(AuthContext);
  const [eventName, setEventName] = useState("");
  const [eventDescription, setEventDescription] = useState("");
  const [eventDate, setEventDate] = useState("");
  const [eventRegion, setEventRegion] = useState("");
  const [maxParticipants, setMaxParticipants] = useState("");
  const [duration, setDuration] = useState("");
  const [image, setImage] = useState("");
  const [loading, setLoading] = useState(false);

  async function handleSubmit(e) {
    e.preventDefault();

    // Validate event date: it must be today or later.
    const selectedDate = new Date(eventDate);
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    if (selectedDate < today) {
      toast.error("Event date cannot be in the past");
      return;
    }
    // Validate maxParticipants and duration are positive numbers
    if (!maxParticipants || Number(maxParticipants) <= 0) {
      toast.error("Max participants must be a positive number");
      return;
    }
    if (!duration || Number(duration) <= 0) {
      toast.error("Duration must be a positive number");
      return;
    }

    const payload = {
      eventName,
      eventDescription,
      eventDate,
      eventRegion,
      maxParticipants: Number(maxParticipants),
      duration: Number(duration),
      image,
    };

    setLoading(true);
    try {
      const res = await fetch("http://localhost:8085/api/events", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
        body: JSON.stringify(payload),
      });

      const data = await res.json();
      if (res.ok) {
        toast.success("Event created successfully!");
        // Optionally, reset the form fields:
        setEventName("");
        setEventDescription("");
        setEventDate("");
        setEventRegion("");
        setMaxParticipants("");
        setDuration("");
		setImage("");
      } else {
        toast.error(data.error || "Event creation failed");
      }
    } catch (error) {
      toast.error("Something went wrong");
      console.error(error);
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className='min-h-screen bg-gray-200 py-8'>
      <h1 className='text-2xl font-bold text-center my-6'>
        Create New Event
      </h1>
      <div className='w-1/2 mx-auto bg-white p-6 rounded-lg shadow'>
        <form onSubmit={handleSubmit} className='space-y-4'>
          {/* Event Name */}
          <div>
            <label className='block text-gray-700 font-medium'>
              Event Name
            </label>
            <input
              type='text'
              name='eventName'
              placeholder='Enter event name'
              required
              value={eventName}
              onChange={(e) => setEventName(e.target.value)}
              className='w-full p-2 border rounded-lg mt-1 focus:ring focus:ring-blue-300'
            />
          </div>

          {/* Event Description */}
          <div>
            <label className='block text-gray-700 font-medium'>
              Event Description
            </label>
            <textarea
              name='eventDescription'
              placeholder='Enter event description'
              rows='3'
              required
              value={eventDescription}
              onChange={(e) => setEventDescription(e.target.value)}
              className='w-full p-2 border rounded-lg mt-1 focus:ring focus:ring-blue-300'
            ></textarea>
          </div>

          {/* Event Date */}
          <div>
            <label className='block text-gray-700 font-medium'>
              Event Date
            </label>
            <input
              type='date'
              name='eventDate'
              required
              value={eventDate}
              onChange={(e) => setEventDate(e.target.value)}
              className='w-full p-2 border rounded-lg mt-1 focus:ring focus:ring-blue-300'
            />
          </div>

          {/* Event Region */}
          <div>
            <label className='block text-gray-700 font-medium'>
              Event Region
            </label>
            <select
              name='eventRegion'
              required
              value={eventRegion}
              onChange={(e) => setEventRegion(e.target.value)}
              className='w-full p-2 border rounded-lg mt-1 focus:ring focus:ring-blue-300'
            >
              <option value=''>Select your region</option>
              {regions.map((r, index) => (
                <option key={index} value={r}>
                  {r.replace(/_/g, " ")}
                </option>
              ))}
            </select>
          </div>

          {/* Max Participants */}
          <div>
            <label className='block text-gray-700 font-medium'>
              Max Participants
            </label>
            <input
              type='number'
              name='maxParticipants'
              placeholder='Enter maximum number of participants'
              required
              value={maxParticipants}
              onChange={(e) => setMaxParticipants(e.target.value)}
              className='w-full p-2 border rounded-lg mt-1 focus:ring focus:ring-blue-300'
            />
          </div>

          {/* Duration */}
          <div>
            <label className='block text-gray-700 font-medium'>
              Duration (in hours)
            </label>
            <input
              type='number'
              name='duration'
              placeholder='Enter event duration in hours'
              required
              value={duration}
              onChange={(e) => setDuration(e.target.value)}
              className='w-full p-2 border rounded-lg mt-1 focus:ring focus:ring-blue-300'
            />
          </div>

          <div>
            <label className='block text-gray-700 font-medium'>Image Url</label>
            <input
              type='text'
              name='image'
              placeholder='Enter event image url'
              value={image}
              onChange={(e) => setImage(e.target.value)}
              className='w-full p-2 border rounded-lg mt-1 focus:ring focus:ring-blue-300'
            />
          </div>

          <button
            type='submit'
            disabled={loading}
            className='w-full bg-blue-800 text-white p-2 rounded-lg hover:bg-blue-700 hover:scale-105 transition-all duration-200 cursor-pointer'
          >
            {loading ? "Submitting..." : "Submit Event"}
          </button>
        </form>
      </div>
    </div>
  );
}

export default NewEvent;
