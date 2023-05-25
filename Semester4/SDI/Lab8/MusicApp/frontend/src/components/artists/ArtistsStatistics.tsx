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
} from "@mui/material";

import { useEffect, useState } from "react";
import { BACKEND_API_URL } from "../../constants";
import { ArtistsOrdAvgViews } from "../../models/ArtistsOrdAvgViews";
import { Paginator } from "../pagination/Pagination";

export const ArtistsStatistics = () => {
    const[loading, setLoading] = useState(true);
    const[artists, setArtists] = useState([]);
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
          `${BACKEND_API_URL}/artists/order-by-views/?page=${page}&page_size=${pageSize}`
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


    return (
    <Container>
        <h1 style={{marginTop:"65px"}}>All Artists ordered by the average number of their total views</h1>

        {loading && <CircularProgress />}
        {!loading && artists.length == 0 && <div>No artists found</div>}
        {!loading && artists.length > 0 && (
          <>
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 800 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
								<TableCell>#</TableCell>
                                <TableCell align="center">Artist name</TableCell>
								<TableCell align="right">Real name</TableCell>
								<TableCell align="right">Country</TableCell>
								<TableCell align="right">Email</TableCell>
								<TableCell align="center">Avg views</TableCell>
							</TableRow>
                    </TableHead>
                    <TableBody>
                        {artists.map((artist:ArtistsOrdAvgViews, index) => (
                            <TableRow key={artist.id}>
                                <TableCell component="th" scope="row">
                                    {index + 1}
                                </TableCell>
                                <TableCell align="center">{artist.artist_name}</TableCell>
                                <TableCell align="right">{artist.real_name}</TableCell>
                                <TableCell align="right">{artist.country}</TableCell>
                                <TableCell align="right">{artist.email}</TableCell>
                                <TableCell align="center">{artist.avg_views}</TableCell>
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