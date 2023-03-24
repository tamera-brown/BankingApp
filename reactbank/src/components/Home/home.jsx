import { Button} from "@mui/material";
import { useNavigate } from "react-router-dom";
const Home=()=>{
    const navigate=useNavigate();
    return(
       <div className="welcome-body">
           <h1 className="title">Welcome</h1>
              <Button type="button" variant="outlined" onClick={()=>navigate('login')}>
                <strong>Log In</strong>
              </Button>
              &nbsp;&nbsp;&nbsp;
              <Button type="button" variant="outlined" onClick={()=>navigate('register')}>
                <strong>Sign Up</strong>
              </Button>
            
       </div>
    )
}
export default Home;