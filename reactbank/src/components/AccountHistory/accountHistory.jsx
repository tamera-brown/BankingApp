import { faHouse} from '@fortawesome/free-solid-svg-icons';
import { Button } from '@mui/material';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useNavigate, useParams } from 'react-router-dom';
import { closeAccount, getAccountHistory, getCurrentUser } from '../service/userService';
import { useState, useEffect } from 'react';
import { useSnackbar } from "notistack";
import TableHead from '@mui/material/TableHead';
import PropTypes from 'prop-types';
import { useTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableFooter from '@mui/material/TableFooter';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import IconButton from '@mui/material/IconButton';
import FirstPageIcon from '@mui/icons-material/FirstPage';
import KeyboardArrowLeft from '@mui/icons-material/KeyboardArrowLeft';
import KeyboardArrowRight from '@mui/icons-material/KeyboardArrowRight';
import LastPageIcon from '@mui/icons-material/LastPage';
import CurrencyFormat from 'react-currency-format';


 function TablePaginationActions(props){

    const theme = useTheme();
    const { count, page, rowsPerPage, onPageChange } = props;
  
    const handleFirstPageButtonClick = (event) => {
      onPageChange(event, 0);
    };
  
    const handleBackButtonClick = (event) => {
      onPageChange(event, page - 1);
    };
  
    const handleNextButtonClick = (event) => {
      onPageChange(event, page + 1);
    };
  
    const handleLastPageButtonClick = (event) => {
      onPageChange(event, Math.max(0, Math.ceil(count / rowsPerPage) - 1));
    };
  
    return (
      <Box sx={{ flexShrink: 0, ml: 2.5 }}>
        <IconButton
          onClick={handleFirstPageButtonClick}
          disabled={page === 0}
          aria-label="first page"
        >
          {theme.direction === 'rtl' ? <LastPageIcon /> : <FirstPageIcon />}
        </IconButton>
        <IconButton
          onClick={handleBackButtonClick}
          disabled={page === 0}
          aria-label="previous page"
        >
          {theme.direction === 'rtl' ? <KeyboardArrowRight /> : <KeyboardArrowLeft />}
        </IconButton>
        <IconButton
          onClick={handleNextButtonClick}
          disabled={page >= Math.ceil(count / rowsPerPage) - 1}
          aria-label="next page"
        >
          {theme.direction === 'rtl' ? <KeyboardArrowLeft /> : <KeyboardArrowRight />}
        </IconButton>
        <IconButton
          onClick={handleLastPageButtonClick}
          disabled={page >= Math.ceil(count / rowsPerPage) - 1}
          aria-label="last page"
        >
          {theme.direction === 'rtl' ? <FirstPageIcon /> : <LastPageIcon />}
        </IconButton>
      </Box>
    );
  }
  
  TablePaginationActions.propTypes = {
    count: PropTypes.number.isRequired,
    onPageChange: PropTypes.func.isRequired,
    page: PropTypes.number.isRequired,
    rowsPerPage: PropTypes.number.isRequired,
  };
  

   function AccountHistory() {
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(5);
      const {accountNum}=useParams()
  const [account,setAccount]=useState([])
  const [userTrans,setUserTrans]=useState([])
  const navigate=useNavigate()
  const {enqueueSnackbar}=useSnackbar()

    // Avoid a layout jump when reaching the last page with empty rows.
    const emptyRows =
      page > 0 ? Math.max(0, (1 + page) * rowsPerPage - userTrans.length) : 0;
  
    const handleChangePage = (event, newPage) => {
      setPage(newPage);
    };
  
    const handleChangeRowsPerPage = (event) => {
      setRowsPerPage(parseInt(event.target.value, 10));
      setPage(0);
    };
   useEffect(()=>{
    getAccountHistory(accountNum).then((userAccount)=>{
        setAccount(userAccount)
        setUserTrans(userAccount.transaction)
        })
    },[])
    return (
      <><>
        <h1 className='title'>Transaction History</h1><CurrencyFormat value={account.balance} displayType={"text"} prefix={'$'} thousandSeparator={true} fixedDecimalScale={true} decimalScale={2} />
        <br/>
        <Button variant="outlined" onClick={() => 
          closeAccount(account.accountNum).then((response)=>{
                   enqueueSnackbar('Account is closed',{
                       variant:'success',
                       anchorOrigin:{
                           vertical:'bottom',horizontal:'center',
                       }
                   });
                       navigate('/accounts');
          })}>Close Account</Button>
      </><TableContainer component={Paper}>
          <Table sx={{ minWidth: 500 }} aria-label="custom pagination table">
            <TableHead>
              <TableRow>
                <TableCell>Transaction Date</TableCell>
                <TableCell align="right">Transaction Type</TableCell>
                <TableCell align="right">Transaction Description</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {(rowsPerPage > 0
                ? userTrans.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                : userTrans
              ).map((row) => (
                <TableRow key={row.transactionId}>
                  <TableCell component="th" scope="row">
                    {row.transactionDate}
                  </TableCell>
                  <TableCell style={{ width: 160 }} align="right">
                    {row.transactionType}
                  </TableCell>
                  <TableCell style={{ width: 160 }} align="right">
                    {row.description}
                  </TableCell>
                </TableRow>
              ))}

              {emptyRows > 0 && (
                <TableRow style={{ height: 53 * emptyRows }}>
                  <TableCell colSpan={6} />
                </TableRow>
              )}
            </TableBody>
            <TableFooter>
              <TableRow>
                <TablePagination
                  rowsPerPageOptions={[5, 10, 25, { label: 'All', value: -1 }]}
                  colSpan={3}
                  count={userTrans.length}
                  rowsPerPage={rowsPerPage}
                  page={page}
                  SelectProps={{
                    inputProps: {
                      'aria-label': 'rows per page',
                    },
                    native: true,
                  }}
                  onPageChange={handleChangePage}
                  onRowsPerPageChange={handleChangeRowsPerPage}
                  ActionsComponent={TablePaginationActions} />
              </TableRow>
            </TableFooter>
          </Table>
        </TableContainer><Button className="homeButton" variant="outlined" onClick={() => navigate('../dashboard')}><FontAwesomeIcon icon={faHouse} size="2xl" /></Button></>
    );
  }
  
 export default AccountHistory;