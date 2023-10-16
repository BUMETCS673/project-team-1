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
  Autocomplete,
} from '@mui/material';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import { useNavigate } from 'react-router-dom';
//import GoogleSignIn from './OauthRedirect';
import { postToken } from '../../api/postToken';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import axios from 'axios';
import { format } from 'date-fns';


export default function AddNewExpense( {gemail}) {
  const [expenseAmount, setExpenseAmount] = useState('');
  const [selectedName, setSelectedName] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('');
  const [selectedDate, setSelectedDate] = useState(null);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarSeverity, setSnackbarSeverity] = useState('success');
  const [snackbarMessage, setSnackbarMessage] = useState('');
  const [open, setOpen] = useState(false);
  const [newCategory, setNewCategoryName] = useState('');
  const [expenseCategories, setExpenseCategories] = useState([])
  
  const formattedDate = selectedDate ? format(selectedDate, 'yyyy-MM-dd') : null;

  useEffect(() => {
      getCategories()
   
  }, [])

 // get expenseCategories, set response.data as expenseCategories 
 const getCategories = async () => {
  try {
      const categories = await axios.get("http://localhost:8080/categories")
      .then(categories => formatCategories(categories.data))

  } catch(err) {
      console.log(err)
  }
}

// create array of unique category names, set array as expenseCategories
  const formatCategories = (array) => {
    const categories = new Set();
    array.map((cat) => {
      categories.add(cat.name)
    })
    setExpenseCategories(Array.from(categories))
  }


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

    try {
      const response = await axios.post("http://localhost:8080/addExpense", {
        username: gemail,
        name: selectedName,
        category: selectedCategory,
        amount: parseFloat(expenseAmount),
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
      console.error('Error creating expense:', error);
      setSnackbarSeverity('error');
      setSnackbarMessage('An error occured. Please check again.');
      setSnackbarOpen(true);
    }
  };

  const handleExpenseChange = (event) => {
    setExpenseAmount(event.target.value);
  };

  const handleExpenseNameChange = (event) => {
    setSelectedName(event.target.value);
  };

  const handleCategoryChange = (_, newValue) => {
    setSelectedCategory(newValue);
  };

  const handleDateChange = (date) => {
    setSelectedDate(date);
  };

  const handleNewCategory = (event) => {
    setNewCategoryName(event.target.value);
  }

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
    <>
      <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: "center",
        width:"35%", background:"#fff", p:5, ml:7, boxShadow:1}}>
        <Typography variant="h6" gutterBottom>Add Expense</Typography>
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
              id="name"
              label="Expense Title"
              name="selectedName"
              autoComplete="off"
              variant="standard"
              onChange={handleExpenseNameChange}
            />

            <TextField
              margin="normal"
              required
              fullWidth
              id="amount"
              label="Amount($)"
              name="expenseAmount"
              autoComplete="off"
              variant="standard"
              onChange={handleExpenseChange}
            />

            <Autocomplete
              sx={{mt:3}}
              id="category"
              options={expenseCategories}
              value={selectedCategory}
              isOptionEqualToValue={(option, value) => option.id === value.id}
              onChange={handleCategoryChange}
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
                            onClick={() => setSelectedCategory('')}
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

        <Button
            onClick={handleOpen}
            variant="outlined"
            style={{
              marginTop: '16px',
              backgroundColor: '#A2B575',
              color: 'white',
              padding: '8px 16px',
              border: 'none',
              cursor: 'pointer',
            }}
          >
          Add New Category
        </Button>

        <Dialog open={open} onClose={handleClose}>

          <DialogTitle>New Category</DialogTitle>
          <DialogContent>
            <TextField
              autoFocus
              label="Category Title"
              name="newCategory"
              autoComplete="false"
              variant="standard"
              onChange={handleNewCategory}
            />
          </DialogContent>
          <DialogActions>
            <Button onClick={handleClose}>Cancel</Button>
            <Button onClick={handleDialogSubmit}>Submit</Button>
          </DialogActions>

        </Dialog>

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

