import "./register.css"
import { Formik, Form, useField} from "formik";
import * as Yup from "yup";
import { register } from "../service/userService";
import { useSnackbar } from "notistack";
import { useNavigate } from 'react-router-dom';
import { Button } from "@mui/material";
import styled from "@emotion/styled";

const Register=()=>{
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
    const MyCheckbox = ({ children, ...props }) => {
        const [field, meta] = useField({ ...props, type: "checkbox" });
        return (
          <>
            <label className="checkbox">
              <input {...field} {...props} type="checkbox" />
              {children}
            </label>
            {meta.touched && meta.error ? (
              <div className="error">{meta.error}</div>
            ) : null}
          </>
        );
      };
      // Styled components ....
const StyledSelect = styled.select`
color: var(--blue);
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
            <h1>Register</h1>
            <Formik
              initialValues={{
                firstName:"",
                lastName:"",
                address:"",
                username:"",
                password:"",
                email:"",
                phoneNum:"",
                account:[{
                    accountType:"",
                    balance:""
                }],
                acceptedTerms:""
              }}
              
              validationSchema={Yup.object().shape({
                 firstName:Yup.string()
                    .required('First name is required'),
                  lastName:Yup.string()
                    .required('Last name is required'),
                  address:Yup.string()
                    .required('Address name is required'),
                  username: Yup.string()
                    .required('Username is required'),
                  password: Yup.string()
                    .min(8, 'Password must be at least 8 characters')
                    .max(15, 'Password has max length of 15 characters')
                    .required('Password is required'),
                  email:Yup.string()
                  .email('Email is invalid')
                  .required('Email is required'),
                  phoneNum:Yup.string()
                  .required('Phone number is required'),
                  accountType:Yup.string()
                  .required('Account Type is required'),
                  balance:Yup.number()
                  .required('Balance is required'),
                  acceptedTerms: Yup.boolean()
            .required("Required")
            .oneOf([true], "You must accept the terms and conditions.")
                })}
                onSubmit={(values, { setSubmitting }) => {
                  setTimeout(() => {
                    let dataToSubmit = {
                        firstName:values.firstName,
                        lastName:values.lastName,
                        address:values.address,
                        username:values.username,
                        password:values.password,
                        email:values.email,
                        phoneNum:values.phoneNum,
                        account:[{
                            accountType:values.accountType,
                            balance:values.balance
                        }]
                    };
                    
                    register(dataToSubmit)
                    .then((response)=>{
                        enqueueSnackbar('Register was successful',{
                            variant:'success',
                            anchorOrigin:{
                                vertical:'bottom',horizontal:'center',
                            },
                        });
                            navigate('/login');

                    }).catch((error)=>{
                        enqueueSnackbar('Register failed',{
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
               label="First Name"
               name="firstName"
               type="firstName"
               placeholder="First Name"
             />
              <MyTextInput
               label="Last Name"
               name="lastName"
               type="lastName"
               placeholder="Last Name"
             />
              <MyTextInput
               label="Address"
               name="address"
               type="address"
               placeholder="ex. 101 Water Street Lanham, MD 20706"
             />
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
              <MyTextInput
               label="Email"
               name="email"
               type="email"
               placeholder="Email"
             />
               <MyTextInput
               label="Phone Number"
               name="phoneNum"
               type="phoneNum"
               placeholder="(XXX) XXX-XXX"
             />
             

              <MySelect label="Account Type" name="accountType" type="account[0].accountType">
            <option value="">Select a account type</option>
            <option value="CHECKING">CHECKING</option>
            <option value="SAVINGS">SAVINGS</option>
          </MySelect>

          <MyTextInput
               label="Initial Deposit"
               name="balance"
               type="account[0].balance"
               placeholder="0.00"
             />

           <MyCheckbox name="acceptedTerms">
            I accept the terms and conditions
          </MyCheckbox>
                <Button type="submit">Submit</Button>
              </Form>
            </Formik>
          </>
        );
  }
  export default Register;