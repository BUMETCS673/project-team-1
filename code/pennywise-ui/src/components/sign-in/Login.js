import React, { useState, useEffect } from 'react'
import {Avatar, Button, CssBaseline, TextField, FormControlLabel, Link, Grid, Typography, Container, Box, createChainedFunction} from '@mui/material';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import { useNavigate } from 'react-router-dom';
import GoogleSignIn from './OauthRedirect';
import { postToken } from './PostToken';
import axios from 'axios';
import GoogleIcon from '@mui/icons-material/Google';
import googleLogo from '../img/google.png'; 


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
        <Box sx={{ mt: 2 }}>
          
          <Link href=" https://pennywise-oauth-aaa79e984b4d.herokuapp.com/">
          <Button
            style={{
              zIndex: 2,
              backgroundColor: 'transparent',
              color: 'black',
              padding: '8px 16px',
              border: '2px solid #A2B575',
              cursor: 'pointer',
              display: 'flex',
              alignItems: 'center',
              textTransform: 'none',
              mt: 2,
              ml: 3,
            }}
          >
             <img
              src={googleLogo}
              alt="Google Logo"
              style={{ width: '20px', height: '20px', marginRight: '8px' }}
            /> 
            Log in with Google
          </Button>
        </Link>
        </Box>
      </Box>
    </Container>
  </>
);
}
