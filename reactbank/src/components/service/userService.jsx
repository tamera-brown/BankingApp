import axios from "axios";
import jwt_decode from 'jwt-decode';
export const API_BASE_URL="http://localhost:8080/api";


   
    
export async function login(loginRequest){
    const auth= await axios.post(API_BASE_URL+'/auth/login',loginRequest)
        let token='Bearer '+ auth.data.accessToken;
        sessionStorage.setItem('Authorization',token)
  }
    
export function getCurrentUser(){
    if(!sessionStorage.getItem('Authorization')){
        return Promise.reject('No access token set');
    }
    return Promise.resolve('got token');
}
export async function register(registerRequest){
    await axios.post(API_BASE_URL+'/users/createUser',registerRequest)

}
export async function getAccountsByUser(username){
    const accounts=await axios.get(API_BASE_URL+'/accounts/user/'+username,{
        headers:{
            'Authorization':sessionStorage.getItem('Authorization')
        }
    })
    const data= accounts.data;
    return data
}
export async function  getAccountHistory(accountNum){
    const account=await axios.get(API_BASE_URL+'/accounts/'+accountNum,{
        headers:{
            'Authorization':sessionStorage.getItem('Authorization')
        }
    })
    const data= account.data;
    return data
}
export async function deposit(depositRequest){
    await axios.post(API_BASE_URL+'/transactions/deposit',depositRequest,{
        headers:{
            'Authorization':sessionStorage.getItem('Authorization')
        }
    })
}
export async function withdraw(withdrawRequest){
    await axios.post(API_BASE_URL+'/transactions/withdraw',withdrawRequest,{
        headers:{
            'Authorization':sessionStorage.getItem('Authorization')
        }
    })
}
export async function addAccount(newRequest,username){
    await axios.post(API_BASE_URL+'/accounts/AddAccount/'+username,newRequest,{
        headers:{
            'Authorization':sessionStorage.getItem('Authorization')
        }
    })
}
export async function transfer(transferRequest){
    await axios.post(API_BASE_URL+'/transactions/transfer',transferRequest,{
        headers:{
            'Authorization':sessionStorage.getItem('Authorization')
        }
    })
}
export async function closeAccount(accountNum){
    await axios.patch(API_BASE_URL+'/accounts/closeAccount/'+accountNum,{
        headers:{
            'Authorization':sessionStorage.getItem('Authorization')
        }
    })
}

export async function getUserProfile(username){
    const userDetails=await axios.get(API_BASE_URL+'/users/usernames/'+username,{
        headers:{
            'Authorization':sessionStorage.getItem('Authorization')
        }
    })
    const data= userDetails.data;
    return data
}
export function ifTokenExpired(){
let token = sessionStorage.getItem('Authorization')
let decodedToken = jwt_decode(token);
let currentDate = new Date();
// JWT exp is in seconds
if (decodedToken.exp * 1000 < currentDate.getTime()) {
    return false
} else {
    // sessionStorage.removeItem('Authorization')
  return true

}
}