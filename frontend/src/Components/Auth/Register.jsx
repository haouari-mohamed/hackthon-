import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  Card,
  CardBody,
  Typography,
  Input,
  Button,
} from "@material-tailwind/react";
import { toast } from "react-hot-toast";

export const regions = [
  "TANGER_TETOUAN_AL_HOCEIMA",
  "ORIENTAL",
  "FES_MEKNES",
  "RABAT_SALE_KENITRA",
  "BENI_MELLAL_KHENIFRA",
  "CASABLANCA_SETTAT",
  "MARRAKECH_SAFI",
  "DRAA_TAFILALET",
  "SOUSS_MASSA",
  "GUELMIM_OUED_NOUN",
  "LAAYOUNE_SAKIA_EL_HAMRA",
  "DAKHLA_OUED_ED_DAHAB",
];

const Register = () => {
  const navigate = useNavigate();

  // Role state determines which fields to show and which endpoint to call.
  const [role, setRole] = useState("VOLUNTEER"); // "VOLUNTEER" or "ORGANIZATION"

  // Form fields state
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    email: "",
    firstName: "",
    lastName: "",
    phoneNumber: "",
    region: "",
    // Organization-specific fields
    orgName: "",
    orgPhoneNumber: "",
    orgAddress: "",
    // Volunteer specific, default hoursVolunteer = 0
    hoursVolunteer: 0,
  });

  const [errors, setErrors] = useState({});

  // Handle change for controlled inputs
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  // Validate form fields based on your validation requirements.
  const validate = () => {
    let tempErrors = {};
    if (!formData.username.trim()) {
      tempErrors.username = "Username is required";
    }
    if (!formData.firstName.trim()) {
      tempErrors.firstName = "First name is required";
    }
    if (!formData.lastName.trim()) {
      tempErrors.lastName = "Last name is required";
    }
    if (!formData.email.trim()) {
      tempErrors.email = "Email is required";
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      tempErrors.email = "Invalid email address";
    }
    if (!formData.phoneNumber.trim()) {
      tempErrors.phoneNumber = "Phone number is required";
    } else if (!/^[0-9]{10}$/.test(formData.phoneNumber)) {
      tempErrors.phoneNumber = "Phone number must contain exactly 10 digits";
    }
    if (!formData.password.trim()) {
      tempErrors.password = "Password is required";
    }
    if (!formData.region.trim()) {
      tempErrors.region = "Region is required";
    }
    // Organization validations
    if (role === "ORGANIZATION") {
      if (!formData.orgName.trim()) {
        tempErrors.orgName = "Organization name is required";
      }
      if (!formData.orgPhoneNumber.trim()) {
        tempErrors.orgPhoneNumber = "Organization phone number is required";
      } else if (!/^[0-9]{10}$/.test(formData.orgPhoneNumber)) {
        tempErrors.orgPhoneNumber =
          "Phone number must contain exactly 10 digits";
      }
      if (!formData.orgAddress.trim()) {
        tempErrors.orgAddress = "Organization address is required";
      }
    }
    setErrors(tempErrors);
    return Object.keys(tempErrors).length === 0;
  };

  // Submit handler posts the data to the appropriate endpoint.
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validate()) {
      toast.error("Please fix the errors in the form");
      return;
    }

    // Select the endpoint based on role.
    const endpoint =
      role === "ORGANIZATION"
        ? "http://localhost:8085/api/auth/register/organization"
        : "http://localhost:8085/api/auth/register/volunteer";

    // Build the payload with common fields.
    let payload = {
      username: formData.username,
      password: formData.password,
      email: formData.email,
      firstName: formData.firstName,
      lastName: formData.lastName,
      phoneNumber: formData.phoneNumber,
      role: role,
      region: formData.region,
    };

    // Append extra fields based on role.
    if (role === "ORGANIZATION") {
      payload = {
        ...payload,
        orgName: formData.orgName,
        orgPhoneNumber: formData.orgPhoneNumber,
        orgAddress: formData.orgAddress,
      };
    } else {
      payload = {
        ...payload,
        hoursVolunteer: formData.hoursVolunteer,
      };
    }

    try {
      const res = await fetch(endpoint, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      });
      
      const contentType = res.headers.get("Content-Type") || "";
      let data;
      if (contentType.includes("application/json")) {
        data = await res.json();
      } else {
        data = { message: await res.text() };
      }

      if (res.ok) {
        toast.success("Registration successful!");
        navigate("/login");
      } else {
        toast.error(data.error || "Registration failed");
      }
    } catch (error) {
      console.error("Registration error:", error);
      toast.error("An error occurred. Please try again.");
    }
  };

  return (
    <div className='min-h-screen flex items-center justify-center bg-gray-100 py-8'>
      <Card className='w-full max-w-md'>
        <CardBody>
          <Typography variant='h4' className='mb-6 text-center'>
            Register
          </Typography>
          <div className='mb-4 flex justify-center gap-4'>
            <Button
              variant={role === "VOLUNTEER" ? "filled" : "outlined"}
              onClick={() => setRole("VOLUNTEER")}
            >
              I'm a Volunteer
            </Button>
            <Button
              variant={role === "ORGANIZATION" ? "filled" : "outlined"}
              onClick={() => setRole("ORGANIZATION")}
            >
              I'm an Organization
            </Button>
          </div>
          <form onSubmit={handleSubmit} className='space-y-4'>
            <Input
              label='Username'
              name='username'
              value={formData.username}
              onChange={handleInputChange}
              error={!!errors.username}
            />
            {errors.username && (
              <p className='text-red-500 text-xs mt-1'>{errors.username}</p>
            )}

            <Input
              label='First Name'
              name='firstName'
              value={formData.firstName}
              onChange={handleInputChange}
              error={!!errors.firstName}
            />
            {errors.firstName && (
              <p className='text-red-500 text-xs mt-1'>{errors.firstName}</p>
            )}

            <Input
              label='Last Name'
              name='lastName'
              value={formData.lastName}
              onChange={handleInputChange}
              error={!!errors.lastName}
            />
            {errors.lastName && (
              <p className='text-red-500 text-xs mt-1'>{errors.lastName}</p>
            )}

            <Input
              label='Email'
              name='email'
              type='email'
              value={formData.email}
              onChange={handleInputChange}
              error={!!errors.email}
            />
            {errors.email && (
              <p className='text-red-500 text-xs mt-1'>{errors.email}</p>
            )}

            <Input
              label='Phone Number'
              name='phoneNumber'
              value={formData.phoneNumber}
              onChange={handleInputChange}
              error={!!errors.phoneNumber}
            />
            {errors.phoneNumber && (
              <p className='text-red-500 text-xs mt-1'>{errors.phoneNumber}</p>
            )}

            <Input
              label='Password'
              name='password'
              type='password'
              value={formData.password}
              onChange={handleInputChange}
              error={!!errors.password}
            />
            {errors.password && (
              <p className='text-red-500 text-xs mt-1'>{errors.password}</p>
            )}

            <div>
              <label
                htmlFor='region'
                className='block text-sm font-medium text-gray-700'
              >
                Region
              </label>
              <select
                id='region'
                name='region'
                value={formData.region}
                onChange={handleInputChange}
                className='mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500'
              >
                <option value=''>Select your region</option>
                {regions.map((r) => (
                  <option key={r} value={r}>
                    {r.replace(/_/g, " ")}
                  </option>
                ))}
              </select>
              {errors.region && (
                <p className='text-red-500 text-xs mt-1'>{errors.region}</p>
              )}
            </div>

            {/* Organization-specific inputs */}
            {role === "ORGANIZATION" && (
              <>
                <Input
                  label='Organization Name'
                  name='orgName'
                  value={formData.orgName}
                  onChange={handleInputChange}
                  error={!!errors.orgName}
                />
                {errors.orgName && (
                  <p className='text-red-500 text-xs mt-1'>{errors.orgName}</p>
                )}

                <Input
                  label='Organization Phone Number'
                  name='orgPhoneNumber'
                  value={formData.orgPhoneNumber}
                  onChange={handleInputChange}
                  error={!!errors.orgPhoneNumber}
                />
                {errors.orgPhoneNumber && (
                  <p className='text-red-500 text-xs mt-1'>
                    {errors.orgPhoneNumber}
                  </p>
                )}

                <Input
                  label='Organization Address'
                  name='orgAddress'
                  value={formData.orgAddress}
                  onChange={handleInputChange}
                  error={!!errors.orgAddress}
                />
                {errors.orgAddress && (
                  <p className='text-red-500 text-xs mt-1'>
                    {errors.orgAddress}
                  </p>
                )}
              </>
            )}
            <p>
              Already have an account?{" "}
              <Link to='/login' className='text-blue-600 hover:underline'>
                Login here
              </Link>
            </p>
            <Button type='submit' fullWidth>
              Register
            </Button>
          </form>
          <div className='mt-4 text-center'>
            <p>
              Already have an account?{" "}
              <Link to='/login' className='text-blue-600 hover:underline'>
                Login here
              </Link>
            </p>
          </div>
        </CardBody>
      </Card>
    </div>
  );
};

export default Register;
