import { useRef , useEffect} from "react";

export default function LoginToGoogle({onGoogleSignIn = () => {}, text = "signin_with"}) {
    const signInButton = useRef(null);
    useEffect(() => {
        //URL to the Google Sign-In
        const googleScriptUrl = 'https://accounts.google.com/gsi/client';
    
        //Callback function after the script is loaded
        const scriptOnLoad = () => {
          window.google.accounts.id.initialize({
            client_id: process.env.REACT_APP_GOOGLE_CLIENT_ID,
            callback: onGoogleSignIn,
          });
    
          //Render Sign-In button
          window.google.accounts.id.renderButton(
            signInButton.current,
            { size: 'large', text, width: '250' }, 
          );
        };
    
        //Load the Google Sign-In script 
        const script = document.createElement('script');
        script.src = googleScriptUrl;
        script.onload = scriptOnLoad;
        document.head.appendChild(script);
    
        return () => {
          //Remove script element when the component unmounts
          document.head.removeChild(script);
        };
      }, [onGoogleSignIn, text]);

      return <div ref={signInButton}></div>;
}