// import Home from "./components/home/Home"
import Login from "./components/sign-in/Login"
import SetUp from "./components/sign-in/SetUp"
import AddExpense from "./components/user/AddExpense"
import AddIncome from "./components/user/AddIncome"
import Dashboard from "./components/user/Dashboard"
import NavBar from "./components/user/Navbar"
import Signup from "./components/sign-in/Signin";
import { Routes, Route} from "react-router-dom" 

import './App.css';

function App() {
  return (
   <>
   <div className="app">

   <NavBar/>
   
    <Routes>
      
      <Route path="/addExpense" element={<AddExpense/>}></Route>
      <Route path="/addIncome" element={<AddIncome/>}></Route>
      <Route path="/" element={<Login/>}></Route>
      <Route path="/dashboard" element={<Dashboard/>}></Route>
      <Route path="/setUp" element={<SetUp/>}></Route>  
      <Route path="/signup" element={<Signup/>}></Route>
    
    

    </Routes>
    

   </div>
    
   </>
  );
}

export default App;

