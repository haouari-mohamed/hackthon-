import {
  Card,
  CardHeader,
  CardBody,
  CardFooter,
  Typography,
  Avatar,
  Tooltip,
} from "@material-tailwind/react";
import ReadMore from "./ReadMore";
import { LuMapPin } from "react-icons/lu";

export default function EventCard({ event }) {
  const defaultImage =
    "https://coffective.com/wp-content/uploads/2018/06/default-featured-image.png.jpg";

  return (
    <Card className='max-w-[24rem] overflow-hidden flex flex-col justify-between gap-2'>
      <div>
        <CardHeader
          floated={false}
          shadow={false}
          color='transparent'
          className='m-0 rounded-none h-[204px]'
        >
          <img
            className='hover:scale-105 h-full w-full object-cover transition-all duration-200'
            src={event.image || defaultImage}
            alt='event image'
          />
        </CardHeader>
        <CardBody className='flex flex-col justify-between gap-4'>
          <Typography variant='h4' color='blue-gray'>
            {event.eventName}
          </Typography>
          <Typography
            variant='lead'
            color='gray'
            className='mt-3 font-light text-sm'
          >
            {event.eventDescription}
          </Typography>
          <Typography
            color='gray'
            className='mt-3 font-light text-sm flex items-center gap-2'
          >
            <LuMapPin />
            {event.eventRegion}
          </Typography>
        </CardBody>
      </div>
      <CardFooter className='flex items-center justify-between'>
        <Typography className='font-light text-gray-500 text-sm'>
          {event.eventDate}
        </Typography>
        <div className='flex items-center -space-x-3'>
          <ReadMore event={event} />
        </div>
      </CardFooter>
    </Card>
  );
}
