import { Formik, Form, useField} from "formik";
import * as Yup from "yup";
import { deposit } from "../service/userService";
import { useSelector } from "react-redux";
import { Button } from "@mui/material";
import { useSnackbar } from "notistack";
import { useNavigate } from 'react-router-dom';
import "./deposit.css"


const Deposit=()=>{
    
    const currentUserData=state=>state.currentUser
    const currentUser=useSelector(currentUserData)
    const navigate = useNavigate();
    const {enqueueSnackbar}=useSnackbar()
    // const navigate = useNavigate();
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

    return(
        <>
            <h1>Deposit</h1>
            <Formik
              initialValues={{
                username:currentUser.username,
                accountNum:"",
                depositAmount:""
              }}
              
              validationSchema={Yup.object().shape({
                 accountNum:Yup.string()
                 .required("Account number is required"),
                 depositAmount:Yup.number()
                 .required("Deposit amount is required")
                  
                })}
                onSubmit={(values, { setSubmitting }) => {
                  setTimeout(() => {
                    let dataToSubmit = {
                        username:values.username,
                        accountNum:values.accountNum,
                        depositAmount:values.depositAmount
                    };
                    
                    deposit(dataToSubmit)
                    .then((response)=>{
                        enqueueSnackbar('Deposit was successful',{
                            variant:'success',
                            anchorOrigin:{
                                vertical:'bottom',horizontal:'center',
                            },
                        });
                            navigate('/dashboard');

                    }).catch((error)=>{
                        enqueueSnackbar('Deposit failed',{
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
               label="Account Number"
               name="accountNum"
               type="accountNum"
               placeholder="Account #"
             />
              <MyTextInput
               label="Deposit Amount"
               name="depositAmount"
               type="depositAmount"
               placeholder="Amount"
             />
           
              <Button type="submit">Submit</Button>
              </Form>
            </Formik>
        </>
    )
}
export default Deposit;