import React, { useState } from 'react';
import {
  Avatar,
  Button,
  CssBaseline,
  TextField,
  Grid,
  Typography,
  Container,
  Box,
  Snackbar,
  Alert,
} from '@mui/material';
import axios from 'axios';

export default function Signin() {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    firstName: '',
    lastName: '',
    password: '',
  });
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarSeverity, setSnackbarSeverity] = useState('success');
  const [snackbarMessage, setSnackbarMessage] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();
    const URL = process.env.REACT_APP_API_BASE_URL;

    try {
      const response = await axios.post(`${URL}/createUser`, formData);

      if (response.status === 201) {
        console.log('User created successfully');
        setSnackbarSeverity('success');
        setSnackbarMessage('User created successfully');
        setSnackbarOpen(true);
      } else {
        console.error('Failed: create user');
        setSnackbarSeverity('error');
        setSnackbarMessage('Failed to create user');
        setSnackbarOpen(true);
      }
    } catch (error) {
      console.error('Error occurred:', error);
      setSnackbarSeverity('error');
      setSnackbarMessage('Error occurred. Please try again.');
      setSnackbarOpen(true);
    }
  };

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setFormData({ ...formData, [name]: value });
  };

  return (
    <>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box sx={{ marginTop: 15, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
          <Typography variant="h6" gutterBottom>
            Sign up
          </Typography>
          <form onSubmit={handleSubmit}>
            <Grid container spacing={3}>
              <Grid item xs={12}>
                <TextField
                  margin="normal"
                  required
                  fullWidth
                  name="username"
                  label="Username"
                  autoComplete="off"
                  variant="standard"
                  onChange={handleInputChange}
                />
                <TextField
                  margin="normal"
                  required
                  fullWidth
                  name="email"
                  label="Email"
                  autoComplete="off"
                  variant="standard"
                  onChange={handleInputChange}
                />
                <TextField
                  margin="normal"
                  required
                  fullWidth
                  name="firstName"
                  label="First Name"
                  autoComplete="off"
                  variant="standard"
                  onChange={handleInputChange}
                />
                <TextField
                  margin="normal"
                  required
                  fullWidth
                  name="lastName"
                  label="Last Name"
                  autoComplete="off"
                  variant="standard"
                  onChange={handleInputChange}
                />
                <TextField
                  margin="normal"
                  required
                  fullWidth
                  name="password"
                  label="Password"
                  autoComplete="off"
                  variant="standard"
                  type="password"
                  onChange={handleInputChange}
                />
              </Grid>
            </Grid>
            <Button
              type="submit"
              variant="contained"
              style={{
                marginTop: '16px',
                backgroundColor: '#A2B575',
                color: 'white',
                padding: '8px 16px',
                border: 'none',
                cursor: 'pointer',
              }}
            >
              Sign Up
            </Button>
          </form>
        </Box>
        <Snackbar
          open={snackbarOpen}
          autoHideDuration={6000}
          onClose={() => setSnackbarOpen(false)}
          anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
        >
          <Alert severity={snackbarSeverity}>{snackbarMessage}</Alert>
        </Snackbar>
      </Container>
    </>
  );
}
