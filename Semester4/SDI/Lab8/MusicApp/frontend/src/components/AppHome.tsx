import { useEffect, useState } from 'react';
import { User } from '../models/User';
import { Card, CardContent, Container, TextField } from '@mui/material';

export const AppHome = () => {
	const [user, setUser] = useState<User>({
		id:1,
        username: '',
        first_name: '',
        last_name: '',
        date_of_birth: '',
        location: '',
        bio: '',
		page_size: 1
    });

    useEffect(() => {
        const userString = localStorage.getItem('user');
        const user = userString !== null ? JSON.parse(userString) : null;

        if (user !== null) {
            setUser(user);
        }
    }, []);


    return (

		<>{user.username === '' && (
                <h1>Home page</h1>
            )}

		{user.username !== '' && (
			<>
		<h1>Welcome back, {user.username}!</h1>
		<>
		<TextField
			id="page_sizes"
			label="Page Size"
			variant="outlined"
			fullWidth
			sx={{ mb: 2, color: "whitesmoke !important" }}
			value={user.page_size}
			type="number"
			onChange={(event) => {
				const size = Number(event.target.value);
				if (size < 0 || size > 100) {
					return;
				}

				setUser({
					...user,
					page_size: size
				});

				localStorage.setItem('user', JSON.stringify({
					...user,
					page_size: size
				}));
			}}
			/>
		</>
		<Container>
		<Card style={{ backgroundColor: "whitesmoke", color: "whitesmoke" }}>
			<CardContent style={{ backgroundColor: "whitesmoke", color: "whitesmoke" }}>
				<TextField
					id="username"
					label="Username"
					variant="outlined"
					fullWidth
					sx={{ mb: 2, color: "whitesmoke !important" }}
					value={user.username}
					InputProps={{
						readOnly: true,
					}}
				/>

				<TextField
					id="FirstName"
					label="First Name"
					variant="outlined"
					fullWidth
					sx={{ mb: 2, color: "whitesmoke !important" }}
					value={user.first_name}
					InputProps={{
						readOnly: true,
					}}
				/>

				<TextField
					id="lastName"
					label="Last Name"
					variant="outlined"
					fullWidth
					sx={{ mb: 2, color: "whitesmoke !important" }}
					value={user.last_name}
					InputProps={{
						readOnly: true,
					}}
				/>

				<TextField
					id="date_of_birth"
					label="Date of Birth"
					variant="outlined"
					fullWidth
					sx={{ mb: 2, color: "whitesmoke !important" }}
					value={user.date_of_birth}
					InputProps={{
						readOnly: true,
					}}
				/>

				<TextField
					id="location"
					label="Location"
					variant="outlined"
					fullWidth
					sx={{ mb: 2, color: "whitesmoke !important" }}
					value={user.location}
					InputProps={{
						readOnly: true,
					}}
				/>

				<TextField
					id="bio"
					label="Bio"
					variant="outlined"
					fullWidth
					sx={{ mb: 2, color: "whitesmoke !important" }}
					value={user.bio}
					InputProps={{
						readOnly: true,
					}}
				/>

			</CardContent>
		</Card>
	</Container>
	</>
	)}
	</>
    );
};