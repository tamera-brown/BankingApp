import axios from "axios";
import { API_BASE_URL } from "../constants/constants";

   
    
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
    // sessionStorage.getItem('Authorization')
    const accounts=await axios.get(API_BASE_URL+'/accounts/user/'+username,{
        headers:{
            'Authorization':sessionStorage.getItem('Authorization')
        }
    })
    const data= accounts.data;
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