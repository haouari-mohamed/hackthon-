import { useState, useEffect, useContext } from 'react'
import toast, { Toaster } from 'react-hot-toast'
import { useNavigate } from 'react-router-dom'
import { AuthContext } from '../context/AuthContext'
import CheckBox from './CheckBox'


function LoginForm() {

	const [email, setEmail] = useState('')
	const [password, setPassword] = useState('')
	const [isPwdShown, setIsPwdShown] = useState(false)
	const navigate = useNavigate()


	const { login } = useContext(AuthContext)

	function togglePwdVisibility() {

		setIsPwdShown(prev => !prev)
	}

	async function signIn(e) {
		e.preventDefault()
		const credentials = {
			email,
			password,
		}
		if (!credentials.email || !credentials.password) {
			toast.error('Please fill all fields')
			return;

		}

		try {
			console.log(credentials);


			const res = await fetch('http://localhost:8085/api/auth/login/', {
				method: 'POST',
				headers: {
					'Content-type': 'application/json'
				},

				body: JSON.stringify(credentials)
			})


			const data = await res.json();
			if (res.ok) {
				// console.log('data ', data.user)
				if (data.user) {
					login(data.user, () => {
						navigate('/', { replace: true });
						toast.success('Logged in successfully!');
					})
				}
			}
			else {
				toast.error('Invalid credentials')
			}
		} catch (error) {
			toast.error('Something went wrong')
		}
	}

	return (
		<>
			<form onSubmit={signIn}>
				<div className='my-4'>
					<div className='flex flex-col '>
						<label htmlFor="email" className='my-2 text-sm '>Email</label>
						<input type="email" className='bg-white text-black pl-4 py-3 w-full rounded-md text-sm ' name='email' placeholder='username@gmail.com' required onChange={(e) => setEmail(e.target.value)} />
					</div>
					<div className='flex flex-col mt-2'>
						<label htmlFor="password" className='my-2 text-sm '>Password</label>
						<input type={isPwdShown ? 'text' : 'password'} className='bg-white text-black pl-4 py-3 w-full rounded-md text-sm ' name='password' placeholder='Password' required onChange={(e) => setPassword(e.target.value)} />
						<CheckBox togglePwdVisibility={togglePwdVisibility} />
					</div>
					{/* <div className='text-right mt-2'>
						<a href="#forget_password" className=' text-sm underline'>Forgot Password?</a>
					</div> */}
				</div>
				<div className='mt-8'>
					<button type='submit' className='bg-white cursor-pointer text-black my-4 px-8 w-full py-2 rounded'>Sign in</button>
					{/* <Toaster toastOptions={toastStyle} /> */}
				</div>
			</form>

		</>
	)
}

export default LoginForm
