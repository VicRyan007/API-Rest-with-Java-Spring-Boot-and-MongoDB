import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/api'
});

export const getPosts = async () => {
    const response = await api.get('/posts/allposts');
    return response.data;
};

export const getPost = async (id: string) => {
    const response = await api.get(`/posts/${id}`);
    return response.data;
};

export const getUsers = async () => {
    const response = await api.get('/users');
    return response.data;
};

export const getUser = async (id: string) => {
    const response = await api.get(`/users/${id}`);
    return response.data;
};

export const searchPosts = async (text: string) => {
    const response = await api.get(`/posts/titlesearch?text=${text}`);
    return response.data;
};

export const searchPostsByDate = async (text: string, minDate: string, maxDate: string) => {
    const response = await api.get(`/posts/fullsearch?text=${text}&minDate=${minDate}&maxDate=${maxDate}`);
    return response.data;
};

export default api; 