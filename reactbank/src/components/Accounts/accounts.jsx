import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { getAccountsByUser} from '../service/userService';
import { useSelector } from "react-redux";
import { useState,useEffect} from 'react';
import { Button } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus,faHouse,faArrowRight} from '@fortawesome/free-solid-svg-icons';
import CurrencyFormat from 'react-currency-format';

function Accounts(){

    const currentUserData=state=>state.currentUser
    const currentUser=useSelector(currentUserData)
    const [accounts, setAccounts] = useState([]);
    const navigate=useNavigate()

    useEffect(()=>{
        getAccountsByUser(currentUser.username).then((account)=>{
            setAccounts(account)
                
            })
        },[])
      
      function calcTotal(){
       let sum=0;
        {accounts.map((account)=>{
          
            sum += account.balance;
        })}
        return sum;
      }
      
    return(
        <>
        <h1 className='title'>Account Overview</h1>
        <Link to='/newAccount'><Button variant="outlined">New Account <FontAwesomeIcon icon={faPlus} size="2xl"/></Button></Link>

      <TableContainer component={Paper}>
      <Table sx={{ minWidth: 650 }} aria-label="spanning table">
        <TableHead>
          <TableRow>
            <TableCell>Account Number</TableCell>
            <TableCell align="right">Account Type</TableCell>
            <TableCell align="right">Balance</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {accounts.map((row) => (
            <TableRow onClick={()=>navigate(`./history/${row.accountNum}`)}
              key={row.accountNum}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
              <TableCell component="th" scope="row">
                {row.accountNum}
              </TableCell>
              <TableCell align="right">{row.accountType}</TableCell>
              <TableCell align="right"> <CurrencyFormat value={row.balance} displayType={"text"} prefix={'$'} thousandSeparator={true} fixedDecimalScale={true} decimalScale={2}/> <FontAwesomeIcon icon={faArrowRight} size="lg" onClick={()=>navigate("/history")}></FontAwesomeIcon></TableCell>
            </TableRow>
          ))}
          <TableRow>
            <TableCell rowSpan={2} />
            <TableCell>Total</TableCell>
            <TableCell align="right"><CurrencyFormat value={calcTotal()} displayType={"text"} prefix={'$'} thousandSeparator={true} fixedDecimalScale={true} decimalScale={2}/></TableCell>
          </TableRow>
        </TableBody>
      </Table>
    </TableContainer>
  <Button className="homeButton" variant="outlined" onClick={()=>navigate('../dashboard')}><FontAwesomeIcon icon={faHouse} size="2xl"/></Button>
        </>
    );
}
export default Accounts;