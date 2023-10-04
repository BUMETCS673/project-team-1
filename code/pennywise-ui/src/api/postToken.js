export const postToken = async (token) => {
    const URL = process.env.REACT_APP_API_BASE_URL;
    try {
      const res = await fetch(`${URL}/v1/oauth/login`, {
        method: 'POST',
        credentials: 'include',
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(token),
      });
      
      if (!res.ok) {
        throw new Error('Bad server response')
      }
      return true;
    } catch (e) {
      console.error('POST token Error: ', e.message);
      return false;
    }
  };