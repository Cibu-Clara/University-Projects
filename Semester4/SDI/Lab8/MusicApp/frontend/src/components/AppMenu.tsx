import { Box, AppBar, Toolbar, IconButton, Typography, Button } from "@mui/material";
import { Link, useLocation } from "react-router-dom";
import MusicNoteIcon from '@mui/icons-material/MusicNote';
import QueueMusicIcon from '@mui/icons-material/QueueMusic';
import GroupIcon from '@mui/icons-material/Group';
import AlbumIcon from '@mui/icons-material/Album';
import LibraryMusicIcon from '@mui/icons-material/LibraryMusic';
import SignalCellularAltIcon from '@mui/icons-material/SignalCellularAlt';
import HowToRegIcon from '@mui/icons-material/HowToReg';
import LoginIcon from '@mui/icons-material/Login';
import { useEffect, useState } from "react";
import { User } from "../models/User";
import LogoutIcon from '@mui/icons-material/Logout';

export const AppMenu = () => {
	const location = useLocation();
	const path = location.pathname;
	const [user, setUser] = useState<User>({
		id:1,
        username: '',
        first_name: '',
        last_name: '',
        date_of_birth: '',
        location: '',
        bio: '',
		page_size:1
    });

	useEffect(() => {
		const intervalId = setInterval(() => {
			const userString = localStorage.getItem('user');
            const user = userString !== null ? JSON.parse(userString) : null;

			if (user !== null) {
                setUser(user);
                return;
			}

            setUser({
                id: 0,
                username: '',
                first_name: '',
                last_name: '',
                date_of_birth: '',
                location: '',
                bio: '',
                page_size: 0
            });
		}, 250);

		return () => clearInterval(intervalId);}, []);

	return (
		<Box sx={{ flexGrow: 1 }}>
			<AppBar position="absolute" sx={{ marginBottom: "20px", backgroundColor: "#C8BEEA", color: "#72648B" }}>
				<Toolbar >
					<IconButton
						component={Link}
						to="/"
						size="large"
						edge="start"
						color="inherit"
						aria-label="school"
						sx={{ mr: 2 }}>
						<MusicNoteIcon/>
					</IconButton>
					<Typography variant="h6" component="div" sx={{ mr: 5 }}>
						Music application
					</Typography>
					{user.username === '' && (
					<Button
						variant={path.startsWith("/register") ? "outlined" : "text"}
						to="/register"
						component={Link}
						color="inherit"
						sx={{ mr: 5 }}
						startIcon={<HowToRegIcon />}>
						Register
					</Button>
					)}

					{user.username === '' && (
					<Button
						variant={path.startsWith("/login") ? "outlined" : "text"}
						to="/login"
						component={Link}
						color="inherit"
						sx={{ mr: 5 }}
						startIcon={<LoginIcon />}>
						LogIn
					</Button>
					)}

					{user.username !== '' && (
					<Button
						variant={path.startsWith("/logout") ? "outlined" : "text"}
						to="/logout"
						component={Link}
						color="inherit"
						sx={{ mr: 5 }}
						startIcon={<LogoutIcon />}>
						LogOut
					</Button>
					)}
					<Button
						variant={path.startsWith("/songs") ? "outlined" : "text"}
						to="/songs"
						component={Link}
						color="inherit"
						sx={{ mr: 5 }}
						startIcon={<QueueMusicIcon />}>
						Songs
					</Button>
					<Button
						variant={path.startsWith("/artists") ? "outlined" : "text"}
						to="/artists"
						component={Link}
						color="inherit"
						sx={{ mr: 5 }}
						startIcon={<GroupIcon />}>
						Artists
					</Button>
					<Button
						variant={path.startsWith("/albums") ? "outlined" : "text"}
						to="/albums"
						component={Link}
						color="inherit"
						sx={{ mr: 5 }}
						startIcon={<AlbumIcon />}>
						Albums
					</Button>
					<Button
						variant={path.startsWith("/performances") ? "outlined" : "text"}
						to="/performances"
						component={Link}
						color="inherit"
						sx={{ mr: 5 }}
						startIcon={<LibraryMusicIcon />}>
						Performances
					</Button>
					<Button
						variant={path.startsWith("/statistics") ? "outlined" : "text"}
						to="/statistics"
						component={Link}
						color="inherit"
						sx={{ ml: "auto" }}
						startIcon={<SignalCellularAltIcon />}>
						Statistics
					</Button>
				</Toolbar>
			</AppBar>
		</Box>
	);
};