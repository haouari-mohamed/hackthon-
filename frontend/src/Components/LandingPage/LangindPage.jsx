import React from "react";
import { Link } from "react-router-dom";
import { Button } from "@material-tailwind/react";

const LandingPage = () => {
  return (
    <div className='min-h-screen bg-gray-100 flex flex-col'>
      {/* Hero Section */}
      <header className='bg-blue-600 text-white'>
        <div className='container mx-auto px-6 py-16 text-center'>
          <h1 className='text-4xl font-bold mb-4'>
            Welcome to KarizmaVolunteers
          </h1>
          <p className='mb-8 text-lg'>
            Connecting passionate volunteers with organizations in need.
          </p>
          <div className='flex justify-center gap-4'>
            <Link to='/login'>
              <Button color='white' variant='outlined'>
                Login
              </Button>
            </Link>
            <Link to='/register'>
              <Button color='blue'>Register</Button>
            </Link>
          </div>
        </div>
      </header>

      {/* Main Content */}
      <main className='flex-grow'>
        {/* Volunteer Section */}
        <section className='container mx-auto px-6 py-16'>
          <div className='grid grid-cols-1 md:grid-cols-2 gap-8 items-center'>
            <div>
              <h2 className='text-3xl font-bold mb-4'>For Volunteers</h2>
              <p className='text-gray-700 mb-6'>
                Discover events that match your skills and passion. Join our
                community and make a real difference by getting involved.
              </p>
              <Link
                to='/events'
                className='text-blue-600 font-semibold hover:underline'
              >
                Explore Available Events →
              </Link>
            </div>
            <div>
              <img
                src='https://www.wastatepta.org/wp-content/uploads/2016/11/Senior-volunteer-helping-African-American-man-register-for-marathon-000065245281_Medium.jpg'
                alt='Volunteers working together'
                className='rounded-lg shadow-md w-full h-full'
              />
            </div>
          </div>
        </section>

        {/* Organization Section */}
        <section className='bg-white py-16'>
          <div className='container mx-auto px-6'>
            <div className='grid grid-cols-1 md:grid-cols-2 gap-8 items-center'>
              <div>
                <img
                  src='https://good-deeds-day.org/wp-content/uploads/2017/03/DSC6305.jpg'
                  alt='Organization team'
                  className='rounded-lg shadow-md'
                />
              </div>
              <div>
                <h2 className='text-3xl font-bold mb-4'>For Organizations</h2>
                <p className='text-gray-700 mb-6'>
                  Post events, manage volunteer applications, and build a
                  thriving community around your cause. Our platform simplifies
                  your recruitment process.
                </p>
                <Link
                  to='/newEvent'
                  className='text-blue-600 font-semibold hover:underline'
                >
                  Create a New Event →
                </Link>
              </div>
            </div>
          </div>
        </section>
      </main>

      {/* Footer */}
      <footer className='bg-blue-600 text-white py-8'>
        <div className='container mx-auto px-6 text-center'>
          <p>
            &copy; {new Date().getFullYear()} KarizmaVolunteers. All rights
            reserved.
          </p>
        </div>
      </footer>
    </div>
  );
};

export default LandingPage;
