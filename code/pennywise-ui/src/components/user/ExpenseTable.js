import * as React from 'react';
import { DataGrid } from "@mui/x-data-grid"

const columns = [
    {field: "name", headerName: 'Expense', width: "320"}, 
    {field: "category", headerName: "Expense Category", width: "320"}, 
    {field: "amount", headerName: "Amount", width: "320"}, 
]


 const ExpenseTable = ( { expenses }) =>  {
    
    return (
        <>
        <div style={{height: 300, width: "90%", background:"#fff"}}>
            <DataGrid
            getRowId={row => row.expenseId}
            rows={expenses}
            columns={columns}/>


        </div>
  
        </>
    )
}

export default ExpenseTable; 
