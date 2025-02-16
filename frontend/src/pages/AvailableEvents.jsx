import React from 'react'
import BrowseEvents from '../Components/BrowseEvents'
import { Toaster } from 'react-hot-toast'

function AvailableEvents() {
	return (
		<div className='min-h-screen h-full bg-gray-100 p-4 flex-col'>
			{/* <MyEvents /> */}
			<BrowseEvents />

		</div>
	)
}

export default AvailableEvents
