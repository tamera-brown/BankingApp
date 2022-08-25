import { Formik, Form, useField} from "formik";
import * as Yup from "yup";
import { withdraw } from "../service/userService";
import { useSelector } from "react-redux";
import { Button } from "@mui/material";
import { useSnackbar } from "notistack";
import { useNavigate } from 'react-router-dom';
import "./withdraw.css"

const Withdraw=()=>{
    const currentUserData=state=>state.currentUser
    const currentUser=useSelector(currentUserData)
    const navigate = useNavigate();
    const {enqueueSnackbar}=useSnackbar()

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
            <h1>Withdraw</h1>
            <Formik
              initialValues={{
                username:currentUser.username,
                accountNum:"",
                withdrawAmount:""
              }}
              
              validationSchema={Yup.object().shape({
                 accountNum:Yup.string()
                 .required("Account number is required"),
                 withdrawAmount:Yup.number()
                 .required("Deposit amount is required")
                  
                })}
                onSubmit={(values, { setSubmitting }) => {
                  setTimeout(() => {
                    let dataToSubmit = {
                        username:values.username,
                        accountNum:values.accountNum,
                        withdrawAmount:values.withdrawAmount
                    };
                    
                    withdraw(dataToSubmit)
                    .then((response)=>{
                        enqueueSnackbar('Withdraw was successful',{
                            variant:'success',
                            anchorOrigin:{
                                vertical:'bottom',horizontal:'center',
                            },
                        });
                            navigate('/dashboard');

                    }).catch((error)=>{
                        enqueueSnackbar('Withdraw failed',{
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
               label="Withdraw Amount"
               name="withdrawAmount"
               type="withdrawAmount"
               placeholder="Amount"
             />
           
              <Button type="submit">Submit</Button>
              </Form>
            </Formik>   
        </>
    )
}
export default Withdraw;