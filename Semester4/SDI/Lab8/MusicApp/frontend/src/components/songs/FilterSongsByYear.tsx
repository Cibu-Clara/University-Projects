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
	Button,
} from "@mui/material";
import { useEffect, useState } from "react";
import {Link, useParams} from "react-router-dom";
import {BACKEND_API_URL} from "../../constants";
import {Song} from "../../models/Song";
import { Paginator } from "../pagination/Pagination";

export const FilterSongsByYear = () => {
    const [loading, setLoading] = useState(false);
	const [songs, setSongs] = useState<Song[]>([]);
	const { year } = useParams();
    const [order, setOrder] = useState("desc");
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

/*	useEffect(() => {
        setLoading(true);
		fetch(`${BACKEND_API_URL}/songs/filter-by-year/${year}/`)
			.then((response) => response.json())
			.then((data) => {
				setSongs(data);
				setLoading(false);
			});
	}, []);*/

    const fetchFilterSongs = async () => {
        setLoading(true);
        const response = await fetch(
          `${BACKEND_API_URL}/songs/filter-by-year/${year}/?page=${page}&page_size=${pageSize}`
        );
        const { count, next, results } = await response.json();
        setSongs(results);
        setTotalRows(count);
        setIsLastPage(!next);
        setLoading(false);
      };

	useEffect(() => {
		fetchFilterSongs();}, [page]);

    const sorting = () => {
        if (order === "asc") {
            const sorted = [...songs].sort((s1, s2) =>
                    s1.year_of_release < s2.year_of_release ? 1 : -1);
            setSongs(sorted);
            setOrder("desc");
        }
        if (order === "desc") {
            const sorted = [...songs].sort((s1, s2) =>
                s1.year_of_release > s2.year_of_release ? 1 : -1);
            setSongs(sorted);
            setOrder("asc");
        }
    }

	return (
		<Container>
			<h1>Songs released from {year} on</h1>
            {loading && <CircularProgress />}
			{!loading && songs.length === 0 && <p>No songs found</p>}
			{!loading && songs.length > 0 && (
				<>
				<TableContainer component={Paper}>
					<Table sx={{ minWidth: 650 }} aria-label="simple table">
						<TableHead>
							<TableRow>
								<TableCell>#</TableCell>
                                <TableCell onClick={() => sorting()} align="left">Song name</TableCell>
								<TableCell align="left">Composer</TableCell>
								<TableCell align="left">Genre</TableCell>
								<TableCell align="center">Year of release</TableCell>
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
									<TableCell align="left">{song.composer}</TableCell>
									<TableCell align="left">{song.genre}</TableCell>
									<TableCell align="center">{song.year_of_release}</TableCell>
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
			)}
			{!loading && (
				<Button component={Link} sx={{ mr:155 }} to={`/songs/`} variant="contained" style={{color:"whitesmoke"}}>
					Back
				</Button>
			)}
		</Container>
	);
};