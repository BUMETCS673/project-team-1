// import Home from "./components/home/Home"
import Login from "./components/sign-in/Login"
import SetUp from "./components/sign-in/SetUp"
import AddExpense from "./components/user/AddExpense"
// import AddIncome from "./components/user/AddIncome"
import Dashboard from "./components/user/Dashboard"
import NavBar from "./components/user/Navbar"

import { Routes, Route} from "react-router-dom" 

import './App.css';

const express = require('express')
const path = require('path');

const app = express()
const port = process.env.PORT || 3000 // Heroku will need the PORT environment variable

app.use(express.static(path.join(__dirname, 'build')));

app.listen(port, () => console.log(`App is live on port ${port}!`))

function App() {
  return (
   <>
   <div className="app">

   <NavBar/>
   
    <Routes>
      
      <Route path="/addExpense" element={<AddExpense/>}></Route>
      <Route path="/" element={<Login/>}></Route>
      <Route path="/dashboard" element={<Dashboard/>}></Route>
      <Route path="/setUp" element={<SetUp/>}></Route>
    

    </Routes>
    

   </div>
    
   </>
  );
}

export default App;