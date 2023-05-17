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
} from "@mui/material";
import {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import {Artist} from "../../models/Artist";
import {BACKEND_API_URL} from "../../constants";
import AddIcon from "@mui/icons-material/Add";
import ReadMoreIcon from "@mui/icons-material/ReadMore";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import { Paginator } from "../pagination/Pagination";

export const ShowArtists = () => {
    const [loading, setLoading] = useState(false);
    const [artists, setArtists] = useState<Artist[]>([]);
    const [order, setOrder] = useState("asc");
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

    const fetchArtists = async () => {
        setLoading(true);
        const response = await fetch(
          `${BACKEND_API_URL}/artists/?page=${page}&page_size=${pageSize}`
        );
        const { count, next, results } = await response.json();
        setArtists(results);
        setTotalRows(count);
        setIsLastPage(!next);
        setLoading(false);
      };

      useEffect(() => {
        fetchArtists();
      }, [page]);

    const sorting = () => {
        if (order === "asc") {
            const sorted = [...artists].sort((a1, a2) =>
                    a1.artist_name.toLowerCase() > a2.artist_name.toLowerCase() ? 1 : -1);
            setArtists(sorted);
            setOrder("desc");
        }
        if (order === "desc") {
            const sorted = [...artists].sort((a1, a2) =>
                a1.artist_name.toLowerCase() < a2.artist_name.toLowerCase() ? 1 : -1
            );
            setArtists(sorted);
            setOrder("asc");
        }
    }

        return (
		<Container>
			<h1>All Artists </h1>
			{loading && <CircularProgress />}
			{!loading && artists.length === 0 && <p>No artists found</p>}
			{!loading && (
				<IconButton component={Link} sx={{ mr:155 }} to={`/artists/add`}>
					<Tooltip title="Add a new artist" arrow>
						<AddIcon color="primary" />
					</Tooltip>
				</IconButton>
			)}
			{!loading && artists.length > 0 && (
                <>
				<TableContainer component={Paper}>
					<Table sx={{ minWidth: 650 }} aria-label="simple table">
						<TableHead>
							<TableRow>
								<TableCell>#</TableCell>
                                <TableCell onClick={() => sorting()} align="center">Artist name</TableCell>
								<TableCell align="right">Real name</TableCell>
								<TableCell align="right">Country</TableCell>
								<TableCell align="right">Email</TableCell>
                                <TableCell align="right">Number of albums</TableCell>
								<TableCell align="center">Operations</TableCell>
							</TableRow>
						</TableHead>
						<TableBody>
                            {artists.map((artist, index) => (
								<TableRow key={artist.id}>
									<TableCell component="th" scope="row">
										{index + 1}
									</TableCell>
									<TableCell component="th" scope="row">
										<Link to={`/artists/${artist.id}/details`} title="View artist details">
											{artist.artist_name}
										</Link>
									</TableCell>
									<TableCell align="right">{artist.real_name}</TableCell>
									<TableCell align="right">{artist.country}</TableCell>
									<TableCell align="right">{artist.email}</TableCell>
                                    <TableCell align="right">{artist.nr_albums}</TableCell>
									<TableCell align="right">
										<IconButton
											component={Link}
											sx={{ mr: 3 }}
											to={`/artists/${artist.id}/details`}>
											<Tooltip title="View artist details" arrow>
												<ReadMoreIcon color="primary" />
											</Tooltip>
										</IconButton>
										<IconButton component={Link} sx={{ mr: 3 }} to={`/artists/${artist.id}/edit`}>
											<EditIcon />
										</IconButton>
										<IconButton component={Link} sx={{ mr: 3 }} to={`/artists/${artist.id}/delete`}>
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