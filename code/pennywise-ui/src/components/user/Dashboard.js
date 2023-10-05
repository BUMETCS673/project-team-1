import { Box, Toolbar, Container, Grid, Paper} from "@mui/material";
import { useState, useEffect } from "react";
import axios from "axios"
import ExpenseTable from "./ExpenseTable"
import ExpenseChart from "./ExpenseChart"

const Dashboard = () => {

  const [expenses, setExpenses] = useState([]);

  useEffect(() => {
      loadExpenses()

  },[])

  const loadExpenses = async () => {
      const expenseData = await axios.get("http://localhost:8080/expenses?username=fish66")
      setExpenses(expenseData.data)
  }


    return (
        <>
        <Box sx={{ display: "flex", height: "100vh", width: "100%", display: "flex", 
            justifyContent: "center", alignItems: "flex-start", mt: "100px", background:"#F2F2F2"}}>

        <Toolbar />
              <Container maxWidth="lg" sx={{ mt: 7, mb: 4, display: "flex", justifyContent: "center",
               height: "60vh", ml:20, alignItems:"center" }} >
                <Grid item xs={12} md={8} lg={9} mb={5} display="flex" justifyContent="center" width="60%">

                <ExpenseTable expenses={expenses}/>

                </Grid>

                <Grid item xs={12} md={8} lg={9} mb={5} display="flex" justifyContent="center" width="100%"
                alignItems="center">

                <ExpenseChart expenses={expenses}/>

              </Grid>

              </Container>
        
        </Box>
     
      
        
        </>
    )
}

export default Dashboard; 