import React, { useState } from "react";
import * as Components from './Components';

function Login() {
    const [signIn, toggle] = useState(true);
    const [formData, setFormData] = useState({
        username: '',
        firstName: '',
        lastName: '',
        email: '',
        phoneNumber: '',
        password: '',
        role: '',
        region: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const signUp = async () => {
        console.log("Registering with data:", formData);  // Log the form data when signing up
        try {
            const response = await fetch("/api/signup", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(formData)
            });
            const data = await response.json();
            console.log("Sign Up Success:", data);
            if (data.token) {
                localStorage.setItem("token", data.token);
            }
        } catch (error) {
            console.error("Sign Up Error:", error);
        }
    };

    const signInUser = async () => {
        console.log("Logging in with data:", { email: formData.email, password: formData.password });  // Log the login data
        try {
            const response = await fetch("/api/signin", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email: formData.email, password: formData.password })
            });
            const data = await response.json();
            console.log("Sign In Success:", data);
            if (data.token) {
                localStorage.setItem("token", data.token);
            }
        } catch (error) {
            console.error("Sign In Error:", error);
        }
    };

    const handleSubmit = (e, action) => {
        e.preventDefault();
        if (action === 'Sign Up') {
            signUp();
        } else {
            signInUser();
        }
    };

    const getProtectedData = async () => {
        const token = localStorage.getItem("accessToken");
        if (!token) {
            console.log("No token found");
            return;
        }

        try {
            const response = await fetch("/api/protected", {
                method: "GET",
                headers: { "Authorization": `Bearer ${token}` }
            });
            const data = await response.json();
            console.log("Protected Data:", data);
        } catch (error) {
            console.error("Error fetching protected data:", error);
        }
    };

    return (
        <Components.Container>
            <Components.SignUpContainer signinIn={signIn}>
                <Components.Form onSubmit={(e) => handleSubmit(e, 'Sign Up')}>
                    <Components.Title>Create Account</Components.Title>
                    <Components.Input type='text' placeholder='Username' name='username' onChange={handleChange} />
                    <div style={{ display: 'flex', gap: '10px', width: "100%" }}>
                        <Components.Input type='text' placeholder='First Name' name='firstName' onChange={handleChange} style={{ flex: 1 }} />
                        <Components.Input type='text' placeholder='Last Name' name='lastName' onChange={handleChange} style={{ flex: 1 }} />
                    </div>
                    <Components.Input type='email' placeholder='Email' name='email' onChange={handleChange} />
                    <Components.Input type='text' placeholder='Phone Number' name='phoneNumber' onChange={handleChange} />
                    <Components.Input type='password' placeholder='Password' name='password' onChange={handleChange} />
                    <Components.Input as="select" name="role" onChange={handleChange} value={formData.role}>
                        <option value="">Select Role</option>
                        <option value="organisation">Organisation</option>
                        <option value="benevolaat">Bénévolat</option>
                    </Components.Input>
                    <Components.Input type='text' placeholder='Region' name='region' onChange={handleChange} />
                    <Components.Button type='submit'>Sign Up</Components.Button>
                </Components.Form>
            </Components.SignUpContainer>

            <Components.SignInContainer signinIn={signIn}>
                <Components.Form onSubmit={(e) => handleSubmit(e, 'Sign In')}>
                    <Components.Title>Sign in</Components.Title>
                    <Components.Input type='email' placeholder='Email' name='email' onChange={handleChange} />
                    <Components.Input type='password' placeholder='Password' name='password' onChange={handleChange} />
                    <Components.Anchor href='#'>Forgot your password?</Components.Anchor>
                    <Components.Button type='submit'>Sign In</Components.Button>
                </Components.Form>
            </Components.SignInContainer>

            <Components.OverlayContainer signinIn={signIn}>
                <Components.Overlay signinIn={signIn}>
                    <Components.LeftOverlayPanel signinIn={signIn}>
                        <Components.Title>Welcome Back!</Components.Title>
                        <Components.Paragraph>
                            To keep connected with us please login with your personal info
                        </Components.Paragraph>
                        <Components.GhostButton onClick={() => toggle(true)}>Sign In</Components.GhostButton>
                    </Components.LeftOverlayPanel>

                    <Components.RightOverlayPanel signinIn={signIn}>
                        <Components.Title>Hello, Friend!</Components.Title>
                        <Components.Paragraph>
                            Enter your personal details and start your journey with us
                        </Components.Paragraph>
                        <Components.GhostButton onClick={() => toggle(false)}>Sign Up</Components.GhostButton>
                    </Components.RightOverlayPanel>
                </Components.Overlay>
            </Components.OverlayContainer>
        </Components.Container>
    );
}

export default Login;
