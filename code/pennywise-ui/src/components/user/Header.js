import {Box, Typography, Grid} from "@mui/material"

const Header = ( {balance, gemail, gname} ) => {

    return (
        <>

        <Box sx={{display:"flex", flexDirection:"column", justifyContent:"flex-start", alignItems:"flex-start", 
                width:"80%", height:"20vh", mt:20, boxShadow:1, p:4,}}>
            <Box sx={{display:"flex", width:"100%", justifyContent:"space-between", alignItems:"center"}}>
                <Typography sx={{fontSize:30, fontWeight:"bold", color:"#646464"}}>
                    Welcome to the User Dashboard
                </Typography>

                <Grid sx={{width:"25%", background:"#fff", height: "7vh", display: "flex", flexDirection: "column", 
                    justifyContent:"center", alignItems:"center", boxShadow:1}}>
                    <Typography sx={{fontSize:18, color:"#809159"}}>Remaining Balance: </Typography>
                    <Typography sx={{fontSize:18, color:"#809159", fontWeight:"bold"}}>${balance}</Typography>
                </Grid>
            </Box>

            <Box sx={{display:"flex", flexDirection:"column", justifyContent:"flex-start", alignItems:"flex-start", 
            }}>
                <Typography sx={{fontSize:18}}>Account Information</Typography>
                <Typography>Name: {gname}</Typography>
                <Typography>Email: {gemail} </Typography>
            </Box>
    
        </Box>
        
        </>
    )
}

export default Header;