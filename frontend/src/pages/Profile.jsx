import React, { useContext, useEffect, useState } from "react";
import { AuthContext } from "../context/AuthContext";
import { toast } from "react-hot-toast";
import { regions } from "../Components/Auth/Register";
import { tailChase } from "ldrs";

tailChase.register();

function Profile() {
  const { user, login, accessToken } = useContext(AuthContext);
  const [formData, setFormData] = useState({
    username: "",
    firstName: "",
    lastName: "",
    email: "",
    phoneNumber: "",
    password: "",
    role: "",
    region: "",
    hoursVolunteer: "",
    orgName: "",
    orgPhoneNumber: "",
    orgAddress: "",
  });
  const [loading, setLoading] = useState(false);

  console.log(user);

  // Pre-fill the form with user info on mount
  useEffect(() => {
    if (user) {
      setFormData({
        username: user.username || "",
        firstName: user.firstName || "",
        lastName: user.lastName || "",
        email: user.email || "",
        phoneNumber: user.phoneNumber || "",
        password: "", // never show the password; leave blank to keep unchanged
        role: user.role || "",
        region: user.region || "",
        hoursVolunteer: user.hoursVolunteer || "",
        orgName: user.orgName || "",
        orgPhoneNumber: user.orgPhoneNumber || "",
        orgAddress: user.orgAddress || "",
      });
    }
  }, [user]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      // Build payload only from the modifiable fields based on role
      let payload = {};
      if (formData.role === "VOLUNTEER") {
        // Only these fields can be updated for volunteers
        payload.firstName = formData.firstName;
        payload.lastName = formData.lastName;
        payload.phoneNumber = formData.phoneNumber;
        payload.region = formData.region;
      } else if (formData.role === "ORGANIZATION") {
        payload.firstName = formData.firstName;
        payload.lastName = formData.lastName;
        payload.phoneNumber = formData.phoneNumber;
        payload.region = formData.region;
        payload.orgName = formData.orgName;
        payload.orgPhoneNumber = formData.orgPhoneNumber;
        payload.orgAddress = formData.orgAddress;
      }

      const res = await fetch(`http://localhost:8085/api/users/${user.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
        body: JSON.stringify(payload),
      });

      if (res.ok) {
        const updatedUser = await res.json();
        toast.success("Profile updated successfully!");
        // Update the AuthContext with the new user data
        login(updatedUser, null);
      } else {
        const errorData = await res.json();
        toast.error(errorData.error || "Profile update failed");
      }
    } catch (error) {
      console.error("Profile update error:", error);
      toast.error("An error occurred. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  if (!user) {
    return (
      <div className='min-h-screen flex items-center justify-center'>
        <l-tail-chase size='40' speed='1.75' color='black'></l-tail-chase>
      </div>
    );
  }

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  // Determine which fields are modifiable based on role
  const isVolunteer = formData.role === "VOLUNTEER";
  const isOrganization = formData.role === "ORGANIZATION";

  return (
    <div className='min-h-screen bg-gray-100 py-8'>
      <div className='max-w-3xl mx-auto bg-white p-6 rounded shadow'>
        <h1 className='text-2xl font-bold mb-6 text-center'>Edit Profile</h1>
        <form onSubmit={handleSubmit} className='space-y-4'>
          {/* Non-modifiable fields */}
          <div className='grid grid-cols-2 gap-4'>
            <div>
              <label className='block text-sm font-medium text-gray-700'>
                Username
              </label>
              <input
                type='text'
                name='username'
                value={formData.username}
                disabled
                className='mt-1 block w-full border border-gray-300 rounded-md p-2 bg-gray-200 cursor-not-allowed'
              />
            </div>
            <div>
              <label className='block text-sm font-medium text-gray-700'>
                Email
              </label>
              <input
                type='email'
                name='email'
                value={formData.email}
                disabled
                className='mt-1 block w-full border border-gray-300 rounded-md p-2 bg-gray-200 cursor-not-allowed'
              />
            </div>
          </div>
          <div className='grid grid-cols-2 gap-4'>
            <div>
              <label className='block text-sm font-medium text-gray-700'>
                Role
              </label>
              <input
                type='text'
                name='role'
                value={formData.role}
                disabled
                className='mt-1 block w-full border border-gray-300 rounded-md p-2 bg-gray-200 cursor-not-allowed'
              />
            </div>
            <div>
              <label className='block text-sm font-medium text-gray-700'>
                Password
              </label>
              <input
                type='password'
                name='password'
                value={formData.password}
                disabled
                placeholder='********'
                className='mt-1 block w-full border border-gray-300 rounded-md p-2 bg-gray-200 cursor-not-allowed'
              />
            </div>
          </div>

          {/* Modifiable fields for volunteers */}
          {isVolunteer && (
            <>
              <div className='grid grid-cols-2 gap-4'>
                <div>
                  <label className='block text-sm font-medium text-gray-700'>
                    First Name
                  </label>
                  <input
                    type='text'
                    name='firstName'
                    value={formData.firstName}
                    onChange={handleChange}
                    className='mt-1 block w-full border border-gray-300 rounded-md p-2'
                  />
                </div>
                <div>
                  <label className='block text-sm font-medium text-gray-700'>
                    Last Name
                  </label>
                  <input
                    type='text'
                    name='lastName'
                    value={formData.lastName}
                    onChange={handleChange}
                    className='mt-1 block w-full border border-gray-300 rounded-md p-2'
                  />
                </div>
              </div>
              <div className='grid grid-cols-2 gap-4'>
                <div>
                  <label className='block text-sm font-medium text-gray-700'>
                    Phone Number
                  </label>
                  <input
                    type='text'
                    name='phoneNumber'
                    value={formData.phoneNumber}
                    onChange={handleChange}
                    className='mt-1 block w-full border border-gray-300 rounded-md p-2'
                  />
                </div>
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
                    className='mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2 focus:border-blue-500 focus:ring-blue-500'
                  >
                    <option value=''>Select your region</option>
                    {regions.map((r) => (
                      <option key={r} value={r}>
                        {r.replace(/_/g, " ")}
                      </option>
                    ))}
                  </select>
                </div>
              </div>
            </>
          )}

          {/* Modifiable fields for organizations */}
          {isOrganization && (
            <>
              <div>
                <label className='block text-sm font-medium text-gray-700'>
                  Organization Name
                </label>
                <input
                  type='text'
                  name='orgName'
                  value={formData.orgName}
                  onChange={handleChange}
                  className='mt-1 block w-full border border-gray-300 rounded-md p-2'
                />
              </div>
              <div>
                <label className='block text-sm font-medium text-gray-700'>
                  Organization Phone Number
                </label>
                <input
                  type='text'
                  name='orgPhoneNumber'
                  value={formData.orgPhoneNumber}
                  onChange={handleChange}
                  className='mt-1 block w-full border border-gray-300 rounded-md p-2'
                />
              </div>
              <div>
                <label className='block text-sm font-medium text-gray-700'>
                  Organization Address
                </label>
                <input
                  type='text'
                  name='orgAddress'
                  value={formData.orgAddress}
                  onChange={handleChange}
                  className='mt-1 block w-full border border-gray-300 rounded-md p-2'
                />
              </div>
              {/* For organizations, show volunteer fields as disabled */}
              <div className='grid grid-cols-2 gap-4'>
                <div>
                  <label className='block text-sm font-medium text-gray-700'>
                    First Name
                  </label>
                  <input
                    type='text'
                    name='firstName'
                    value={formData.firstName}
                    onChange={handleChange}
                    className='mt-1 block w-full border border-gray-300 rounded-md p-2'
                  />
                </div>
                <div>
                  <label className='block text-sm font-medium text-gray-700'>
                    Last Name
                  </label>
                  <input
                    type='text'
                    name='lastName'
                    value={formData.lastName}
                    onChange={handleChange}
                    className='mt-1 block w-full border border-gray-300 rounded-md p-2'
                  />
                </div>
              </div>
              <div className='grid grid-cols-2 gap-4'>
                <div>
                  <label className='block text-sm font-medium text-gray-700'>
                    Phone Number
                  </label>
                  <input
                    type='text'
                    name='phoneNumber'
                    value={formData.phoneNumber}
                    onChange={handleChange}
                    className='mt-1 block w-full border border-gray-300 rounded-md p-2'
                  />
                </div>
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
                    className='mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2 focus:border-blue-500 focus:ring-blue-500'
                  >
                    <option value=''>Select your region</option>
                    {regions.map((r) => (
                      <option key={r} value={r}>
                        {r.replace(/_/g, " ")}
                      </option>
                    ))}
                  </select>
                </div>
              </div>
            </>
          )}

          <button
            type='submit'
            disabled={loading}
            className='w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700'
          >
            {loading ? "Updating..." : "Update Profile"}
          </button>
        </form>
      </div>
    </div>
  );
}

export default Profile;
