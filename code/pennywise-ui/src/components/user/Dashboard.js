import { Box, Toolbar, Container, Grid, Paper, Typography, Button} from "@mui/material";
import { useState, useEffect } from "react";
import axios from "axios"
import ExpenseTable from "./ExpenseTable"
import ExpenseChart from "./ExpenseChart"
import ExpensePlot from "./ExpensePlot";
import IncomeReport from "./IncomeReport";
import { useParams } from 'react-router-dom';

import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import { format } from 'date-fns';
import NavBar from "./Navbar";
import AddIncome from "./AddIncome";
import AddNewExpense from "./AddExpense"
import SetBudget from "./SetBudget";
import Header from "./Header"

import { Link } from "react-router-dom";

const Dashboard = () => {
  const [expenses, setExpenses] = useState([]);
  const [startDate, setStartDate] = useState(null)
  const [endDate, setEndDate] = useState(null)
  const [errorMessage, setErrorMessage] = useState(null)
  const [username, setUsername] = useState("")
  const [firstName, setFirstName] = useState("")
  const [emailData, setEmail] = useState("")
  const [balance, setBalance] = useState(null)
  const [incomes, setIncomes] = useState([])

  const [userData, setUserData] = useState(null)
  //Getting "email" and "name" parameters from the URL after authentication
  const [gemail, setGEmail] = useState('');
  const [gname, setGName] = useState('');
  const [gfirstName, setGFirstName] = useState('');
  const [glastName, setGLastName] = useState('');
  const [userExists, setUserExists] = useState(false);

  const formattedStartDate = startDate ? format(startDate, 'yyyy-MM-dd') : null; //format for server 
  const formattedEndDate = endDate ? format(endDate, 'yyyy-MM-dd') : null; //format for server

  //creating user in db
  const createUser = async () => {
    try {
      const newUser = {
        firstName: gfirstName,
        lastName: glastName,
        email: gemail,
        username: gemail,
        budget: null,
        roles: ['USER'],
      };

      const response = await axios.post('https://pennywise-backend-81abbbcf7b6a.herokuapp.com/createUser', newUser);
      console.log('New user created:', response.data);
    } catch (error) {
      console.error('Error creating user:', error);
    }
  };

  useEffect(() => {
    //Parse the query string from URL
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);

    //Get the "email" and "name" parameters from URL
    const emailParam = urlParams.get('email');
    const nameParam = urlParams.get('name');

    if (emailParam) {
      setGEmail(emailParam);
    }

    if (nameParam) {
      setGName(nameParam);
      //name split by space
      const nameParts = nameParam.split(' ');
      setGFirstName(nameParts[0]);
      setGLastName(nameParts.slice(1).join(' '));
    }
  }, []);

  useEffect(() => {
    const checkUserExistence = async () => {
      try {
        const response = await axios.get("https://pennywise-backend-81abbbcf7b6a.herokuapp.com/user", {
          params: {
            username: gemail,
          }
        });

        const userExists = response.data;

        if (userExists) {
          console.log("User exists.");
          setUserExists(true);
        } else {
          console.log("User doesn't exist. Proceeding to create the user.");
          createUser(); //create user in db
        }
      } catch (error) {
       if (error.response.status === 404) {
          console.log("User doesn't exist. Proceeding to create the user.");
          createUser(); //create user in db
        } else {
          console.error("Catch error: ", error);
        }
      }
    };

    if (gname && gemail) {
      checkUserExistence();
    }
  }, [gname, gemail]);
  
  useEffect(() => {
      getUserData()

  },[])


  const getUserData = async () => {
    try {
        const res = await axios.get("https://pennywise-backend-81abbbcf7b6a.herokuapp.com/home", {
            params: {
                username: gemail,
            }
        }).then((res) => {
            setUserData(res.data)
            setExpenses(res.data.expenses)
            setBalance(res.data.balance)
            setEmail(res.data.email)
            setUsername(res.data.username)
            setFirstName(res.data.firstName)
            setIncomes(res.data.incomes)
        })
    } catch(err) {
        console.log(err)
        setErrorMessage("Error: failed to get user data")
    }
  }


  
 // get all user data by username 
  const loadExpenses = async () => {
    try {
        const expenseData = await axios.get("https://pennywise-backend-81abbbcf7b6a.herokuapp.com/expenses", {
            params: {
                username: gemail, 
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
            const res = await axios.get("https://pennywise-backend-81abbbcf7b6a.herokuapp.com/home", {
                params: {
                    username: gemail,
                    startDate: formattedStartDate, 
                    endDate: formattedEndDate
                }
            }).then((res) => {
                setUserData(res.data)
                setExpenses(res.data.expenses)
                setBalance(res.data.balance)
                setEmail(res.data.email)
                setUsername(res.data.username)
                setFirstName(res.data.firstName)
                setIncomes(res.data.incomes)
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
        <Box sx={{display:"flex", flexDirection:"column", justifyContent:"flex-start", alignItems: "center",
                background:"#f2f2f2", height:"130vh"}}>
            
            <Header balance={balance} gemail={gemail} gname={gname}/>
            <Box  sx={{display:"flex", flexDirection:"column", width:"80%", mt:5, justifyContent:"space-around", 
                        alignItems:"flex-start", p:4,}}>
                <Typography sx={{fontSize:20}}>Enter Income and Expense Information Here: </Typography>
                <Box sx={{display: "flex", justifyContent:"flex-start", background:"#f2f2f2",
                            width:"100%", alignItems:"flex-start", mt:5}}>
                     <AddIncome gemail={gemail}/>
                     <AddNewExpense gemail={gemail}/>
                      <SetBudget gemail={gemail} />

                </Box>

            </Box>
        <Box sx={{ display: "flex", height: "250vh", width: "100vw", display: "flex", flexDirection:"column",
            justifyContent: "flex-start", alignItems: "center", background:"#F2F2F2"}}>

        <Toolbar />
                <Box sx={{width:"80%", display:"flex", justifyContent:"space-between", alignItems:"flex-start", height:"5vh", 
                    mt:7}}>
                    <Box sx={{width: "30%"}}>
                        <Typography sx={{fontSize:30, fontWeight:"bold", color:"#646464"}}>Expense Report</Typography>
                    </Box>

                </Box>
                <Box sx={{display:"flex", justifyContent:"space-between", alignItems:"center",
                    width:"80%",height:"5vh", boxShadow:1, p:4, pt:6, pb:6}}>
                    <Typography sx={{fontSize:20}}>Filter By Date</Typography>
               
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
               height: "60vh", alignItems:"center", width:"80%", boxShadow:1, p:4, mt:5}} >
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
              <Box sx={{ display: "flex", justifyContent: "space-around",
               height: "60vh", alignItems:"center", width:"80%", mt:"10px", p:4,}} >
                <Typography sx={{fontSize:24, fontWeight:"bold", color:"#646464"}}>Expenses By Date</Typography>
              <ExpensePlot expenses={expenses}/>

              </Box>
              <Box sx={{ display: "flex", justifyContent: "space-around",
               height: "60vh", alignItems:"center", width:"80%", mt:"10px", p:4,}} >
                    <Typography sx={{fontSize:24, fontWeight:"bold", color:"#646464"}}>Incomes By Date</Typography>
       
                    <IncomeReport incomes={incomes} startDate={formattedStartDate} endDate={formattedEndDate}/>

              </Box>
              <Box sx={{height:"30vh"}}>

              </Box>
        
        </Box>
        </Box>
     
        
        </>
    )
}

export default Dashboard; 
