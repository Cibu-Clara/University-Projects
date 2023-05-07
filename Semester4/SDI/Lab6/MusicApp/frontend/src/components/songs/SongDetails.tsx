import { Card, CardActions, CardContent, IconButton } from "@mui/material";
import { Container } from "@mui/system";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { BACKEND_API_URL } from "../../constants";
import { Song } from "../../models/Song";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";

export const SongDetails = () => {
	const { songId } = useParams();
	const [song, setSong] = useState<Song>();

	useEffect(() => {
		const fetchSong = async () => {
			const response = await fetch(`${BACKEND_API_URL}/songs/${songId}/`);
			const song = await response.json();
			setSong(song);
		};
		fetchSong();
	}, [songId]);

	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/songs`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<h1>Song Details</h1>
					<p>Song name: {song?.song_name}</p>
					<p>Composer: {song?.composer}</p>
					<p>Genre: {song?.genre}</p>
					<p>Year of release: {song?.year_of_release}</p>
					<p>Song artists:</p>
					<ul>
						{song?.artists?.map((artist) => (
							<li key={artist.id}>{artist.artist_name}</li>
						))}
					</ul>
				</CardContent>
				<CardActions>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/songs/${songId}/edit`}>
						<EditIcon />
					</IconButton>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/songs/${songId}/delete`}>
						<DeleteForeverIcon sx={{ color: "red" }} />
					</IconButton>
				</CardActions>
			</Card>
		</Container>
	);
};