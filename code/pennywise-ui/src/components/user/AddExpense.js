import React, { useState, useEffect } from 'react'
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
  InputLabel, 
  MenuItem, 
  Dialog, 
  DialogTitle, 
  DialogActions,
  DialogContent, 
  Paper, 
  FormControl, 
  Select,
  Snackbar,
  Alert,
} from '@mui/material';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import { useNavigate } from 'react-router-dom';
//import GoogleSignIn from './OauthRedirect';
import { postToken } from '../../api/postToken';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import axios from 'axios';


export default function AddNewExpense() {

  const [expense, setExpense]                 = useState('');
  const [name, setExpenseName]                = useState('');
  const [category, setExpenseCategory]        = useState('');
  const [selectedDate, setSelectedDate]       = useState(null);
  const [open, setOpen]                       = useState(false);
  const [newCategory, setNewCategory]      = useState('');
  const [categories, setCategories]           = useState([]); // State for storing categories
  const [snackbarOpen, setSnackbarOpen]       = useState(false);
  const [snackbarSeverity, setSnackbarSeverity] = useState('success');
  const [snackbarMessage, setSnackbarMessage] = useState('');

  const handleOpen = () => {
    setOpen(true);
  }

  const handleClose = () => {
    setOpen(false);
  }

  const handleSubmit = async (event) => {
    event.preventDefault();
    const currentDate = new Date();
    const URL = process.env.REACT_APP_API_BASE_URL;
    // const port = process.env.PORT || 3000;

    try {
      const response = await axios.post(`${URL}/addExpense`, {
        username: "pmp2023",
        name: setExpenseName(name),
        category: setExpenseCategory(category),
        amount: parseFloat(expense),
        date: selectedDate,
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
      console.error('Error creating expense:', error);
      setSnackbarSeverity('error');
      setSnackbarMessage('An error occured. Please check again.');
      setSnackbarOpen(true);
    }
  };

  const handleExpenseChange = (event) => {
    setExpense(event.target.value);
  };

  const handleExpenseNameChange = (event) => {
    setExpenseName(event.target.value);
  };

  const handleNewCategory = (eventTwo) => {
    setNewCategory(eventTwo.target.value);
  }

  const handleExpenseCategoryChange = (event) => {
    setExpenseCategory(event.target.value);
  };

  const handleDateChange = (date) => {
    setSelectedDate(date);
  };

  const handleDialogSubmit = async (eventTwo) => {
    eventTwo.preventDefault();
    const URL = process.env.REACT_APP_API_BASE_URL;

    try {
      const response = await axios.post(`${URL}/addExpense`, {
        name: setNewCategory(newCategory),
      });

      if (response.status === 201) {
        console.log('Data saved successfully');
        const responseData = await response.data;
        console.log('Response data:', responseData);
      } else {
        console.error('Failed to save data');
      }
    } catch (error) {
      console.error('Error creating expense:', error);
    }

  };

  return (
    <Container>
      <Grid container justifyContent="center" alignItems="center" style={{ height: '100vh' }}>
        <Grid item xs={12} sm={6}>
          <Paper elevation={3} style={{ padding: '16px' }}>
            <Typography variant="h5" gutterBottom>
              Create New Expense
            </Typography>
            <form onSubmit={handleSubmit}>
              <TextField
                label="Description"
                name="name"
                variant="standard"
                onChange={handleExpenseNameChange}
                fullWidth
                required
                margin="normal"
              />
              <TextField
                label="Amount"
                name="amount"
                onChange={handleExpenseChange}
                fullWidth
                required
                margin="normal"
                type="number"
              />
              <LocalizationProvider dateAdapter={AdapterDateFns}>
                <DatePicker 
                  label="Select Date"
                  value={selectedDate}
                  onChange={handleDateChange}
                  renderInput={(params) => <TextField {...params} fullwidth variant="standard" />}
                  slotProps={{ textField: { size: 'small' }}}
                  sx={{mt:3, ml:9}}
                />
              </LocalizationProvider>
              <FormControl fullWidth required margin="normal">
                <InputLabel>Category</InputLabel>
                <Select
                  name="category"
                  value={expense.category}
                  onChange={handleExpenseCategoryChange}
                >
                  <MenuItem value="">Select a Category</MenuItem>
                  <MenuItem value="Health">Health</MenuItem>
                  {categories.map((category) => (
                    <MenuItem key={category.id} value={category.id}>
                      {category.name}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
              <Button onClick={handleSubmit} type="submit" variant="contained" color="primary" fullWidth>
                Create Expense
              </Button>
            </form>
            <Button onClick={handleOpen} variant="outlined" color="secondary" fullWidth>
              Add New Category
            </Button>
            <Dialog open={open} onClose={handleClose}>
              <DialogTitle>Enter New Category Title</DialogTitle>
              <DialogContent>
                <TextField
                  autoFocus
                  margin="dense"
                  label="Category Title"
                  type="text"
                  fullWidth
                  onChange={handleNewCategory}
                />
              </DialogContent>
              <DialogActions>
                <Button onClick={handleClose} color="primary">
                  Cancel
                </Button>
                <Button onClick={handleDialogSubmit} color="primary">
                  Submit
                </Button>
              </DialogActions>
            </Dialog>
            <Snackbar
              open={snackbarOpen}
              autoHideDuration={6000}
              onClose={() => setSnackbarOpen(false)}
              anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
            >
              <Alert severity={snackbarSeverity}>{snackbarMessage}</Alert>
            </Snackbar>
          </Paper>
        </Grid>
      </Grid>
    </Container>
  );

}

