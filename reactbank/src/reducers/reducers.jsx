import { ACCESS_TOKEN } from "../components/constants/constants"

const initialState={
    currentUser:null,
    isAuthenticated:false
}

export default function appReducer(state=initialState,action){
    switch(action.type){
        case "USER_SIGN_IN":
            return{
                ...state,
                currentUser:action.currentUser,
                isAuthenticated:true
            }
        case "USER_SIGN_OUT":
            sessionStorage.removeItem('Authorization')
            return{
                ...state,
                currentUser:null,
                isAuthenticated:false
            }
            default:
       
                return state
            }
    
}
