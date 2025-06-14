export interface User {
    id: string;
    name: string;
    email: string;
}

export interface AuthorDTO {
    id: string;
    name: string;
}

export interface CommentDTO {
    text: string;
    date: Date;
    author: AuthorDTO;
}

export interface Post {
    id: string;
    date: Date;
    title: string;
    body: string;
    author: AuthorDTO;
    comments: CommentDTO[];
} 