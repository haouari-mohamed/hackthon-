import { createBrowserRouter } from "react-router";
import Profile from "./pages/Profile";
import AvailableEvents from "./pages/AvailableEvents";
import NotFound from "./pages/NotFound";
import App from "./App";
import MyEvents from "./pages/MyEvents";
import NewEvent from "./pages/NewEvent";
import ProtectedRoute from "./Components/ProtectedRoute";
import PublicRoute from "./Components/PublicRoute";
import Login from "./Components/Auth/Login";
import Register from "./Components/Auth/Register";
import LandingPage from "./Components/LandingPage/LangindPage";
import VolunteersList from "./pages/VolunteersList";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      {
        path: "",
        element: <LandingPage />,
      },
      {
        path: "/profile",
        element: <Profile />,
      },
      {
        path: "/events",
        element: (
          <ProtectedRoute allowedRoles={["VOLUNTEER"]}>
            <AvailableEvents />
          </ProtectedRoute>
        ),
      },
      {
        path: "/my-events",
        element: (
          <ProtectedRoute>
            <MyEvents />
          </ProtectedRoute>
        ),
      },
      {
        path: "/my-events/:id/volunteersList",
        element: (
          <ProtectedRoute allowedRoles={["ORGANIZATION"]}>
            <VolunteersList />
          </ProtectedRoute>
        ),
      },
      {
        path: "/newEvent",
        element: (
          <ProtectedRoute allowedRoles={["ORGANIZATION"]}>
            <NewEvent />
          </ProtectedRoute>
        ),
      },
    ],
  },
  {
    path: "/login",
    element: (
      <PublicRoute>
        <Login />
      </PublicRoute>
    ),
  },
  {
    path: "/register",
    element: (
      <PublicRoute>
        <Register />
      </PublicRoute>
    ),
  },
  {
    path: "*",
    element: <NotFound />,
  },
]);
