import React from 'react';
import { Card, CardContent, CardHeader, Typography, Avatar, Box } from '@mui/material';
import { Post } from '../types';

interface PostCardProps {
    post: Post;
}

const PostCard: React.FC<PostCardProps> = ({ post }) => {
    return (
        <Card sx={{ maxWidth: 800, margin: '20px auto' }}>
            <CardHeader
                avatar={
                    <Avatar sx={{ bgcolor: 'primary.main' }}>
                        P
                    </Avatar>
                }
                title={post.title}
                subheader={`${post.author.name} - ${new Date(post.date).toLocaleDateString()}`}
            />
            <CardContent>
                <Typography variant="body1" color="text.secondary">
                    {post.body}
                </Typography>
                {post.comments.length > 0 && (
                    <Box mt={2}>
                        <Typography variant="h6" gutterBottom>
                            Comentários ({post.comments.length})
                        </Typography>
                        {post.comments.map((comment, index) => (
                            <Box key={index} sx={{ ml: 2, mb: 1 }}>
                                <Typography variant="subtitle2">
                                    {comment.author.name} - {new Date(comment.date).toLocaleDateString()}
                                </Typography>
                                <Typography variant="body2" color="text.secondary">
                                    {comment.text}
                                </Typography>
                            </Box>
                        ))}
                    </Box>
                )}
            </CardContent>
        </Card>
    );
};

export default PostCard; 