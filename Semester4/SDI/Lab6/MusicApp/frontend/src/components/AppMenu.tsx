import { Box, AppBar, Toolbar, IconButton, Typography, Button } from "@mui/material";
import { Link, useLocation } from "react-router-dom";
import MusicNoteIcon from '@mui/icons-material/MusicNote';
import QueueMusicIcon from '@mui/icons-material/QueueMusic';

export const AppMenu = () => {
	const location = useLocation();
	const path = location.pathname;

	return (
		<Box sx={{ flexGrow: 1 }}>
			<AppBar position="absolute" sx={{ marginBottom: "20px" }}>
				<Toolbar>
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
					<Button
						variant={path.startsWith("/songs") ? "outlined" : "text"}
						to="/songs"
						component={Link}
						color="inherit"
						sx={{ mr: 5 }}
						startIcon={<QueueMusicIcon />}>
						Songs
					</Button>
				</Toolbar>
			</AppBar>
		</Box>
	);
};