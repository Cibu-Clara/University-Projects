import {Button, Card, CardContent, IconButton, TextField} from "@mui/material";
import {Container} from "@mui/system";
import {useEffect, useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import {BACKEND_API_URL} from "../../constants";
import {Song} from "../../models/Song";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";
import {toast, ToastContainer} from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export const EditSong = () => {
    const {songId} = useParams();
    const navigate = useNavigate();
    const id = songId ? parseInt(songId) : 0;
    const [song, setSong] = useState<Song>({
        id,
        song_name: "",
		composer: "",
        genre:"",
        year_of_release:0,
    });

     useEffect(() => {
		const fetchSong = async () => {
			const response = await fetch(`${BACKEND_API_URL}/songs/${songId}/`);
			const song = await response.json();
			setSong({
				song_name: song.song_name,
                composer: song.composer,
                genre: song.genre,
                year_of_release: song.year_of_release
		})
            console.log(song);
		};
		fetchSong();
	}, [songId]);

    const editSong = async (event: { preventDefault: () => void }) => {
            event.preventDefault();
            try {
				if(song.year_of_release < 1800 || song.year_of_release > 2023)
				{
					throw new Error("Not a valid year!");
				}
                const response = await axios.patch(`${BACKEND_API_URL}/songs/${songId}/`, song);
                if (response.status < 200 || response.status >= 300) {
				throw new Error("An error occurred while updating the item!");
			  } else {
				navigate("/songs");
			  }
            } catch (error) {
				toast.error((error as { message: string }).message);
                console.log(error);
            }
        }
    ;

    return (
        <Container>
            <Card>
                <CardContent>
                    <IconButton component={Link} sx={{mr: 3}} to={`/songs`}>
                        <ArrowBackIcon/>
                    </IconButton>{" "}
                    <form onSubmit={editSong}>
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
						<ToastContainer/>
                        <Button type="submit">Update Song</Button>
                    </form>
                </CardContent>
            </Card>
        </Container>
    );
};