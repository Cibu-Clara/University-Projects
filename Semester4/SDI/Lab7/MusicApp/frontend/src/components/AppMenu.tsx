import { Box, AppBar, Toolbar, IconButton, Typography, Button } from "@mui/material";
import { Link, useLocation } from "react-router-dom";
import MusicNoteIcon from '@mui/icons-material/MusicNote';
import QueueMusicIcon from '@mui/icons-material/QueueMusic';
import GroupIcon from '@mui/icons-material/Group';
import AlbumIcon from '@mui/icons-material/Album';
import LibraryMusicIcon from '@mui/icons-material/LibraryMusic';
import SignalCellularAltIcon from '@mui/icons-material/SignalCellularAlt';

export const AppMenu = () => {
	const location = useLocation();
	const path = location.pathname;

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