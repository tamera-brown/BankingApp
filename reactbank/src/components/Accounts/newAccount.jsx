import "./newAccount.css"
import { Formik, Form, useField} from "formik";
import * as Yup from "yup";
import { addAccount } from "../service/userService";
import { useSnackbar } from "notistack";
import { useNavigate } from 'react-router-dom';
import { useSelector } from "react-redux";
import { Button } from "@mui/material";
import styled from "@emotion/styled";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHouse } from "@fortawesome/free-solid-svg-icons";

const NewAccount=()=>{
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
 // Styled components ....
 const StyledSelect = styled.select`
 
 `;
 
 const StyledErrorMessage = styled.div`
 font-size: 12px;
 color: var(--red-600);
 width: 400px;
 margin-top: 0.25rem;
 &:before {
   content: "❌ ";
   font-size: 10px;
 }
 @media (prefers-color-scheme: dark) {
   color: var(--red-300);
 }
 `;
 
 const StyledLabel = styled.label`
 margin-top: 1rem;
 `;
 
 const MySelect = ({ label, ...props }) => {
 // useField() returns [formik.getFieldProps(), formik.getFieldMeta()]
 // which we can spread on <input> and alse replace ErrorMessage entirely.
 const [field, meta] = useField(props);
 return (
   <>
     <StyledLabel htmlFor={props.id || props.name}>{label}</StyledLabel>
     <StyledSelect {...field} {...props} />
     {meta.touched && meta.error ? (
       <StyledErrorMessage>{meta.error}</StyledErrorMessage>
     ) : null}
   </>
 );
 };
      return (
          <>
            <h1 className='title'>New Account</h1>
            <Formik
              initialValues={{
                    accountType:"",
                    balance:""
                
              }}
              
              validationSchema={Yup.object().shape({
                  accountType:Yup.string()
                  .required('Account Type is required'),
                  balance:Yup.number()
                  .required('Balance is required'),
                
                })}
                onSubmit={(values, { setSubmitting }) => {
                  setTimeout(() => {
                    let dataToSubmit = {
                            accountType:values.accountType,
                            balance:values.balance
                        
                    };
                    
                    addAccount(dataToSubmit,currentUser.username)
                    .then((response)=>{
                        enqueueSnackbar('Account creation was successful',{
                            variant:'success',
                            anchorOrigin:{
                                vertical:'bottom',horizontal:'center',
                            },
                        });
                            navigate('/dashboard');

                    }).catch((error)=>{
                        enqueueSnackbar('Account creation failed',{
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
              <MySelect label="Account Type" name="accountType" type="account[0].accountType">
            <option value="">Select a account type</option>
            <option value="CHECKING">CHECKING</option>
            <option value="SAVINGS">SAVINGS</option>
          </MySelect>

          <MyTextInput
               label="Initial Deposit"
               name="balance"
               type="balance"
               placeholder="$0.00"
             />
                <Button type="submit">Submit</Button>
              </Form>
            </Formik>
            <Button className="homeButton" variant="outlined" onClick={()=>navigate('../dashboard')}><FontAwesomeIcon icon={faHouse} size="2xl"/></Button>


          </>
        );
  }
  export default NewAccount;