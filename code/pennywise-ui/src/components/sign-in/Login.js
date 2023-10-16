import React, { useState, useEffect } from 'react'
import {Avatar, Button, CssBaseline, TextField, FormControlLabel, Link, Grid, Typography, Container, Box, createChainedFunction} from '@mui/material';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import { useNavigate } from 'react-router-dom';
import GoogleSignIn from './OauthRedirect';
import { postToken } from './postToken';
import axios from 'axios';

export default function LoginWithGoogle() {
  const navigate = useNavigate();
  const [isLogin, setIsLogin] = useState(false);
  
    //Callback function for Google Sign-In
  const onGoogleSignIn = async (response) => {
    const { credential } = response;
    const result = await postToken(credential);
    setIsLogin(result);
  };

  useEffect(() => {
    //If already logged in, navigate to the dashboard
    if (isLogin) {
      navigate('/dashboard'); 
    }
  }, [isLogin, navigate]);



 

  return (
    <>
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <Box sx={{ marginTop: 30, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <Avatar sx={{ m: 1, bgcolor: '#A2B575' }}>
            <LockOutlinedIcon />
          </Avatar>
        <Typography component="h1" variant="h6">
          Sign in
        </Typography>
        {/* <Box sx={{ mt: 3 }}>
          <TextField
            fullWidth
            variant="outlined"
            value={googleSignInUrl}
            InputProps={{
              readOnly: true,
            }}
          />
        </Box> */}
        <Box sx={{ mt: 2 }}>


        <Link href="http://localhost:8081">
              <Button variant="contained" color="primary">
                Visit localhost:8081
              </Button>
            </Link>

        

          <GoogleSignIn onGoogleSignIn={onGoogleSignIn} text="Sign in with Google"/>
          {/* <Button
            onClick={handleLoginWithGoogle}
            style={{ zIndex: 2, backgroundColor: '#A2B575', color: 'white', padding: '8px 16px', border: 'none', cursor: 'pointer' }}
          >
            Sign in
          </Button> */}
        </Box>
      </Box>
    </Container>
  </>
);
}
