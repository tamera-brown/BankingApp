import { useSelector } from "react-redux";
import React from 'react';
import {Link} from 'react-router-dom'
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMoneyCheckDollar,faWallet,faUser,faMoneyBillTransfer,faDollarSign,faMinus} from "@fortawesome/free-solid-svg-icons";
import { Button } from "@mui/material";


function Dashboard(){
    const isAuthenticatedData=state=>state.isAuthenticated
    const currentUserData=state=>state.currentUser
    const isAuthenticated=useSelector(isAuthenticatedData)
    const currentUser=useSelector(currentUserData)
    return(
        <>
        <h1>Hello {isAuthenticated ? currentUser.username:"Guest"}!</h1>
        <form >

    <Link to = '/accounts'><Button className="menuButton">New Account&nbsp;<FontAwesomeIcon icon={faWallet} size="2xl"/></Button></Link>
    <Link to = '/deposit'><Button className="menuButton">Deposit&nbsp; <FontAwesomeIcon icon={faMoneyCheckDollar} size="2xl"/></Button></Link>
    <Link to = '/withdraw'><Button className="menuButton" >Withdraw&nbsp; <FontAwesomeIcon icon={faDollarSign} size="2xl"/><FontAwesomeIcon icon={faMinus} size="2xl"/></Button></Link>
    <Link to = '/transfer'><Button className="menuButton">Transfer&nbsp;<FontAwesomeIcon icon={faMoneyBillTransfer} size="2xl"/></Button></Link>
    <Link to = '/profile'><Button className="menuButton" >Profile&nbsp;<FontAwesomeIcon icon={faUser} size="2xl"/></Button></Link>

{/* <Link to = '/'><button className="homeButton" onClick={this.logout}>Logout&nbsp;<FontAwesomeIcon icon={faUndo}/></button></Link><Link to = '/'><button className="homeButton" onClick={this.logout}>Logout&nbsp;<FontAwesomeIcon icon={faUndo}/></button></Link> */}


</form>
        </>
    )
}
export default Dashboard;