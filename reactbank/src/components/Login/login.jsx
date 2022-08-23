import { Link } from "react-router-dom";
import "./login.css"
import { Formik, Form, useField} from "formik";
import * as Yup from "yup";
import { getCurrentUser, login } from "../service/userService";
import { useSnackbar } from "notistack";
import {  useDispatch } from "react-redux";
import { useNavigate } from 'react-router-dom';
import { Button } from "@mui/material";
const Login=()=>{
    const dispatch = useDispatch()
    const {enqueueSnackbar}=useSnackbar()
    const navigate = useNavigate();
    const MyTextInput = ({ label, ...props }) => {
      // useField() returns [formik.getFieldProps(), formik.getFieldMeta()]
      // which we can spread on <input>. We can use field meta to show an error
      // message if the field is invalid and it has been touched (i.e. visited)
      const [field, meta] = useField(props);
      return (
        <>
          <label htmlFor={props.id || props.name}>{label}</label>
          <input className="text-input" {...field} {...props} />
          {meta.touched && meta.error ? (
            <div className="error">{meta.error}</div>
          ) : null}
        </>
      );
    };

      return (
          <>
            <h1>Login</h1>
            <Formik
              initialValues={{
                username: "",
                password: ""
          
              }}
              
              validationSchema={Yup.object().shape({
                  username: Yup.string()
                    .required('Username is required'),
                  password: Yup.string()
                    .min(8, 'Password must be at least 8 characters')
                    .max(15, 'Password has max length of 15 characters')
                    .required('Password is required'),
                })}
                onSubmit={(values, { setSubmitting }) => {
                  setTimeout(() => {
                    let dataToSubmit = {
                      username: values.username,
                      password: values.password
                    };
                    login(dataToSubmit)
                    .then((response)=>{
                        enqueueSnackbar('Login was successful',{
                            variant:'success',
                            anchorOrigin:{
                                vertical:'bottom',horizontal:'center',
                            },
                        });
                       
                       
                        
                        getCurrentUser()
                        .then(response=>{
                            enqueueSnackbar('User authenticated',{
                                variant:'success',
                                anchorOrigin:{
                                    vertical:'bottom',horizontal:'center',
                                },
                            });
                            dispatch({
                                type:'USER_SIGN_IN',
                                currentUser:dataToSubmit,
                            });
                            navigate('/dashboard');
                        }).catch(error=>{
                            enqueueSnackbar('authentication failed',{
                                variant:'error',
                                anchorOrigin:{
                                    vertical:'bottom',horizontal:'center'
                                },
                            });
                        });

                    }).catch((error)=>{
                        enqueueSnackbar('Login failed',{
                            variant:'error',
                            anchorOrigin:{
                                vertical:'bottom',horizontal:'center'
                            },
                        });
                    });
              
            setSubmitting(true);
          }, 500);
        }}
            >
              <Form>
              <MyTextInput
               label="Username"
               name="username"
               type="username"
               placeholder="Username"
             />
             <MyTextInput
               label="Password"
               name="password"
               type="password"
               placeholder="Password"
             />
              
                <Button type="submit">Submit</Button>
                <div>
                  Or <Link to={"/register"}>register now!</Link>
                  </div>
              </Form>
            </Formik>
          </>
        );
  }
  export default Login;