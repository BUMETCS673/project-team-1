import * as React from 'react';
import { DataGrid } from "@mui/x-data-grid"
import { Typography } from '@mui/material';

const columns = [
    {field: "name", headerName: 'Expense', width: "250"}, 
    {field: "category", headerName: "Expense Category", width: "250"}, 
    {field: "amount", headerName: "Amount", width: "250"}, 
]


 const ExpenseTable = ( { expenses }) =>  {
    
    return (
        <>
        <div style={{height: 450, width: "100%", display:"felx", flexDirection:"column", 
        justifyContent:"center", alignItems:"center", m:0}}>
            {/* <Typography sx={{fontSize:18, mb:1}}>Expenses</Typography> */}
            <DataGrid sx={{fontSize:16, background:"#fff", mt:3, height:"90%"}}
            getRowId={row => row.expenseId}
            rows={expenses}
            columns={columns}/>


        </div>
  
        </>
    )
}

export default ExpenseTable; 
