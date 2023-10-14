import { Box, Toolbar, Container, Grid, Paper, Typography, Button} from "@mui/material";
import { useState, useEffect } from "react";
import axios from "axios"
import ExpenseTable from "./ExpenseTable"
import ExpenseChart from "./ExpenseChart"

import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import { format } from 'date-fns';

const Dashboard = () => {
  const [expenses, setExpenses] = useState([]);
  const [startDate, setStartDate] = useState(null)
  const [endDate, setEndDate] = useState(null)
  const [errorMessage, setErrorMessage] = useState(null)

  useEffect(() => {
      loadExpenses()

  },[])
  
 // get user expenses by username, set expenses as response data 
  const loadExpenses = async () => {
    const URL = process.env.REACT_APP_API_BASE_URL;  
    // const port = process.env.PORT || 3000;  
    try {
        const expenseData = await axios.get("", {
            params: {
                username: "fish66"
            }
        }).then(expenseData => setExpenses(expenseData))
        if (expenseData.status === 200) {
            console.log("success")
        } else {
            console.error("failed to get data")
            setErrorMessage("failed to get data")
        }
    } catch(err) {
        console.log(err)
        setErrorMessage("Error: failed to get user data")
    }
  }

   // get user expenses by username and dates, set expenses as response data 
   const loadExpensesByDate = async () => {
    try {
        const expenseData = await axios.get("", {
            params: {
                username: "fish66"
            }
        }).then(expenseData => setExpenses(expenseData))
        if (expenseData.status === 200) {
            console.log("success")
        } else {
            console.error("failed to get data")
            setErrorMessage("Error: failed to get user data")
        }
    } catch(err) {
        console.log(err)
    }
  }

  const handleClick = (e) => {
    e.preventDefault()
    setErrorMessage("Error: failed to search data")
    console.log(startDate, endDate)
  }



    return (
        <>
        <Box sx={{ display: "flex", height: "100vh", width: "100vw", display: "flex", flexDirection:"column",
            justifyContent: "flex-start", alignItems: "center", mt: "60px", background:"#F2F2F2"}}>

        <Toolbar />
                <Box sx={{display:"flex", justifyContent:"flex-sart", alignItems:"center",
                    width:"72%",height:"10vh"}}>
                    <Typography sx={{fontSize:24}}>Expense Summary</Typography>
                    <LocalizationProvider dateAdapter={AdapterDateFns}>
                        <DatePicker
                        label="Start Date"
                        value={startDate}
                        onChange={(newDate) => {setStartDate(newDate)}}
                        renderInput={(params) => <TextField {...params} fullWidth variant="standard" />}
                        slotProps={{ textField: { size: 'small' } }}
                        sx={{ml:3}}
                        />
                    </LocalizationProvider>
                    <LocalizationProvider dateAdapter={AdapterDateFns}>
                        <DatePicker
                        label="End Date"
                        value={endDate}
                        onChange={(newDate) => {setEndDate(newDate)}}
                        renderInput={(params) => <TextField {...params} fullWidth variant="standard" />}
                        slotProps={{ textField: { size: 'small' } }}
                        sx={{ml:3}}
                        />
                    </LocalizationProvider>
                    <Button
                    sx={{p:1, ml:3, pl:2, pr:2, border:1}}
                    onClick={handleClick}>
                        Search
                    </Button>

                </Box>
              <Container sx={{ display: "flex", justifyContent: "center",
               height: "60vh", ml:30, alignItems:"center", width:"90%", border:1, mt:"10px"}} >
                <Grid item xs={12} md={8} lg={9} mb={5} display="flex" justifyContent="center" width="60%">

                <ExpenseTable expenses={expenses}/>

                </Grid>

                <Grid item xs={12} md={8} lg={9} mb={5} display="flex" justifyContent="center" width="40%"
                alignItems="center" >

                <ExpenseChart expenses={expenses}/>

              </Grid>

              </Container>
              { errorMessage && (
                <p>{errorMessage}</p>
              )}
        
        </Box>
     
      
        
        </>
    )
}

export default Dashboard; 
