import React, { useEffect, useState } from 'react';
import {
  Avatar,
  Button,
  CssBaseline,
  TextField,
  FormControlLabel,
  Link,
  Grid,
  Typography,
  Container,
  Box,
  Autocomplete,
} from '@mui/material';
import axios from 'axios';

const nameOptions = ['Income', 'Check', 'Salary', 'Job'];

export default function Setup() {
  const [amount, setAmount] = useState('');
  const [username, setUsername] = useState('');
  const [selectedName, setSelectedName] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();
    const currentDate = new Date();

    try {
      const response = await axios.post('http://localhost:8080/addIncome', {
        username,
        name: selectedName,
        amount: parseFloat(amount),
        date: currentDate.toISOString().split('T')[0],
      });

      if (response.status === 201) {
        console.log('Data saved successfully');
        const responseData = await response.data;
        console.log('Response data:', responseData);
      } else {
        console.error('Failed to save data');
      }
    } catch (error) {
      console.error('An error occurred:', error);
    }
  };

  const handleAmountChange = (event) => {
    setAmount(event.target.value);
  };

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const handleNameChange = (_, newValue) => {
    setSelectedName(newValue);
  };

  return (
    <>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box sx={{ marginTop: 15, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
          <Typography variant="h6" gutterBottom>
            Set up
          </Typography>
          <Grid container spacing={3}>
            <Grid item xs={12}>
              <TextField
                margin="normal"
                required
                fullWidth
                id="username"
                label="Username"
                name="username"
                autoComplete="off"
                variant="standard"
                onChange={handleUsernameChange}
              />
              <TextField
                margin="normal"
                required
                fullWidth
                id="amount"
                label="Amount($)"
                name="amount"
                autoComplete="off"
                variant="standard"
                onChange={handleAmountChange}
              />
              <Autocomplete
                id="name"
                options={nameOptions}
                value={selectedName}
                onChange={handleNameChange}
                renderInput={(params) => (
                  <TextField
                    {...params}
                    label="Type"
                    variant="standard"
                    InputProps={{
                      ...params.InputProps,
                      endAdornment: (
                        <>
                          {params.InputProps.endAdornment}
                          {selectedName && (
                            <Button
                              size="small"
                              onClick={() => setSelectedName('')}
                            >
                              Clear
                            </Button>
                          )}
                        </>
                      ),
                    }}
                  />
                )}
                fullWidth
              />
            </Grid>
          </Grid>
          <Button
            onClick={handleSubmit}
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
            Submit
          </Button>
        </Box>
      </Container>
    </>
  );
}
