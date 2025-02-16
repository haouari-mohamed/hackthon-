import {
  Card,
  CardHeader,
  CardBody,
  Typography,
} from "@material-tailwind/react";
import { LuMapPin } from "react-icons/lu";
import { Link } from "react-router-dom";

export default function MyEventCard({ myEvent, user }) {
  const defaultImage =
    "https://coffective.com/wp-content/uploads/2018/06/default-featured-image.png.jpg";

  return (
    <Card className='w-full h-72 flex-row my-4'>
      <CardHeader
        shadow={false}
        floated={false}
        className='m-0 w-2/5 shrink-0 rounded-r-none'
      >
        <img
          src={myEvent.image || defaultImage}
          alt='card-image'
          className='h-full w-full object-cover hover:scale-105 transition-all duration-200'
        />
      </CardHeader>
      <CardBody className='relative w-full flex flex-col gap-3 justify-between !p-4'>
        <Typography variant='h4' color='blue-gray' className='mb-2'>
          {myEvent.eventName}
        </Typography>
        <div className='mb-4 font-normal max-h-42 overflow-y-auto'>
          <Typography color='gray'>{myEvent.eventDescription}</Typography>
        </div>
        <div className='flex items-center justify-between px-2 w-full'>
          <Typography
            color='gray'
            className='font-normal flex items-center gap-2'
          >
            <LuMapPin /> {myEvent.eventRegion}
          </Typography>
          {user.role === "ORGANIZATION" && (
            <Typography className='flex justify-between'>
              Number of volunteers: {myEvent.volunteers?.length || "0"}
            </Typography>
          )}
        </div>
        {myEvent.volunteers?.length > 0 && (
          <div className='absolute top-4 right-8'>
            <Link
              className='font-bold text-3xl'
              to={`/my-events/${myEvent.idEvent}/volunteersList`}
            >
              &gt;
            </Link>{" "}
          </div>
        )}
      </CardBody>
    </Card>
  );
}
