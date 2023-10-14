import { useState } from "react"
import {Box, Typography, Grid} from "@mui/material"
import { Line } from "react-chartjs-2" 

const IncomeReport = ( {incomes, startDate, endDate} ) => {

        // create array of unique expenseCategories from user expenses data
        const getIncomeDates = () => {
            const incomeDates = new Set(); 
    
            incomes.map((income) => {
                incomeDates.add(income.date)
            })
            return Array.from(incomeDates).sort(); 
    
        }
    
        // loop over expenses and calculate total amount spent for input expense category 
        // add key, value pair to input dict 
        const makeCategory= (incomeDate, dict) => {
          var amountSpent = 0
    
          incomes.map((income) => {
            if (income.date === incomeDate) {
              amountSpent += income.amount
              dict.set(income.date, amountSpent)
            }
          })
        }
    
        // create dict, loop over expenseCategories and call makeCategory to add 
        // { expenseCategory : amountSpent } to dict 
        const getDates = () => {
          const incomeDates = getIncomeDates() 
          var dict = new Map();
    
          incomeDates.map((date) => {
            makeCategory(date, dict)
          })
    
          return dict
          
        }
    
        const myMap = getDates()
        // create array from map and display pie chart of amountSpent per expenseCategory for user 
        const values = Array.from(myMap.values())

        const data = {
            // set expenseCategories as labels
            labels: getIncomeDates().map((date) => date),
            datasets: [{
              label: 'Incomes',
              // set amountSpent per expenseCategory as data
              data: values.map((value) => value),
              backgroundColor: [
                'rgba(28, 208, 187, 0.39)',
                'rgba(255, 164, 0, 0.5)',
                'rgba(0, 169, 215, 0.5)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(201, 203, 207, 0.2)'
              ],
              borderColor: [
                '#12C1AC',
                '#F19E09',
                '#0496D2',
                'rgb(75, 192, 192)',
                'rgb(54, 162, 235)',
                'rgb(153, 102, 255)',
                'rgb(201, 203, 207)'
              ],
              borderWidth: 1,
              hoverOffset: 4
            }]
          };

 

    return (
        <>
        <Box sx={{width:"100%", height:"100vh", display:"flex", flexDirection:"column", alignItems:"center", 
            justifyContent:"flex-start"}}>
            <Box sx={{width:"100%", height:"5vh"}}>
                <Typography sx={{fontSize:30, fontWeight:"bold", color:"#646464"}}>Income Report</Typography>
                <Typography>{startDate} - {endDate}</Typography>
            </Box>
            <Box sx={{height: 450, width: "90%", display:"flex", flexDirection:"column", 
        justifyContent:"center", alignItems:"center", background: "#fff", p:5, mt:10}}>
                <Typography>Incomes by Date</Typography>
                <Line data={data}/>

            </Box>

        </Box>
        </>

    )
}

export default IncomeReport; 