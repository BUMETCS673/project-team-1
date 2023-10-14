import axios from 'axios';

export const postToken = async (token) => {
  const URL = process.env.REACT_APP_API_BASE_URL;
  // const port = process.env.PORT || 3000;
  try {
    const response = await axios({
      method: 'POST',
      url: `${URL}/v1/oauth/login`,
      headers: {
        'Authorization': `Bearer ${token}`,
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
