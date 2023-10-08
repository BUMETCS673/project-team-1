import React, { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext();
//Used constant
const LOCAL_STORAGE_KEY = 'user';
const MS_PER_SECOND = 1000;

function AuthProvider({ children }) {
  const [user, setUser] = useState(null);

  useEffect(() => {
    try {
      const storedUser = JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY));
      setUser(storedUser);
    } catch (error) {
      console.error('Error parsing user data from localStorage:', error);
    }
  }, []);
  
  const getUser = () => {
    try {
      return JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY));
    } catch (error) {
      console.error('Error parsing user data from localStorage:', error);
      return null;
    }
  };

  //check if the key is set or not
  const isAuthenticated = () => {
    const storedUser = getUser();
    if (!storedUser) {
      return false;
    }
    //if expired, log out
    const { data } = storedUser;
    if (Date.now() > data.exp * MS_PER_SECOND) {
      logout();
      return false;
    }

    return true;
  };

  //login function
   const login = (user) => {
    try {
      localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(user));
      setUser(user);
    } catch (error) {
      console.error('Error saving user data to localStorage:', error);
    }
  };
  
  //logout function
  const logout = () => {
    try {
      localStorage.removeItem(LOCAL_STORAGE_KEY);
      setUser(null);
    } catch (error) {
      console.error('Error removing user data from localStorage:', error);
    }
  };  

  const contextValue = {
    user,
    getUser,
    isAuthenticated,
    login,
    logout,
  };

  return <AuthContext.Provider value={contextValue}>{children}</AuthContext.Provider>;
}

export default AuthProvider;

export function useAuth() {
  return useContext(AuthContext);
}