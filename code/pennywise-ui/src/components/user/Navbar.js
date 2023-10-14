
import * as React from 'react';
import { styled, useTheme, createTheme, ThemeProvider } from '@mui/material/styles';
import Box from '@mui/material/Box';
import MuiDrawer from '@mui/material/Drawer';
import MuiAppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import CssBaseline from '@mui/material/CssBaseline';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import DashboardIcon from '@mui/icons-material/Dashboard';
import PaymentIcon from '@mui/icons-material/Payment';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import LogoutIcon from '@mui/icons-material/Logout';
import LoginIcon from '@mui/icons-material/Login';
import HomeIcon from '@mui/icons-material/Home';
import ChecklistIcon from '@mui/icons-material/Checklist';
import pennyTitle from "../img/penny.png"


import { Link } from 'react-router-dom'
import Dashboard from './Dashboard';

const drawerWidth = 240;

const AppBar = styled(MuiAppBar, {
  shouldForwardProp: (prop) => prop !== 'open',
})(({ theme, open }) => ({
  zIndex: theme.zIndex.drawer + 1,
  transition: theme.transitions.create(['width', 'margin'], {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  ...(open && {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  }),
}));


const Drawer = styled(MuiDrawer, { shouldForwardProp: (prop) => prop !== 'open' })(
  ({ theme, open }) => ({
    '& .MuiDrawer-paper': {
      position: 'relative',
      whiteSpace: 'nowrap',
      width: drawerWidth,
      transition: theme.transitions.create('width', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.enteringScreen,
      }),
      boxSizing: 'border-box',
      ...(!open && {
        overflowX: 'hidden',
        transition: theme.transitions.create('width', {
          easing: theme.transitions.easing.sharp,
          duration: theme.transitions.duration.leavingScreen,
        }),
        width: theme.spacing(7),
        [theme.breakpoints.up('sm')]: {
          width: theme.spacing(9),
        },
      }),
    },
  }),
);

const StyledListItemButton = styled(ListItemButton)`
  ${({ theme }) => `
    cursor: pointer; 
    background-color: ${theme.palette.primary.secondary};
    transition: ${theme.transitions.create(['background-color', 'transform'], {
      duration: theme.transitions.duration.standard,
    })};
    &:hover {
      transform: scale(1.05);
    }
  `}
  `;


const NavBar = () => {

    const theme = createTheme({
        palette: {
          primary: {
            main: "#fff"
          }, 
          secondary: {
            main: "#A2B575"
          }
        }, 
        typography: {
          fontSize: 14,
        }
      })
        const [open, setOpen] = React.useState(true);
      
        const handleDrawerOpen = () => {
          setOpen(true);
        };
      
        const handleDrawerClose = () => {
          setOpen(false);
        };

    

    return(
        <>
            <ThemeProvider theme={theme}>
      <Box sx={{ display: 'flex', width:"50px"}}>
        <CssBaseline />
        <AppBar position="absolute" open={open} sx={{ pb: "15px", pt: "15px", display: "flex", justifyContent: "center", alignItems: "space-between"}}>
          <Toolbar   sx={{
                pr: '24px', 
                display:"flex",
                justifyContent:"space-between",
                alignItems:"center"
                // keep right padding when drawer closed
              }}>
            <Box sx={{display:"flex"}}>
            <IconButton
              color="inherit"
              aria-label="open drawer"
              onClick={handleDrawerOpen}
              edge="start"
              sx={{
                marginRight: "36px",
                ...(open && { display: 'none' }),
              }}
            >
              <MenuIcon />
            </IconButton>

            <Box>
              <img src={pennyTitle}/>
            </Box>

            </Box>
          

            <IconButton
              sx={{
                minHeight: 10,
                display: "flex",
                justifyContent: "center",
                px: 6,
                backgroundColor: "#646464", 
                color: "#fff",
                fontWeight: "bold", 
                border: 1,
                borderRadius: 1 
                }}>

                  

                  <LogoutIcon />
            </IconButton>

            
          </Toolbar>
        </AppBar>
        
        <Drawer variant="permanent" open={open}>
          <Toolbar
          sx={{
              display: "flex",
              alignItems: "center", 
              justifyContent: "flex-end", 
              px: [1]
          }}
          
          >
            <IconButton onClick={() => open === true ? handleDrawerClose() : handleDrawerOpen() }>
              {theme.direction === 'rtl' ? <ChevronRightIcon /> : <ChevronLeftIcon />}
            </IconButton>
          </Toolbar>
          <Divider />

          <List>
              <ListItem  disablePadding sx={{ display: 'block' }} component={Link} to="/dashboard">
                <StyledListItemButton 
                  sx={{
                    minHeight: 60,
                    justifyContent: open ? 'initial' : 'center',
                    px: 2.5,
                    color: "#3F4141", 
                    fontWeight: "bold",
                    display: "flex", 
                    alignItems: "center",
                    justifyContent: "center" 
                  
                  }}
                >
                  <ListItemIcon
                    sx={{
                      minWidth: 0,
                      mr: open ? 3 : 'auto',
                      justifyContent: 'center',
                    }}
                  >
                  <DashboardIcon /> 
                  </ListItemIcon>
                  <ListItemText primary="Dashboard" sx={{ opacity: open ? 1 : 0, color: "#161717"}} />
                </StyledListItemButton>
              </ListItem>

              <ListItem  disablePadding sx={{ display: 'block' }} component={Link} to="/addIncome">
                <StyledListItemButton
                  sx={{
                    minHeight: 60,
                    justifyContent: open ? 'initial' : 'center',
                    px: 2.5,
                    color: "#3F4141", 
                  }}
                >
                  <ListItemIcon
                    sx={{
                      minWidth: 0,
                      mr: open ? 3 : 'auto',
                      justifyContent: 'center',
                    }}
                  >
                  <AttachMoneyIcon /> 
                  </ListItemIcon>
                  <ListItemText primary="Add Income" sx={{ opacity: open ? 1 : 0 }} />
                </StyledListItemButton>
              </ListItem>

              <ListItem  disablePadding sx={{ display: 'block' }} component={Link} to="/addExpense">
                <StyledListItemButton
                  sx={{
                    minHeight: 60,
                    justifyContent: open ? 'initial' : 'center',
                    px: 2.5,
                    color: "#646464", 
                    backgrounfColor: "#F2F2F2"
                  }}
                >
                  <ListItemIcon
                    sx={{
                      minWidth: 0,
                      mr: open ? 3 : 'auto',
                      justifyContent: 'center',
                    }}
                  >
                  <PaymentIcon /> 
                  </ListItemIcon>
                  <ListItemText primary="Add Expense" sx={{ opacity: open ? 1 : 0 }} />
                </StyledListItemButton>
              </ListItem>
       
          </List>
          <Divider />
          <List>
              <ListItem  disablePadding sx={{ display: 'block' }} component={Link} to="/">
                <StyledListItemButton
                  sx={{
                    minHeight: 60,
                    justifyContent: open ? 'initial' : 'center',
                    px: 2.5,
                    color: "#3F4141", 
                    fontWeight: "bold",
                    display: "flex", 
                    alignItems: "center",
                    justifyContent: "center" 
                  
                  }}
                >
                  <ListItemIcon
                    sx={{
                      minWidth: 0,
                      mr: open ? 3 : 'auto',
                      justifyContent: 'center',
                    }}
                  >
                  <LoginIcon /> 
                  </ListItemIcon>
                  <ListItemText primary="Login" sx={{ opacity: open ? 1 : 0, color: "#161717"}} />
                </StyledListItemButton>
              </ListItem>

              <ListItem  disablePadding sx={{ display: 'block' }} component={Link} to="/home">
                <StyledListItemButton
                  sx={{
                    minHeight: 60,
                    justifyContent: open ? 'initial' : 'center',
                    px: 2.5,
                    color: "#3F4141", 
                  }}
                >
                  <ListItemIcon
                    sx={{
                      minWidth: 0,
                      mr: open ? 3 : 'auto',
                      justifyContent: 'center',
                    }}
                  >
                  <HomeIcon /> 
                  </ListItemIcon>
                  <ListItemText primary="Home" sx={{ opacity: open ? 1 : 0 }} />
                </StyledListItemButton>
              </ListItem>

              <ListItem  disablePadding sx={{ display: 'block' }} component={Link} to="/setUp">
                <StyledListItemButton
                  sx={{
                    minHeight: 60,
                    justifyContent: open ? 'initial' : 'center',
                    px: 2.5,
                    color: "#646464", 
                    backgrounfColor: "#F2F2F2"
                  }}
                >
                  <ListItemIcon
                    sx={{
                      minWidth: 0,
                      mr: open ? 3 : 'auto',
                      justifyContent: 'center',
                    }}
                  >
                  <ChecklistIcon/> 
                  </ListItemIcon>
                  <ListItemText primary="Setup" sx={{ opacity: open ? 1 : 0 }} />
                </StyledListItemButton>
              </ListItem>
       
          </List>


        </Drawer>
        </Box>
      </ThemeProvider>





        </>
    )
}

export default NavBar
