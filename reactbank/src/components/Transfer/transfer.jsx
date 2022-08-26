
import { Formik, Form, useField} from "formik";
import * as Yup from "yup";
import { transfer } from "../service/userService";
import { useSelector } from "react-redux";
import { useSnackbar } from "notistack";
import { useNavigate } from 'react-router-dom';
import { Button } from "@mui/material";

const Transfer=()=>{
    const currentUserData=state=>state.currentUser
    const currentUser=useSelector(currentUserData)
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
    return(
        <>
        <h1>Transfer</h1>
            <Formik
              initialValues={{
                giveUsername:currentUser.username,
                giveAccNum:"",
                receiveUsername:"",
                receiveAccNum:"",
                transferAmount:""
              }}
              
              validationSchema={Yup.object().shape({
                 giveAccNum:Yup.string()
                 .required("Giveer Account number is required"),
                 receiveUsername:Yup.string()
                 .required("Receiver username is required"),
                 receiveAccNum:Yup.string()
                 .required("Receiver Account number is required"),
                 transferAmount:Yup.number()
                 .required("Transfer amount is required")
                  
                })}
                onSubmit={(values, { setSubmitting }) => {
                  setTimeout(() => {
                    let dataToSubmit = {
                        giveUsername:values.giveUsername,
                        giveAccNum:values.giveAccNum,
                        receiveUsername:values.receiveUsername,
                        receiveAccNum:values.receiveAccNum,
                        transferAmount:values.transferAmount
                    };
                    
                    transfer(dataToSubmit)
                    .then((response)=>{
                        enqueueSnackbar('Transfer was successful',{
                            variant:'success',
                            anchorOrigin:{
                                vertical:'bottom',horizontal:'center',
                            },
                        });
                            navigate('/dashboard');

                    }).catch((error)=>{
                        enqueueSnackbar('Transfer failed',{
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
               label="Giver Account Number"
               name="giveAccNum"
               type="giveAccNum"
               placeholder="Account #"
             />
             <MyTextInput
             label='Reciver Username'
             name='receiveUsername'
             type='receiveUsername'
             placeholder='Username'
             />
                <MyTextInput
               label="Receiver Account Number"
               name="receiveAccNum"
               type="receiveAccNum"
               placeholder="Account #"
             />
              <MyTextInput
               label="Transfer Amount"
               name="transferAmount"
               type="transferAmount"
               placeholder="Amount"
             />
           
              <Button type="submit">Submit</Button>
              </Form>
            </Formik>
        </>
    )
}
export default Transfer;