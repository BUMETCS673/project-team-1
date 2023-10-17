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
  Alert
} from '@mui/material';
import axios from 'axios';


export default function SetBudget({ gemail }) {
    const [budgetAmount, setBudgetAmount] = useState(null);
    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const [snackbarSeverity, setSnackbarSeverity] = useState('success');
    const [snackbarMessage, setSnackbarMessage] = useState('');



    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            const response = await axios.post("https://pennywise-backend-81abbbcf7b6a.herokuapp.com/setUserBudget", {
                username: gemail,
                amount: formattedBudgetAmount,
            });

            if (response.status === 201) {
                console.log('Budget set successfully');
                const responseData = await response.data;
                console.log('Response data:', responseData);
                setSnackbarSeverity('success');
                setSnackbarMessage('Budget set successfully');
                setSnackbarOpen(true);
            } else {
                console.error('Failed to set budget');
                setSnackbarSeverity('error');
                setSnackbarMessage('Failed to set budget');
                setSnackbarOpen(true);
            }
        } catch (error) {
            console.error('An error occurred:', error);
            setSnackbarSeverity('error');
            setSnackbarMessage('An error occurred. Please check again.');
        }
    };

    const handleBudgetAmountChange = (event) => {
        setBudgetAmount(event.target.value);
    };

    return (
        <>
            <CssBaseline />
            <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: "center", width: "35%", background: "#fff", p: 5, boxShadow: 1 }}>
                <Typography variant="h6" gutterBottom>
                    Set Budget
                </Typography>
                <Grid container spacing={3}>
                    <Grid item xs={12}>
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            id="budgetAmount"
                            label="Budget Amount($)"
                            name="budgetAmount"
                            autoComplete="off"
                            variant="standard"
                            onChange={handleBudgetAmountChange}
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
                    Set Budget
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
