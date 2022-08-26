import { BrowserRouter as Router,Routes, Route } from 'react-router-dom';
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
import NewAccount from './components/Accounts/newAccount';

function App() {
  return (
    <div className="App">
    <Router>
      <Routes>
        <Route path='/' element={<Home/>}/>
        <Route path='/dashboard' element={<Dashboard/>}></Route>
        <Route path='/accounts' element={<Accounts/>}></Route>
        <Route path='/deposit' element={<Deposit/>}></Route>
        <Route path='/withdraw' element={<Withdraw/>}></Route>
        <Route path='/transfer' element={<Transfer/>}></Route>
        <Route path='/login' element={<Login/>}></Route>
        <Route path='/profile' element={<Profile/>}></Route>
        <Route path='/register' element={<Register/>}></Route>
        <Route path='/newAccount'element={<NewAccount/>}></Route>
      </Routes>
    </Router>
    </div>
  );
}

export default App;
