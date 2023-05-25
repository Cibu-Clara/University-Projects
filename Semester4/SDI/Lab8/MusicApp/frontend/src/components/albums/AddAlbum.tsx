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
import { Link, useNavigate } from "react-router-dom";
import { BACKEND_API_URL } from "../../constants";
import { Artist } from "../../models/Artist";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import {debounce} from 'lodash';
import axios from "axios";
import {Album} from "../../models/Album";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export const AddAlbum = () => {
	const navigate = useNavigate();

	const [album, setAlbum] = useState<Album>({
		album_title: "",
		nr_of_tracks: 1,
		label: "",
		year_of_release: 2000,
		main_artist_id: 1,
        added_by_id: 1
	});

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

	const addAlbum = async (event: { preventDefault: () => void }) => {
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
            const id = localStorage.getItem('user_id');
			if(id){
				album.added_by_id = parseInt(id);
			}
			const response = await axios.post(`${BACKEND_API_URL}/albums/`, album);
			if (response.status < 200 || response.status >= 300) {
				throw new Error("An error occurred while adding the item!");
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
					<form onSubmit={addAlbum}>
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
							onChange={( event, value) => {
								if (value) {
									console.log(value);
									console.log(event);
									setAlbum({ ...album, main_artist_id: value.id});
								}
							}}
						/>
						<ToastContainer />
						<Button type="submit" sx={{ color: "#72648B" }}>Add Album</Button>
					</form>
				</CardContent>
			</Card>
		</Container>
	);
};
