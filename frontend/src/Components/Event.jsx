import React from 'react'
import DialogDefault from './ReadMore'
import ReadMore from './ReadMore'

function Event({ event }) {
	return (
		<>
			<div className='p-4 border-b-[1px] hover:bg-gray-200 transition-colors duration-200 flex justify-between items-center'>
				<div>
					<h2 className=' mb-2'>{event['eventName']}</h2>
					<p className='text-gray-500 text-sm'>{event['eventDate']}</p>
				</div>
				{/* <button className='mr-4 text-sm bg-blue-800 text-white px-3 py-2 hover:scale-105 transition-all duration-200 cursor-pointer rounded-lg'>More Details</button> */}
				<ReadMore event={event} />

			</div>
		</>
	)
}

export default Event
