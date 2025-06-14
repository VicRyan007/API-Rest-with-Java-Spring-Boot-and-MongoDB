import React, { useEffect, useState } from 'react';
import { Container, Typography, TextField, Box } from '@mui/material';
import { Post } from '../types';
import PostCard from '../components/PostCard';
import { getPosts, searchPosts } from '../services/api';

const Home: React.FC = () => {
    const [posts, setPosts] = useState<Post[]>([]);
    const [searchText, setSearchText] = useState('');

    useEffect(() => {
        loadPosts();
    }, []);

    const loadPosts = async () => {
        try {
            const data = await getPosts();
            setPosts(data);
        } catch (error) {
            console.error('Erro ao carregar posts:', error);
        }
    };

    const handleSearch = async (text: string) => {
        setSearchText(text);
        try {
            if (text.trim()) {
                const data = await searchPosts(text);
                setPosts(data);
            } else {
                await loadPosts();
            }
        } catch (error) {
            console.error('Erro ao buscar posts:', error);
        }
    };

    return (
        <Container>
            <Box sx={{ my: 4 }}>
                <Typography variant="h4" component="h1" gutterBottom>
                    Meu Blog de Posts
                </Typography>
                <TextField
                    fullWidth
                    label="Buscar posts"
                    variant="outlined"
                    value={searchText}
                    onChange={(e) => handleSearch(e.target.value)}
                    sx={{ mb: 4 }}
                />
                {posts.map((post) => (
                    <PostCard key={post.id} post={post} />
                ))}
            </Box>
        </Container>
    );
};

export default Home; 