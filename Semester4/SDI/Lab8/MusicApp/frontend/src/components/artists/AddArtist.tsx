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
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import {Artist} from "../../models/Artist";

export const AddArtist = () => {
	const navigate = useNavigate();

	const [artist, setArtist] = useState<Artist>({
		artist_name: "",
		real_name: "",
        country:"",
        email:"",
		added_by_id: 1
	});

	const addArtist = async (event: { preventDefault: () => void }) => {
		event.preventDefault();
		try {
			const id = localStorage.getItem('user_id');
			if(id){
				artist.added_by_id = parseInt(id);
			}
			const response = await axios.post(`${BACKEND_API_URL}/artists/`, artist);
			if (response.status < 200 || response.status >= 300)
			{
				throw new Error("This email is already in use!");
			}
			else{
				navigate("/artists");
			}

		} catch (error: any) {
			toast.error(error.response.data.email[0]);
			console.log(error);
		}
	};

	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/artists`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<form onSubmit={addArtist}>
						<TextField
							id="artist_name"
							label="Artist name"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setArtist({ ...artist, artist_name: event.target.value })}
						/>
						<TextField
							id="real_name"
							label="Real name"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setArtist({ ...artist, real_name: event.target.value })}
						/>
						<TextField
							id="country"
							label="Country"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setArtist({ ...artist, country: event.target.value })}
						/>
						<TextField
							id="email"
							label="Email"
							variant="outlined"
							fullWidth
							sx={{ mb: 2}}
							onChange={(event) => setArtist({ ...artist, email: event.target.value })}
						/>
						<ToastContainer/>
						<Button type="submit" sx={{ color: "#72648B" }}>Add Artist</Button>
					</form>
				</CardContent>
			</Card>
		</Container>
	);
};