import {Button, Card, CardContent, IconButton, TextField} from "@mui/material";
import {Container} from "@mui/system";
import {useEffect, useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import {BACKEND_API_URL} from "../../constants";
import {Artist} from "../../models/Artist";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";
import {toast, ToastContainer} from "react-toastify";

export const EditArtist = () => {
    const {artistId} = useParams();
    const navigate = useNavigate();
    const id = artistId ? parseInt(artistId) : 0;
    const [artist, setArtist] = useState<Artist>({
        id,
        artist_name: "",
		real_name: "",
        country:"",
        email:"",
    });

     useEffect(() => {
		const fetchArtist = async () => {
			const response = await fetch(`${BACKEND_API_URL}/artists/${artistId}/`);
			const artist = await response.json();
			setArtist({
				artist_name: artist.artist_name,
                real_name: artist.real_name,
                country: artist.country,
                email: artist.email
		})
            console.log(artist);
		};
		fetchArtist();
	}, [artistId]);

    const editArtist = async (event: { preventDefault: () => void }) => {
            event.preventDefault();
            try {
                const response = await axios.patch(`${BACKEND_API_URL}/artists/${artistId}/`, artist);
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
        }
    ;

    return (
        <Container>
            <Card>
                <CardContent>
                    <IconButton component={Link} sx={{mr: 3}} to={`/artists`}>
                        <ArrowBackIcon/>
                    </IconButton>{" "}
                    <form onSubmit={editArtist}>
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
							sx={{ mb: 2 }}
							onChange={(event) => setArtist({ ...artist, email: event.target.value })}
						/>
						<ToastContainer/>
                        <Button type="submit">Update Artist</Button>
                    </form>
                </CardContent>
            </Card>
        </Container>
    );
};