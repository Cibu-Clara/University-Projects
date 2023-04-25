import {Button, Card, CardContent, IconButton, TextField} from "@mui/material";
import {Container} from "@mui/system";
import {useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import {BACKEND_API_URL} from "../../constants";
import {Song} from "../../models/Song";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";

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

    const editSong = async (event: { preventDefault: () => void }) => {
            event.preventDefault();
            try {
                await axios.patch(`${BACKEND_API_URL}/songs/${songId}/`, song);
                navigate("/songs");
            } catch (error) {
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
                        <Button type="submit">Update Song</Button>
                    </form>
                </CardContent>
            </Card>
        </Container>
    );
};