import { Box, Toolbar, Container, Grid, Paper} from "@mui/material";
import { useState, useEffect } from "react";
import axios from "axios"
import ExpenseTable from "./ExpenseTable"
import Chart from "./Chart"

const Dashboard = () => {

//   const [expenses, setExpenses] = useState([]);

//   useEffect(() => {
//       loadExpenses()

//   },[])

//   const loadExpenses = async () => {
//       const expenseData = await axios.get("http://localhost:8080/expenses?username=fish66")
//       setExpenses(expenseData.data)
//   }


    return (
        <>
        <Box sx={{ display: "flex", height: "100vh", width: "100%", display: "flex", 
            justifyContent: "center", alignItems: "flex-start", mt: "100px", background:"#F2F2F2"}}>

        <Toolbar />
              <Container maxWidth="lg" sx={{ mt: 7, mb: 4}}>
                <Grid item xs={12} md={8} lg={9} mb={5} display="flex" justifyContent="center" ml={4}>

                {/* <ExpenseTable expenses={expenses}/> */}

                </Grid>

                <Grid item xs={12} md={4} lg={3} mb={5} display="flex" justifyContent="center" ml={4}>
                {/* <Chart expenses={expenses}/> */}
              </Grid>

              </Container>

        </Box>
     
      
        
        </>
    )
}

export default Dashboard; 