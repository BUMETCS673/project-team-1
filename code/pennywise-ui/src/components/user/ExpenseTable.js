import * as React from 'react';
import { DataGrid } from "@mui/x-data-grid"

const columns = [
    {field: "name", headerName: 'Expense', width: "250"}, 
    {field: "category", headerName: "Expense Category", width: "250"}, 
    {field: "amount", headerName: "Amount", width: "250"}, 
]


 const ExpenseTable = ( { expenses }) =>  {
    
    return (
        <>
        <div style={{height: 400, width: "100%", background:"#fff"}}>
            <DataGrid sx={{fontSize:16}}
            getRowId={row => row.expenseId}
            rows={expenses}
            columns={columns}/>


        </div>
  
        </>
    )
}

export default ExpenseTable; 
