import React, { useContext } from "react";
import {
  Button,
  Dialog,
  DialogHeader,
  DialogBody,
  DialogFooter,
} from "@material-tailwind/react";
import toast from "react-hot-toast";
import { AuthContext } from "../context/AuthContext";
import { useNavigate } from "react-router";

export default function ReadMore({ event }) {
  const [open, setOpen] = React.useState(false);
  const { accessToken, user } = useContext(AuthContext);

  const handleOpen = () => setOpen(!open);

  const navigate = useNavigate();
  async function participateHandler() {
    // console.log('endpoint: ', `http://localhost:8085/volunteer/register/${event.idEvent}/${user.id}/`)
    try {
      const res = await fetch(
        `http://localhost:8085/api/events/${event.idEvent}/register/${user.id}`,
        {
          method: "POST",
          headers: {
            "Content-type": "application/json",
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );

      const data = await res.json();
      if (res.ok) {
        if (data) {
          toast.success("Thank you for your participation!");
          navigate("/my-events", { replace: true });
        }
      } else {
        console.error("Error in submiting participation");
        toast.error("Error in submiting participation");
      }
    } catch (error) {
      toast.error("Something went wrong");
      console.error(error);
    }
  }

  const handleSubmit = () => {
    handleOpen();
    participateHandler();
    // toast.success('Participated')
  };

  return (
    <>
      <Button className='bg-blue-800 cursor-pointer' onClick={handleOpen}>
        participate
      </Button>
      <Dialog open={open} handler={handleOpen}>
        <DialogHeader>Title: {event["eventName"]}</DialogHeader>
        <DialogBody>
          <b>Description:</b> {event["eventDescription"]}
        </DialogBody>
        <DialogFooter>
          <Button
            variant='text'
            color='red'
            onClick={handleOpen}
            className='mr-1'
          >
            <span>Cancel</span>
          </Button>
          <Button className='bg-blue-800 cursor-pointer' onClick={handleSubmit}>
            <span>Participate</span>
          </Button>
        </DialogFooter>
      </Dialog>
    </>
  );
}
