import { Box, Toolbar, Container, Grid, Paper, Typography, Button} from "@mui/material";
import { useState, useEffect } from "react";
import axios from "axios"
import ExpenseTable from "./ExpenseTable"
import ExpenseChart from "./ExpenseChart"
import ExpensePlot from "./ExpensePlot";

import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import { format } from 'date-fns';

const Dashboard = () => {
  const [expenses, setExpenses] = useState([]);
  const [startDate, setStartDate] = useState(null)
  const [endDate, setEndDate] = useState(null)
  const [errorMessage, setErrorMessage] = useState(null)
  const [username, setUsername] = useState("")
  const [firstName, setFirstName] = useState("")
  const [email, setEmail] = useState("")
  const [balance, setBalance] = useState(null)

  const [userData, setUserData] = useState(null)

  const formattedStartDate = startDate ? format(startDate, 'yyyy-MM-dd') : null; //format for server 
  const formattedEndDate = endDate ? format(endDate, 'yyyy-MM-dd') : null; //format for server 

  useEffect(() => {
      getUserData()

  },[])

  const getUserData = async () => {
    try {
        const res = await axios.get("http://localhost:8080/home", {
            params: {
                username: "fish66"
            }
        }).then((res) => {
            setExpenses(res.data.expenses)
            setBalance(res.data.balance)
            setEmail(res.data.email)
            setUsername(res.data.username)
            setFirstName(res.data.firstName)
        })
    } catch(err) {
        console.log(err)
        setErrorMessage("Error: failed to get user data")
    }
  }


  
 // get all user data by username 
  const loadExpenses = async () => {
    try {
        const expenseData = await axios.get("http://localhost:8080/expenses", {
            params: {
                username: "fish66", 
            }
        }).then(expenseData => setExpenses(expenseData.data))
    } catch(err) {
        console.log(err)
        setErrorMessage("Error: failed to get user data")
    }
  }

   // get filtered user data by username and dates 
    const getUserDataByDate = async () => {
        try {
            const res = await axios.get("http://localhost:8080/home", {
                params: {
                    username: "fish66",
                    startDate: formattedStartDate, 
                    endDate: formattedEndDate
                }
            }).then((res) => {
                setExpenses(res.data.expenses)
                setBalance(res.data.balance)
                setEmail(res.data.email)
                setUsername(res.data.username)
                setFirstName(res.data.firstName)
            })
        } catch(err) {
            console.log(err)
            setErrorMessage("Error: failed to search data")
        }
    }


  const handleClick = (e) => {
    e.preventDefault()
    getUserDataByDate()

  }



    return (
        <>
        <Box sx={{ display: "flex", height: "170vh", width: "100vw", display: "flex", flexDirection:"column",
            justifyContent: "flex-start", alignItems: "center", mt: "60px", background:"#F2F2F2"}}>

        <Toolbar />
                <Box sx={{width:"80%", display:"flex", justifyContent:"space-between", alignItems:"flex-start", height:"5vh", 
                    mt:7}}>
                    <Box sx={{width: "30%"}}>
                        <Typography sx={{fontSize:30, fontWeight:"bold", color:"#646464"}}>Expense Dashboard</Typography>
                        <Typography sx={{fontSize:14}}>Username: {username}</Typography>
                        <Typography sx={{fontSize:14}}>Email: {email} </Typography>
                    </Box>

                    <Box sx={{border:1, width: "25%", mt:2}}>
                        <Grid sx={{width:"100%", background:"#fff", height: "7vh", display: "flex", flexDirection: "column", 
                            justifyContent:"center", alignItems:"center"}}>
                            <Typography sx={{fontSize:18, color:"#809159"}}>Remaining Balance: </Typography>
                            <Typography sx={{fontSize:18, color:"#809159", fontWeight:"bold"}}>${balance}</Typography>
                        </Grid>
                    </Box>

                </Box>
                <Box sx={{display:"flex", justifyContent:"flex-start", alignItems:"center",
                    width:"80%",height:"5vh", mt:10, mb:0}}>
                    <Typography sx={{fontSize:20}}>Filter Expenses By Date</Typography>
                    <LocalizationProvider dateAdapter={AdapterDateFns}>
                        <DatePicker
                        label="Start Date"
                        value={startDate}
                        onChange={(newDate) => {setStartDate(newDate)}}
                        renderInput={(params) => <TextField {...params} fullWidth variant="standard" />}
                        slotProps={{ textField: { size: 'small' } }}
                        sx={{ml:3, background:"#fff"}}
                        />
                    </LocalizationProvider>
                    <LocalizationProvider dateAdapter={AdapterDateFns}>
                        <DatePicker
                        label="End Date"
                        value={endDate}
                        onChange={(newDate) => {setEndDate(newDate)}}
                        renderInput={(params) => <TextField {...params} fullWidth variant="standard" />}
                        slotProps={{ textField: { size: 'small' } }}
                        sx={{ml:3,  background:"#fff"}}
                        />
                    </LocalizationProvider>
                    <Button
                    sx={{p:1, ml:3, pl:2, pr:2, border:1 }}
                    onClick={handleClick}>
                        Search
                    </Button>

                </Box>

              <Box sx={{ display: "flex", justifyContent: "center",
               height: "50vh", alignItems:"center", width:"80%"}} >
                <Grid item xs={12} md={8} lg={9} mb={5} display="flex" justifyContent="center" width="60%">

                <ExpenseTable expenses={expenses}/>

                </Grid>

                <Grid item xs={12} md={8} lg={9} mb={5} display="flex" justifyContent="center" width="40%"
                alignItems="center" >

                <ExpenseChart expenses={expenses}/>

              </Grid>

              </Box>

              { errorMessage && (
                <Typography sx={{color:"red", fontSize:16}}>{errorMessage}</Typography>
              )}
              <Box sx={{ display: "flex", justifyContent: "center",
               height: "60vh", alignItems:"center", width:"80%", mt:"10px"}} >

              <ExpensePlot expenses={expenses}/>

              </Box>
        
        </Box>
     
        
        </>
    )
}

export default Dashboard; 
