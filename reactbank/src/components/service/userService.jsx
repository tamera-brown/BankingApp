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