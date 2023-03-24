import { Formik, Form, useField} from "formik";
import * as Yup from "yup";
import { withdraw,getAccountsByUser } from "../service/userService";
import { useSelector } from "react-redux";
import { Button } from "@mui/material";
import { useSnackbar } from "notistack";
import { useNavigate } from 'react-router-dom';
import { useState,useEffect } from "react";
import { faHouse } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import styled from "@emotion/styled";
import "./withdraw.css"

const Withdraw=()=>{
    const currentUserData=state=>state.currentUser
    const currentUser=useSelector(currentUserData)
    const navigate = useNavigate();
    const {enqueueSnackbar}=useSnackbar()
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
            <h1 className='title'>Withdraw</h1>
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
              <MySelect label="Account Number" name="accountNum" >
            <option value="">Select account number</option>
            {accounts.map((option,index) => (
               option.accountStatus==='CLOSED' ? <option disabled key ={index} value={option.accountNum}>{option.accountNum}n</option>: <option key ={index} value={option.accountNum}>{option.accountNum}</option>  
            ))}
          
          </MySelect>
              <MyTextInput
               label="Withdraw Amount"
               name="withdrawAmount"
               type="withdrawAmount"
               placeholder="$0.00"
             />
           
              <Button type="submit">Submit</Button>
              </Form>
            </Formik>   
            <Button className="homeButton"  variant="outlined" onClick={()=>navigate('../dashboard')}><FontAwesomeIcon icon={faHouse} size="2xl"/></Button>

        </>
    )
}
export default Withdraw;