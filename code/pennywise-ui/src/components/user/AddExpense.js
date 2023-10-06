import React, { useState, useEffect } from 'react'
import {Avatar, Button, CssBaseline, TextField, FormControlLabel, Link, Grid, Typography, Container, Box, Dialog, DialogTitle, DialogContent} from '@mui/material';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import { useNavigate } from 'react-router-dom';
//import GoogleSignIn from './OauthRedirect';
import { postToken } from '../../api/postToken';

export default function AddNewExpense() {
  // control the open/close state
  const [open, setOpen] = useState(false);

  // store the input value
  const [inputValue, setInputValue] = useState('');

  // handle the dialog open
  const handleOpen = () => {
    setOpen(true);
  };

  // handle dialog close
  const handleClose = () => {
    setOpen(false);
  };

  // handle input field change
  const handleInputChange = (event) => {
    setInputValue(event.target.value);
  };

  // handle form submission
  const handleSubmit = () => {
    console.log('Input Value:', inputValue);
    handleClose();
  };

  // Store the input value
  const [expenseDescription, setExpenseDescription] = useState('');

  // Handle the input change
  const handleDescriptionChange = (event) => {
    setExpenseDescription(event.target.value="name");
  };

  //Handle the form submission
  const handleFormSubmit = async (event) => {
    event.preventDefault();

    // POST request to the endpoint
    try {
      const response = await fetch('', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ description: expenseDescription }),
      });

      //check status of request
      if (response.ok) {
        console.log('Expense created successfully!');
      } else {
        console.log('Failed to create expense');
      }
    } catch (error) {
      console.error('Error', error);
    }
  };
  return (
    <>
    <Container component="main" maxWidth="md">
      <CssBaseline />
      <Box sx={{ padding: 5, marginTop: 15, display: 'flex', flexDirection: 'column', alignItems: 'left', backgroundColor: '#efefef', }}>
        <form onSubmit={handleFormSubmit}>
          <TextField value={expenseDescription} onChange={handleDescriptionChange} fullWidth sx={{ marginBottom: 1 }} id="expense-title-field" label="Expense Title" variant="outlined" />
          <TextField type="number" fullWidth sx={{ marginBottom: 1 }} id="expense-input-field" label="$ Expense Amount" variant="outlined" />
          <Button type="submit" variant="contained" color="primary">
            Create Expense
          </Button>
          <Button variant="outlined" color="primary" onClick={handleOpen}>
            Add New Category
          </Button>
          <Dialog open={open} onClose={handleClose}>
            <DialogTitle>New Category Title</DialogTitle>
            <DialogContent>
              <TextField
              autoFocus
              label="Category Title"
              type="text"
              />
            </DialogContent>
          </Dialog>
        </form>
      </Box>
    </Container>
  </>
);

}

