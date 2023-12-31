import Login from "./components/sign-in/Login"
import SetUp from "./components/sign-in/SetUp"
import AddExpense from "./components/user/AddExpense"
import AddIncome from "./components/user/AddIncome"
import Dashboard from "./components/user/Dashboard"
import NavBar from "./components/user/Navbar"
import { Routes, Route} from "react-router-dom" 

import './App.css';

function App() {
  return (
   <>
   <div className="app">

   <NavBar/>
   
    <Routes>
      
      <Route path="/" element={<Login/>}></Route>
      <Route path="/dashboard" element={<Dashboard/>}></Route>

    </Routes>
    

   </div>
    
   </>
  );
}

export default App;

