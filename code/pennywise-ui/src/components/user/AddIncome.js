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
  Autocomplete,
  Snackbar,
  Alert
} from '@mui/material';
import axios from 'axios';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import { format } from 'date-fns';

const nameOptions = ['Income', 'Check', 'Salary', 'Job', 'Extra'];

export default function AddIncome( {gemail }) {
    const [amount, setAmount] = useState(null);
    const [selectedName, setSelectedName] = useState('');
    const [selectedDate, setSelectedDate] = useState(null);
    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const [snackbarSeverity, setSnackbarSeverity] = useState('success');
    const [snackbarMessage, setSnackbarMessage] = useState('');

    const formattedDate = selectedDate ? format(selectedDate, 'yyyy-MM-dd') : null;
    const formattedAmount = amount ? parseFloat(amount) : null;

    const URL = process.env.REACT_APP_API_BASE_URL;
    
    const handleSubmit = async (event) => {
        event.preventDefault();
        const currentDate = new Date();

        try {
            const response = await axios.post("https://pennywise-backend-81abbbcf7b6a.herokuapp.com/addIncome", {
                username: gemail,
                name: selectedName,
                amount: formattedAmount,
                date: formattedDate,
            });

            if (response.status === 201) {
                console.log('Data saved successfully');
                const responseData = await response.data;
                console.log('Response data:', responseData);
                setSnackbarSeverity('success');
                setSnackbarMessage('Data saved successfully');
                setSnackbarOpen(true);
            } else {
                console.error('Failed to save data');
                setSnackbarSeverity('error');
                setSnackbarMessage('Failed to save data');
                setSnackbarOpen(true);
            }
        } catch (error) {
            console.error('An error occured:', error);
            setSnackbarSeverity('error');
            setSnackbarMessage('An error occured. Please check again.');
            setSnackbarOpen(true);
            console.log({
                username, selectedName, amount, selectedDate
            })
        }
    };

    const handleAmountChange = (event) => {
        setAmount(event.target.value);
    };

    const handleNameChange = (_, newValue) => {
        setSelectedName(newValue);
    };

    const handleDateChange = (date) => {
        setSelectedDate(date);
    };

    return (
        <>
                <CssBaseline />
                <Box  sx={{ display: 'flex', flexDirection: 'column', alignItems: "center",
                     width:"35%", background:"#fff", p:5, boxShadow:1}}>
                    <Typography variant="h6" gutterBottom>
                        Add Income
                    </Typography>
                    <Grid container spacing={3}>
                        <Grid item xs={12}>
                            <LocalizationProvider dateAdapter={AdapterDateFns}>
                                <DatePicker
                                    label="Select Date"
                                    value={selectedDate}
                                    onChange={handleDateChange}
                                    renderInput={(params) => <TextField {...params} fullWidth variant="standard" />}
                                    slotProps={{ textField: { size: 'small' } }}
                                    sx={{mt:3, ml:9}}
                                />
                            </LocalizationProvider>

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
                                sx={{mt:3}}
                                id="name"
                                options={nameOptions}
                                value={selectedName}
                                isOptionEqualToValue={(option, value) => option.id === value.id}
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
                <Snackbar
                open={snackbarOpen}
                autoHideDuration={6000}
                onClose={() => setSnackbarOpen(false)}
                anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
                >
                    <Alert severity={snackbarSeverity}>{snackbarMessage}</Alert>
                </Snackbar> 
        </>
    );
}
