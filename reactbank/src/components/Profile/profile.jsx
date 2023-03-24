import { useEffect, useState } from "react";
import { getUserProfile } from "../service/userService";
import { useSelector } from "react-redux";
import { faHouse } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button } from "@mui/material";

const Profile=()=>{
    const [user,setUser]=useState([])
    const currentUserData=state=>state.currentUser
    const currentUser=useSelector(currentUserData)
    const navigate=useNavigate();

   
    useEffect(()=>{
        getUserProfile(currentUser.username).then((user)=>{
            setUser(user)
                
            })
        },[])

    return(
        <>
        <img className="profileImg" src={`https://ui-avatars.com/api/?name=${user.firstName}+${user.lastName}`} alt="Profile Pic"></img>
        <h3>First Name: {user.firstName}</h3>
        <h3>Last Name: {user.lastName}</h3>
        <h3>Address: {user.address}</h3>
        <h3>Username: {user.username}</h3>
        <h3>Email: {user.email}</h3>
        <h3>Phone Number: {user.phoneNum}</h3>
        <h3></h3>
       
        <Button className="homeButton" variant="outlined" onClick={()=>navigate('../dashboard')}><FontAwesomeIcon icon={faHouse} size="2xl"/></Button>


        </>
    )
}
export default Profile;