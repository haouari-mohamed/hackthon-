import "./App.css";
import Breadcrumbs from "./Components/BreadCrumbs";
import Navbar from "./Layouts/Navbar";
import Sidebar from "./Layouts/Sidebar";
import { Outlet } from "react-router";

function App() {
  return (
    <>
      <Navbar />
      <div className='flex'>
        {/* <ProtectedRoute> */}
        <Sidebar />
        {/* </ProtectedRoute> */}
        <div className='flex-1 min-h-screen h-full'>
          <Breadcrumbs />
          <Outlet />
        </div>
      </div>
    </>
  );
}

export default App;
