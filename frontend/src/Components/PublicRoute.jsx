import { useContext } from "react";
import { Navigate } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";

const PublicRoute = ({ children }) => {
  const { isLoggedIn, loading } = useContext(AuthContext);

  if (loading) {
    return <div>Loading...</div>;
  }

  return isLoggedIn ? <Navigate to='/profile' replace /> : children;
};

export default PublicRoute;
