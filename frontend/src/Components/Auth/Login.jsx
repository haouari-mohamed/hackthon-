import { useState, useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "react-hot-toast";
import {
  Card,
  CardBody,
  Typography,
  Input,
  Button,
} from "@material-tailwind/react";
import { AuthContext } from "../../context/AuthContext";

const Login = () => {
  const navigate = useNavigate();
  const { login } = useContext(AuthContext);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(false);

  const validate = () => {
    const tempErrors = {};
    if (!username) {
      tempErrors.username = "Username cannot be null";
    } else if (username.length < 4 || username.length > 32) {
      tempErrors.username = "Username must be between 6 and 32 characters";
    }
    if (!password) {
      tempErrors.password = "Password cannot be null";
    } else if (password.length < 8 || password.length > 32) {
      tempErrors.password = "Password must be between 8 and 32 characters";
    }
    setErrors(tempErrors);
    return Object.keys(tempErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validate()) {
      toast.error("Please fix the errors in the form");
      return;
    }
    setLoading(true);
    try {
      const res = await fetch("http://localhost:8085/api/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      });
      const data = await res.json();
      if (res.ok) {
        localStorage.setItem("accessToken", data.accessToken);
        login(data.user, data.accessToken);
        toast.success("Logged in successfully!");
        navigate("/profile");
      } else {
        toast.error(data.message || "Login failed");
      }
    } catch (error) {
      console.error("Login error", error);
      toast.error("An error occurred. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className='min-h-screen flex flex-col gap-10 items-center justify-center bg-gray-100'>
      <p className=' text-4xl font-medium'>
        Welcome to <span className=' text-blue-400'>Karizma</span>Volunteers
      </p>
      <Card className='w-[30%]'>
        <CardBody>
          <Typography variant='h4' className='mb-6 text-center'>
            Login
          </Typography>
          <form onSubmit={handleSubmit} className='flex flex-col gap-4'>
            <Input
              label='Username'
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
              error={!!errors.username}
            />
            {errors.username && (
              <p className='text-red-500 text-xs'>{errors.username}</p>
            )}
            <Input
              type='password'
              label='Password'
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              error={!!errors.password}
            />
            <p>
              Don't have an account?{" "}
              <Link to='/register' className='text-blue-600 hover:underline'>
                Register here
              </Link>
            </p>
            <Button type='submit' disabled={loading}>
              {loading ? "Logging in..." : "Login"}
            </Button>
          </form>
        </CardBody>
      </Card>
    </div>
  );
};

export default Login;
