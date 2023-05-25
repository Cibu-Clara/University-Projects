import { useEffect, useState } from 'react';
import { Card, CardContent, Container, TextField } from '@mui/material';
import { FullUser } from '../models/FullUser';
import { useParams } from 'react-router-dom';
import { BACKEND_API_URL } from '../constants';

export const UserProfile = () => {

    const { profileId } = useParams();

    const [user, setUser] = useState<FullUser>({
        username: '',
        first_name: '',
        last_name: '',
        date_of_birth: '',
        location: '',
        bio: '',
        songs_count:1,
        artists_count:1,
        albums_count:1,
        performances_count:1
    });

    useEffect(() => {
		const fetchUser = async () => {
			const response = await fetch(`${BACKEND_API_URL}/profile/${profileId}/`);
			const user = await response.json();
			setUser(user);
            console.log(user);
		};
		fetchUser();
	}, [profileId]);


    return (

		<>{user.username === '' && (
                <h1>Home page</h1>
            )}

		{user.username !== '' && (
			<>
		<h1>{user.username}' s details!</h1>
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
					id="first_name"
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
					id="last_name"
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

                <TextField
					id="songs_count"
					label="Songs Count"
					variant="outlined"
					fullWidth
					sx={{ mb: 2, color: "whitesmoke !important" }}
					value={user.songs_count}
					InputProps={{
						readOnly: true,
					}}
                    InputLabelProps={{ shrink: true }}
				/>

                <TextField
					id="artists_count"
					label="Artists Count"
					variant="outlined"
					fullWidth
					sx={{ mb: 2, color: "whitesmoke !important" }}
					value={user.artists_count}
					InputProps={{
						readOnly: true,
					}}
                    InputLabelProps={{ shrink: true }}
				/>

                <TextField
					id="albums_count"
					label="Albums Count"
					variant="outlined"
					fullWidth
					sx={{ mb: 2, color: "whitesmoke !important" }}
					value={user.albums_count}
					InputProps={{
						readOnly: true,
					}}
                    InputLabelProps={{ shrink: true }}
				/>

                <TextField
					id="performances_count"
					label="Performances Count"
					variant="outlined"
					fullWidth
					sx={{ mb: 2, color: "whitesmoke !important" }}
					value={user.performances_count}
					InputProps={{
						readOnly: true,
					}}
                    InputLabelProps={{ shrink: true }}
				/>

			</CardContent>
		</Card>
	</Container>
	</>
	)}
	</>
    );


};