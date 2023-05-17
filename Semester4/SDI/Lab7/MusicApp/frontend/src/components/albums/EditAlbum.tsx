import {
	Button,
	Card,
	CardContent,
	IconButton,
	TextField,
	Autocomplete
} from "@mui/material";
import { Container } from "@mui/system";
import {useCallback, useEffect, useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import { BACKEND_API_URL } from "../../constants";
import { Artist } from "../../models/Artist";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import {debounce} from 'lodash';
import axios from "axios";
import {Album} from "../../models/Album";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export const EditAlbum = () => {
	const navigate = useNavigate();
	const {albumId} = useParams();
    const id = albumId ? parseInt(albumId) : 0;

	const [album, setAlbum] = useState<Album>({
		id,
		album_title: "",
		nr_of_tracks: 1,
		label: "",
		year_of_release: 2000,
		main_artist_id: 1,
	});

	useEffect(() => {
		const fetchAlbum = async () => {
			const response = await fetch(`${BACKEND_API_URL}/albums/${albumId}/`);
			const album = await response.json();
			setAlbum({
				album_title: album.album_title,
                nr_of_tracks: album.nr_of_tracks,
                label: album.label,
                year_of_release: album.year_of_release,
                main_artist_id: album.main_artist_id
		})
            console.log(album);
		};
		fetchAlbum();
	}, [albumId]);

	const [page] = useState(1);
    const [pageSize] = useState(10);
	const [artists, setArtists] = useState<Artist[]>([]);

	const fetchSuggestions = async (query: string) => {
		try {
			let url = `${BACKEND_API_URL}/artists/order-by-name/${query}/?page=${page}&page_size=${pageSize}`;
			const response = await fetch(url);
			const { results } = await response.json();
			setArtists(results);
			console.log(results);
		} catch (error) {
			console.error("Error fetching suggestions:", error);
		}
	};

	const debouncedFetchSuggestions = useCallback(debounce(fetchSuggestions, 500), []);

	useEffect(() => {
		return () => {
			debouncedFetchSuggestions.cancel();
		};
	}, [debouncedFetchSuggestions]);

	const editAlbum = async (event: { preventDefault: () => void }) => {
		event.preventDefault();
		try {
			if(album.nr_of_tracks <= 0)
			{
				throw new Error("No. of tracks must be greater than zero!");
			}
			if(album.year_of_release < 1800 || album.year_of_release > 2023)
			{
				throw new Error("Not a valid year!");
			}
			const response = await axios.patch(`${BACKEND_API_URL}/albums/${albumId}/`, album);
			if (response.status < 200 || response.status >= 300) {
				throw new Error("An error occurred while updating the item!");
			  } else {
				navigate("/albums");
			  }
		} catch (error) {
			toast.error((error as { message: string }).message);
			console.log(error);
		}
	};

	const handleInputChange = (event:any, value: any, reason: any) => {
		console.log("input", event, value, reason);

		if (reason === "input") {
			debouncedFetchSuggestions(value);
		}
	};

	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/albums`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<form onSubmit={editAlbum}>
						<TextField
							id="album_title"
							label="Album title"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setAlbum({ ...album, album_title: event.target.value })}
						/>
						<TextField
							id="nr_of_tracks"
							label="Number of tracks"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setAlbum({ ...album, nr_of_tracks: +event.target.value })}
						/>
						<TextField
							id="label"
							label="Label"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setAlbum({ ...album, label: event.target.value })}
						/>
						<TextField
							id="year_of_release"
							label="Year of release"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setAlbum({ ...album, year_of_release: +event.target.value })}
						/>
						<Autocomplete
							id="main_artist_id"
							options={artists}
							getOptionLabel={(option) => `${option.artist_name}`}
							renderInput={(params) => <TextField {...params} label="Artist" variant="outlined" />}
							filterOptions={(options, state) => options.filter((option) => option.artist_name.toLowerCase().includes(state.inputValue.toLowerCase()))}
							onInputChange={handleInputChange}
							onChange={(event, value) => {
								if (value) {
									console.log(value);
									console.log(event)
									setAlbum({ ...album, main_artist_id: value.id });
								}
							}}
						/>
						<ToastContainer />
						<Button type="submit">Update Album</Button>
					</form>
				</CardContent>
			</Card>
		</Container>
	);
};
