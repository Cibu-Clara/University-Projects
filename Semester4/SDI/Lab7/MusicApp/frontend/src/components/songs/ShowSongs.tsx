import {
    TableContainer,
    Paper,
    Table,
    TableHead,
    TableRow,
    TableCell,
    TableBody,
    CircularProgress,
    Container,
    IconButton,
    Tooltip,
    Button, TextField
} from "@mui/material";
import {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import {Song} from "../../models/Song";
import {BACKEND_API_URL} from "../../constants";
import AddIcon from "@mui/icons-material/Add";
import ReadMoreIcon from "@mui/icons-material/ReadMore";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import { Paginator } from "../pagination/Pagination";

export const ShowSongs = () => {
    const [loading, setLoading] = useState(false);
    const [songs, setSongs] = useState<Song[]>([]);
    const [order, setOrder] = useState("asc");
    let [input, setInput] = useState<number | undefined>();
    const [page, setPage] = useState(1);
    const [pageSize] = useState(10);
    const [totalRows, setTotalRows] = useState(0);
    const [isLastPage, setIsLastPage] = useState(false);

    const setCurrentPage = (newPage: number) => {
        setPage(newPage);
    }

    const goToNextPage = () => {
        if (isLastPage) {
            return;
        }

        setPage(page + 1);
    }

    const goToPrevPage = () => {
        if (page === 1) {
            return;
        }

        setPage(page - 1);
    }

    const fetchSongs = async () => {
        setLoading(true);
        const response = await fetch(
          `${BACKEND_API_URL}/songs/?page=${page}&page_size=${pageSize}`
        );
        const { count, next, results } = await response.json();
        setSongs(results);
        setTotalRows(count);
        setIsLastPage(!next);
        setLoading(false);
      };

      useEffect(() => {
        fetchSongs();
      }, [page]);

    const sorting = () => {
        if (order === "asc") {
            const sorted = [...songs].sort((s1, s2) =>
                    s1.song_name.toLowerCase() > s2.song_name.toLowerCase() ? 1 : -1);
            setSongs(sorted);
            setOrder("desc");
        }
        if (order === "desc") {
            const sorted = [...songs].sort((s1, s2) =>
                s1.song_name.toLowerCase() < s2.song_name.toLowerCase() ? 1 : -1
            );
            setSongs(sorted);
            setOrder("asc");
        }
    }

        return (
		<Container>
			<h1>All Songs </h1>
            <div style={{ display: "flex", alignItems: "center", marginLeft: "900px", marginBottom: "-30px" }}>
            <TextField
                label="Year of Release"
                onChange={(event) => {
						setInput( parseInt(event.target.value))}}
                InputProps={{ style: { color: "black" } }}
                InputLabelProps={{style: {color: 'darkgrey'}}}
                style={{ marginRight: "16px", color:'whitesmoke' }}
            />
            <Button component={Link} sx={{ mr: 3}} to={`/songs/filter-by-year/${input}`} variant="contained" style={{backgroundColor: "#C8BEEA", color:"#72648B"}}>
                Filter
            </Button>
            </div>
			{loading && <CircularProgress />}
			{!loading && songs.length === 0 && <p>No songs found</p>}
			{!loading && (
				<IconButton component={Link} sx={{ mr:155 }} to={`/songs/add`}>
					<Tooltip title="Add a new song" arrow>
						<AddIcon color="primary" />
					</Tooltip>
				</IconButton>
			)}
			{!loading && songs.length > 0 && (
                <>
				<TableContainer component={Paper}>
					<Table sx={{ minWidth: 650 }} aria-label="simple table">
						<TableHead>
							<TableRow>
								<TableCell>#</TableCell>
                                <TableCell onClick={() => sorting()} align="center">Song name</TableCell>
								<TableCell align="right">Composer</TableCell>
								<TableCell align="right">Genre</TableCell>
								<TableCell align="right">Year of release</TableCell>
								<TableCell align="center">Operations</TableCell>
							</TableRow>
						</TableHead>
						<TableBody>
                            {songs.map((song, index) => (
								<TableRow key={song.id}>
									<TableCell component="th" scope="row">
										{index + 1}
									</TableCell>
									<TableCell component="th" scope="row">
										<Link to={`/songs/${song.id}/details`} title="View song details">
											{song.song_name}
										</Link>
									</TableCell>
									<TableCell align="right">{song.composer}</TableCell>
									<TableCell align="right">{song.genre}</TableCell>
									<TableCell align="right">{song.year_of_release}</TableCell>
									<TableCell align="right">
										<IconButton
											component={Link}
											sx={{ mr: 3 }}
											to={`/songs/${song.id}/details`}>
											<Tooltip title="View song details" arrow>
												<ReadMoreIcon color="primary" />
											</Tooltip>
										</IconButton>
										<IconButton component={Link} sx={{ mr: 3 }} to={`/songs/${song.id}/edit`}>
											<EditIcon />
										</IconButton>
										<IconButton component={Link} sx={{ mr: 3 }} to={`/songs/${song.id}/delete`}>
											<DeleteForeverIcon sx={{ color: "red" }} />
										</IconButton>
									</TableCell>
								</TableRow>
							))}
						</TableBody>
					</Table>
				</TableContainer>
                <Paginator
                        rowsPerPage={pageSize}
                        totalRows={totalRows}
                        currentPage={page}
                        isFirstPage={page === 1}
                        isLastPage={isLastPage}
                        setPage={setCurrentPage}
                        goToNextPage={goToNextPage}
                        goToPrevPage={goToPrevPage}
                />
			  </>
        )
        }
    </Container>
    );
};