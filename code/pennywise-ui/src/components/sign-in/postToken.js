import axios from 'axios';

export const postToken = async (token) => {
  const URL = process.env.REACT_APP_API_BASE_URL;
  try {
    const response = await axios.post(`${URL}/v1/oauth/login`, token, {
      withCredentials: true, 
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
    });

    if (response.status !== 200) {
      throw new Error('Bad server response');
    }
    
    return true;
  } catch (error) {
    console.error('POST token Error: ', error.message);
    return false;
  }
};
