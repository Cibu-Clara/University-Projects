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
import {Album} from "../../models/Album";
import {BACKEND_API_URL} from "../../constants";
import AddIcon from "@mui/icons-material/Add";
import ReadMoreIcon from "@mui/icons-material/ReadMore";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import { Paginator } from "../pagination/Pagination";

export const ShowAlbums = () => {
    const [loading, setLoading] = useState(false);
    const [albums, setAlbums] = useState<Album[]>([]);
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

    const fetchAlbums = async () => {
        setLoading(true);
        const response = await fetch(
          `${BACKEND_API_URL}/albums/?page=${page}&page_size=${pageSize}`
        );
        const { count, next, results } = await response.json();
        setAlbums(results);
        setTotalRows(count);
        setIsLastPage(!next);
        setLoading(false);
      };

      useEffect(() => {
        fetchAlbums();
      }, [page]);

    const sorting = () => {
        if (order === "asc") {
            const sorted = [...albums].sort((a1, a2) =>
                    a1.album_title.toLowerCase() > a2.album_title.toLowerCase() ? 1 : -1);
            setAlbums(sorted);
            setOrder("desc");
        }
        if (order === "desc") {
            const sorted = [...albums].sort((a1, a2) =>
                a1.album_title.toLowerCase() < a2.album_title.toLowerCase() ? 1 : -1
            );
            setAlbums(sorted);
            setOrder("asc");
        }
    }

        return (
		<Container>
			<h1>All Albums </h1>
			{loading && <CircularProgress />}
			{!loading && albums.length === 0 && <p>No albums found</p>}
			{!loading && (
				<IconButton component={Link} sx={{ mr:155 }} to={`/albums/add`}>
					<Tooltip title="Add a new album" arrow>
						<AddIcon color="primary" />
					</Tooltip>
				</IconButton>
			)}
			{!loading && albums.length > 0 && (
                <>
				<TableContainer component={Paper}>
					<Table sx={{ minWidth: 650 }} aria-label="simple table">
						<TableHead>
							<TableRow>
								<TableCell>#</TableCell>
                                <TableCell onClick={() => sorting()} align="center">Album title</TableCell>
								<TableCell align="right">Number of tracks</TableCell>
								<TableCell align="right">Label</TableCell>
								<TableCell align="right">Year of release</TableCell>
								<TableCell align="right">Main artist</TableCell>
								<TableCell align="center">Operations</TableCell>
							</TableRow>
						</TableHead>
						<TableBody>
                            {albums.map((album, index) => (
								<TableRow key={album.id}>
									<TableCell component="th" scope="row">
										{index + 1}
									</TableCell>
									<TableCell component="th" scope="row">
										<Link to={`/albums/${album.id}/details`} title="View album details">
											{album.album_title}
										</Link>
									</TableCell>
									<TableCell align="right">{album.nr_of_tracks}</TableCell>
									<TableCell align="right">{album.label}</TableCell>
									<TableCell align="right">{album.year_of_release}</TableCell>
									<TableCell align="right">{album.main_artist?.artist_name}</TableCell>
									<TableCell align="right">
										<IconButton
											component={Link}
											sx={{ mr: 3 }}
											to={`/albums/${album.id}/details`}>
											<Tooltip title="View album details" arrow>
												<ReadMoreIcon color="primary" />
											</Tooltip>
										</IconButton>
										<IconButton component={Link} sx={{ mr: 3 }} to={`/albums/${album.id}/edit`}>
											<EditIcon />
										</IconButton>
										<IconButton component={Link} sx={{ mr: 3 }} to={`/albums/${album.id}/delete`}>
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