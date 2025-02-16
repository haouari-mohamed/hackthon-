import React from 'react'
import LoginForm from '../Components/LoginForm'
import { Link } from 'react-router'

function Login() {
	return (
		<div className="h-screen flex justify-center items-center">

			<div className='text-white bg-gray-900 rounded-2xl pt-12 px-14 pb-7 shadow-lg backdrop-blur-lg min-w-[350px] max-w-[450px] w-full '>
				<div className=''>
					<h1 className='text-5xl'>Login</h1>
					<LoginForm />

					<div>
						<p className='text-center font-extralight'>Don't have an account yet? <Link to='/register' className='cursor-pointer underline'>Register now</Link></p>
					</div>

				</div>
			</div>
		</div>
	)
}

export default Login
