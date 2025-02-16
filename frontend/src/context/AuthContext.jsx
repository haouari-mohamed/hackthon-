import { createContext, useEffect, useState } from "react";
import { tailChase } from "ldrs";

tailChase.register();

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [accessToken, setAccessToken] = useState(
    localStorage.getItem("accessToken") || null
  );
  const [isLoggedIn, setIsLoggedin] = useState(true);
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true); // Add a loading state

  const login = (userData, token, callback) => {
    setIsLoggedin(true);
    setUser(userData);
    if (token) {
      console.log(token);

      setAccessToken(token);
      localStorage.setItem("accessToken", token);
    }
    if (callback) callback();
  };

  const logout = async (callback) => {
    try {
      if (localStorage.getItem("accessToken")) {
        localStorage.removeItem("accessToken"); // Remove the stored token
        window.location.href = "/login";
      }

      setIsLoggedin(false);
      setUser(null);
      if (callback) callback;
    } catch (error) {
      console.error("Logout failed:", error);
    }
  };

  const checkAuth = async () => {
    setLoading(true);
    try {
      const res = await fetch("http://localhost:8085/api/users/@me", {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });

      if (res.ok) {
        const data = await res.json();
        if (data.user) {
          console.log(data);
          login(data.user, accessToken);
        }
      } else {
        console.log("Invalid user");
        logout();
      }
    } catch (error) {
      logout();
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    checkAuth();
  }, []);

  if (loading) {
    return (
      <div className='h-screen flex items-center justify-center'>
        <l-tail-chase size='40' speed='1.75' color='black'></l-tail-chase>
      </div>
    );
  }

  return (
    <AuthContext.Provider
      value={{ isLoggedIn, user, login, logout, accessToken, loading }}
    >
      {children}
    </AuthContext.Provider>
  );
};
