// import Home from "./components/home/Home"
import Login from "./components/sign-in/Login"
import SetUp from "./components/sign-in/SetUp"
import AddExpense from "./components/user/AddExpense"
// import AddIncome from "./components/user/AddIncome"
import Dashboard from "./components/user/Dashboard"
import NavBar from "./components/user/Navbar"


import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router } from 'react-router-dom'; 
import App from './App';

import { Routes, Route} from "react-router-dom" 

import './App.css';

ReactDOM.render(
  <Router>
    <App />
  </Router>,
  document.getElementById('root')
);

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

