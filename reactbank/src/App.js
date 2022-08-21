import { BrowserRouter as Routes,Route, Router } from 'react-router-dom';
import Home from './components/Home/home';
import Dashboard from './components/Dashboard/dashboard';
import Accounts from './components/Accounts/accounts';
import Deposit from './components/Deposit/deposit';
import Withdraw from './components/Withdraw/withdraw';
import Transfer from './components/Transfer/transfer';
import Register from './components/Register/register';
import Profile from './components/Profile/profile';
import Login from './components/Login/login';
import './App.css';

function App() {
  return (
    <div className="App">
    <Router>
      <Routes>
        <Route path='/' element={<Home/>}/>
        <Route path='/dashboard' element={<Dashboard/>}/>
        <Route path='/accounts' element={<Accounts/>}/>
        <Route path='/deposit' element={<Deposit/>}/>
        <Route path='/withdraw' element={<Withdraw/>}/>
        <Route path='/transfer' element={<Transfer/>}/>
        <Route path='/login' element={<Login/>}/>
        <Route path='/profile' element={<Profile/>}/>
        <Route path='/register' element={<Register/>}/>
      </Routes>
    </Router>
    </div>
  );
}

export default App;
