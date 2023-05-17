import { Card, CardActions, CardContent, IconButton } from "@mui/material";
import { Container } from "@mui/system";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { BACKEND_API_URL } from "../../constants";
import { Album } from "../../models/Album";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";

export const AlbumDetails = () => {
	const { albumId } = useParams();
	const [album, setAlbum] = useState<Album>();

	useEffect(() => {
		const fetchAlbum = async () => {
			const response = await fetch(`${BACKEND_API_URL}/albums/${albumId}/`);
			const album = await response.json();
			setAlbum(album);
		};
		fetchAlbum();
	}, [albumId]);

	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/albums`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<h1>Album Details</h1>
					<p>Album title: {album?.album_title}</p>
					<p>Number of tracks: {album?.nr_of_tracks}</p>
					<p>Label: {album?.label}</p>
					<p>Year of release: {album?.year_of_release}</p>
					<p>Main artist: {album?.main_artist?.artist_name}</p>
				</CardContent>
				<CardActions>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/albums/${albumId}/edit`}>
						<EditIcon />
					</IconButton>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/albums/${albumId}/delete`}>
						<DeleteForeverIcon sx={{ color: "red" }} />
					</IconButton>
				</CardActions>
			</Card>
		</Container>
	);
};