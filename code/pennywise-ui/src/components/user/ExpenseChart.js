import 'chart.js/auto';
import { Pie } from "react-chartjs-2" 
import { Box, Typography } from "@mui/material"
import ExpenseTable from './ExpenseTable';
import { useState } from "react"
import axios from "axios"

const ExpenseChart = ( {expenses} ) => {

    // create array of unique expenseCategories from user expenses data
    const getExpenseCategories = () => {
        const expenseCategories = new Set(); 

        expenses.map((expense) => {
            expenseCategories.add(expense.category)
        })
        return Array.from(expenseCategories); 

    }

    // loop over expenses and calculate total amount spent for input expense category 
    // add key, value pair to input dict 
    const makeCategory= (expenseCategory, dict) => {
      var amountSpent = 0

      expenses.map((expense) => {
        if (expense.category === expenseCategory) {
          amountSpent += expense.amount
          dict.set(expense.category, amountSpent)
        }
      })
    }

    // create dict, loop over expenseCategories and call makeCategory to add 
    // { expenseCategory : amountSpent } to dict 
    const getCats = () => {
      const expenseCategories = getExpenseCategories() 
      var dict = new Map();

      expenseCategories.map((cat) => {
        makeCategory(cat, dict)
      })

      return dict
      
    }

    const myMap = getCats()
    // create array from map and display pie chart of amountSpent per expenseCategory for user 
    const values = Array.from(myMap.values())

    

    const data = {
        // set expenseCategories as labels
        labels: getExpenseCategories().map((category) => category),
        datasets: [{
          label: 'My First Dataset',
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

        <Box sx={{height: 400, width: "100%", display:"flex", flexDirection:"column", 
        justifyContent:"center", alignItems:"center"}}>
          <Typography sx={{fontSize:18, mb:1}}>
            Expenses by Category
          </Typography>

        <Pie data={data}/>
        </Box>

        </>

    )
}  

export default ExpenseChart;
