
import { Formik, Form, useField} from "formik";
import * as Yup from "yup";
import { transfer, getAccountsByUser} from "../service/userService";
import { useSelector } from "react-redux";
import { useSnackbar } from "notistack";
import { useNavigate } from 'react-router-dom';
import { Button } from "@mui/material";
import { faHouse } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useState, useEffect } from "react";
import styled from "@emotion/styled";
import './transfer.css'

const Transfer=()=>{
    const currentUserData=state=>state.currentUser
    const currentUser=useSelector(currentUserData)
    const {enqueueSnackbar}=useSnackbar()
    const navigate = useNavigate();
    const [accounts, setAccounts] = useState([]);


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
    // Styled components ....
 const StyledSelect = styled.select`

 `;
 
 
       const MySelect = ({ label, ...props }) => {
         // useField() returns [formik.getFieldProps(), formik.getFieldMeta()]
         // which we can spread on <input> and alse replace ErrorMessage entirely.
         const [field, meta] = useField(props);
         return (
           <>
             <label htmlFor={props.id || props.name}>{label}</label>
             <StyledSelect {...field} {...props} />
             {meta.touched && meta.error ? (
                <div className="error">{meta.error}</div>
             ) : null}
           </>
         );
       };
      useEffect(()=>{
        getAccountsByUser(currentUser.username).then((account)=>{
            setAccounts(account)
           
                
            })
        },[])

    return(
        <>
        <h1 className='title'>Transfer</h1>
            <Formik
              initialValues={{
                username:currentUser.username,
                giveAccNum:"",
                receiveAccNum:"",
                transferAmount:""
              }}
              
              validationSchema={Yup.object().shape({
                 giveAccNum:Yup.string()
                 .required("Giveer Account number is required"),
                 receiveAccNum:Yup.string()
                 .required("Receiver Account number is required"),
                 transferAmount:Yup.number()
                 .required("Transfer amount is required")
                  
                })}
                onSubmit={(values, { setSubmitting }) => {
                  setTimeout(() => {
                    let dataToSubmit = {
                        username:values.username,
                        giveAccNum:values.giveAccNum,
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
              <MySelect label="Giver Account Number" name="giveAccNum" >
            <option value="">Select account number</option>
            {accounts.map((option,index) => (
                option.accountStatus==='CLOSED' ? <option disabled key ={index} value={option.accountNum}>{option.accountNum}n</option>: <option key ={index} value={option.accountNum}>{option.accountNum}</option>  
            ))}
          
          </MySelect>
          <MySelect label="Receiver Account Number" name="receiveAccNum" >
            <option value="">Select account number</option>
            {accounts.map((option,index) => (
              option.accountStatus==='CLOSED' ? <option disabled key ={index} value={option.accountNum}>{option.accountNum}n</option>: <option key ={index} value={option.accountNum}>{option.accountNum}</option>  
            ))}
          
          </MySelect>
              <MyTextInput
               label="Transfer Amount"
               name="transferAmount"
               type="transferAmount"
               placeholder="$0.00"
             />
           
              <Button type="submit">Submit</Button>
              </Form>
            </Formik>
            <Button className="homeButton" variant="outlined" onClick={()=>navigate('../dashboard')}><FontAwesomeIcon icon={faHouse} size="2xl"/></Button>


        </>
    )
}
export default Transfer;