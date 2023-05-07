import {
	Button,
	Card,
	CardContent,
	IconButton,
	TextField,
} from "@mui/material";
import { Container } from "@mui/system";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { BACKEND_API_URL } from "../../constants";
import { Song } from "../../models/Song";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";

export const AddSong = () => {
	const navigate = useNavigate();

	const [song, setSong] = useState<Song>({
		song_name: "",
		composer: "",
        genre:"",
        year_of_release:0,
	});

	const addSong = async (event: { preventDefault: () => void }) => {
		event.preventDefault();
		try {
			await axios.post(`${BACKEND_API_URL}/songs/`, song);
			navigate("/songs");
		} catch (error) {
			console.log(error);
		}
	};

	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/songs`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<form onSubmit={addSong}>
						<TextField
							id="song_name"
							label="Song name"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setSong({ ...song, song_name: event.target.value })}
						/>
						<TextField
							id="composer"
							label="Composer"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setSong({ ...song, composer: event.target.value })}
						/>
						<TextField
							id="genre"
							label="Genre"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setSong({ ...song, genre: event.target.value })}
						/>
						<TextField
							id="year_of_release"
							label="Year of release"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setSong({ ...song, year_of_release: +event.target.value })}
						/>
						<Button type="submit">Add Song</Button>
					</form>
				</CardContent>
			</Card>
		</Container>
	);
};