import { useSelector } from "react-redux";
import React from 'react';
import {Link,useNavigate} from 'react-router-dom'
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMoneyCheckDollar,faWallet,faUser,faMoneyBillTransfer,faDollarSign,faMinus,faRightFromBracket} from "@fortawesome/free-solid-svg-icons";
import { Button, Stack, Box } from "@mui/material";
import { useSnackbar } from "notistack";
import {  useDispatch } from "react-redux";

function Dashboard(){
    const isAuthenticatedData=state=>state.isAuthenticated
    const currentUserData=state=>state.currentUser
    const isAuthenticated=useSelector(isAuthenticatedData)
    const currentUser=useSelector(currentUserData)
    const navigate = useNavigate();
    const dispatch = useDispatch()
    const {enqueueSnackbar}=useSnackbar()

    let timeOfDay;
    const date = new Date();
    const hours = date.getHours();
  
    if (hours < 12) {
      timeOfDay = 'Good Morning';
    } else if (hours >= 12 && hours < 17) {
      timeOfDay = 'Good Afternoon';
    } else {
      timeOfDay = 'Good Evening';
    }
  const Logout=()=>{
    enqueueSnackbar('Logout was successful',{
      variant:'success',
      anchorOrigin:{
          vertical:'bottom',horizontal:'center',
      },
  });
  dispatch({
    type:'USER_SIGN_OUT',
    
});
navigate('../login');
  }

    return(
        <>
        <h1 className='title'>{timeOfDay}  {isAuthenticated ? currentUser.username:"Guest"}!</h1>
        

    <Box display="flex" justifyContent="center">
    <Stack spacing={2} direction="row">
    <Link to = '/accounts'><Button variant="outlined" size="large">Account&nbsp;&nbsp;<FontAwesomeIcon icon={faWallet} size="2xl"/></Button></Link>
    <Link to = '/deposit'><Button variant="outlined" size="large">Deposit&nbsp;&nbsp; <FontAwesomeIcon icon={faMoneyCheckDollar} size="2xl"/></Button></Link>
    <Link to = '/withdraw'><Button variant="outlined" size="large" >Withdraw&nbsp;&nbsp; <FontAwesomeIcon icon={faDollarSign} size="2xl"/><FontAwesomeIcon icon={faMinus} size="2xl"/></Button></Link>
    <Link to = '/transfer'><Button variant="outlined" size="large">Transfer&nbsp;&nbsp;<FontAwesomeIcon icon={faMoneyBillTransfer} size="2xl"/></Button></Link>
    <Link to = '/profile'><Button variant="outlined" size="large">Profile&nbsp;&nbsp;<FontAwesomeIcon icon={faUser} size="2xl"/></Button></Link>
    </Stack>
    </Box>
    <Button variant="outlined" size="medium" className="homeButton" onClick={()=>Logout()}><FontAwesomeIcon icon={faRightFromBracket} size="2xl"/></Button>

        </>
    )
}
export default Dashboard;