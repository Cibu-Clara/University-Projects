import { Card, CardActions, CardContent, IconButton } from "@mui/material";
import { Container } from "@mui/system";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { BACKEND_API_URL } from "../../constants";
import { Artist } from "../../models/Artist";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";

export const ArtistDetails = () => {
	const { artistId } = useParams();
	const [artist, setArtist] = useState<Artist>();

	useEffect(() => {
		const fetchArtist = async () => {
			const response = await fetch(`${BACKEND_API_URL}/artists/${artistId}/`);
			const artist = await response.json();
			setArtist(artist);
		};
		fetchArtist();
	}, [artistId]);

	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/artists`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<h1>Artist Details</h1>
					<p>Artist name: {artist?.artist_name}</p>
					<p>Real name: {artist?.real_name}</p>
					<p>Country: {artist?.country}</p>
					<p>Email: {artist?.email}</p>
					<p>Artist's songs:</p>
					<ul>
						{artist?.songs?.map((song) => (
							<li key={song.id}>{song.song_name}</li>
						))}
					</ul>
					<p>Artist's albums:</p>
					<ul>
						{artist?.albums?.map((album) => (
							<li key={album.id}>{album.album_title}</li>
						))}
					</ul>
				</CardContent>
				<CardActions>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/artists/${artistId}/edit`}>
						<EditIcon />
					</IconButton>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/artists/${artistId}/delete`}>
						<DeleteForeverIcon sx={{ color: "red" }} />
					</IconButton>
				</CardActions>
			</Card>
		</Container>
	);
};