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
import {Song} from "../../models/Song";
import {toast, ToastContainer} from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import {PerformsOn} from "../../models/PerformsOn";

export const AddPerformance = () => {
	const navigate = useNavigate();

	const [performance, setPerformance] = useState<PerformsOn>({
		artist_id: 1,
		song_id: 1,
		nr_of_views: 0,
		duration: "00:00",
		added_by_id: 1
	});

	const [page] = useState(1);
    const [pageSize] = useState(10);
	const [artists, setArtists] = useState<Artist[]>([]);
	const [songs, setSongs] = useState<Song[]>([]);

	const fetchSuggestions1 = async (query: string) => {
		try {
			const url = `${BACKEND_API_URL}/artists/order-by-name/${query}/?page=${page}&page_size=${pageSize}`;
			const response = await fetch(url);
			const { results } = await response.json();
			setArtists(results);
			console.log(results);
		} catch (error) {
			console.error("Error fetching suggestions:", error);
		}
	};

	const debouncedFetchSuggestions1 = useCallback(debounce(fetchSuggestions1, 500), []);

	useEffect(() => {
		return () => {
			debouncedFetchSuggestions1.cancel();
		};
	}, [debouncedFetchSuggestions1]);

	const fetchSuggestions2 = async (query: string) => {
		try {
			const url = `${BACKEND_API_URL}/songs/order-by-name/${query}/?page=${page}&page_size=${pageSize}`;
			const response = await fetch(url);
			const { results } = await response.json();
			setSongs(results);
			console.log(results);
		} catch (error) {
			console.error("Error fetching suggestions:", error);
		}
	};

	const debouncedFetchSuggestions2 = useCallback(debounce(fetchSuggestions2, 500), []);

	useEffect(() => {
		return () => {
			debouncedFetchSuggestions2.cancel();
		};
	}, [debouncedFetchSuggestions2]);

	const addPerformance = async (event: { preventDefault: () => void }) => {
		event.preventDefault();
		try {
			if(performance.nr_of_views < 0)
			{
				throw new Error("No. of views must be greater than or equal to zero!");
			}
			const pattern = /^\d{2}:\d{2}$/;
			if (!pattern.test(performance.duration)) {
				throw new Error("Invalid duration format! Expected format: MM:SS");
			}
			const id = localStorage.getItem('user_id');
			if(id){
				performance.added_by_id = parseInt(id);
			}
			const response = await axios.post(`${BACKEND_API_URL}/performances/`, performance);
			if (response.status < 200 || response.status >= 300) {
				throw new Error("An error occurred while adding the item!");
			} else {
				navigate("/performances");
			}
		} catch (error) {
			toast.error((error as { message: string }).message);
			console.log(error);
		}
	};

	const handleInputChange1 = (event:any, value: any, reason: any) => {
		console.log("input", event, value, reason);

		if (reason === "input") {
			debouncedFetchSuggestions1(value);
		}
	};

	const handleInputChange2 = (event:any, value: any, reason: any) => {
		console.log("input", event, value, reason);

		if (reason === "input") {
			debouncedFetchSuggestions2(value);
		}
	};

	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/performances`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<form onSubmit={addPerformance}>
						<Autocomplete
							id="artist_id"
							options={artists}
							getOptionLabel={(option) => `${option.artist_name}`}
							renderInput={(params) => <TextField {...params} label="Artist" variant="outlined" />}
							filterOptions={(options, state) => options.filter((option) => option.artist_name.toLowerCase().includes(state.inputValue.toLowerCase()))}
							onInputChange={handleInputChange1}
							onChange={( event, value) => {
								if (value) {
									console.log(value);
									console.log(event);
									setPerformance({ ...performance, artist_id: value.id});
								}
							}}
						/>
						<Autocomplete
							id="song_id"
							options={songs}
							getOptionLabel={(option) => `${option.song_name}`}
							renderInput={(params) => <TextField {...params} label="Song" variant="outlined" />}
							filterOptions={(options, state) => options.filter((option) => option.song_name.toLowerCase().includes(state.inputValue.toLowerCase()))}
							onInputChange={handleInputChange2}
							onChange={( event, value) => {
								if (value) {
									console.log(value);
									console.log(event);
									setPerformance({ ...performance, song_id: value.id});
								}
							}}
						/>
						<TextField
							id="nr_of_views"
							label="Number of views"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setPerformance({ ...performance, nr_of_views: +event.target.value })}
						/>
						<TextField
							id="duration"
							label="Duration"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setPerformance({ ...performance, duration: event.target.value })}
						/>
						<ToastContainer/>
						<Button type="submit" sx={{ color: "#72648B" }}>Add Performance</Button>
					</form>
				</CardContent>
			</Card>
		</Container>
	);
};
