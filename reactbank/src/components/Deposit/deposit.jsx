import { Formik, Form, useField} from "formik";
import * as Yup from "yup";
import { deposit } from "../service/userService";
import { useSelector } from "react-redux";
import { Button } from "@mui/material";
import { useSnackbar } from "notistack";
import { useNavigate } from 'react-router-dom';
import { faHouse } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { getAccountsByUser} from '../service/userService';
import { useState,useEffect} from 'react';
import styled from "@emotion/styled";
import "./deposit.css"


const Deposit=()=>{
    
    const currentUserData=state=>state.currentUser
    const currentUser=useSelector(currentUserData)
    const [accounts, setAccounts] = useState([]);
  
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
            <h1 className='title'>Deposit</h1>
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
         <MySelect label="Account Number" name="accountNum" >
            <option value="">Select account number</option>
            {accounts.map((option,index) => (
              option.accountStatus==='CLOSED' ? <option disabled key ={index} value={option.accountNum}>{option.accountNum}n</option>: <option key ={index} value={option.accountNum}>{option.accountNum}</option>  
            ))}
          
          </MySelect>
          
              <MyTextInput
               label="Deposit Amount"
               name="depositAmount"
               type="depositAmount"
               placeholder="$0.00"
             />
            {/* <CurrencyFormat value={"45555556"} type="text" prefix={'$'} thousandSeparator={true} name={"depositAmount"} onChange={(e) => setAmount(e.target.value)} required/> */}
              <Button variant="outlined" type="submit">Submit</Button>
              </Form>

            </Formik>
            <Button className="homeButton" variant="outlined" onClick={()=>navigate('../dashboard')}><FontAwesomeIcon icon={faHouse} size="2xl"/></Button>


        </>
    )
}
export default Deposit;