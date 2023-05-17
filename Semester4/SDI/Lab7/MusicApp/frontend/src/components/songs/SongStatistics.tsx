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
import { SongsOrdNoPerf } from "../../models/SongsOrdNoPerf";
import { Paginator } from "../pagination/Pagination";

export const SongStatistics = () => {
    const[loading, setLoading] = useState(true);
    const[songs, setSongs] = useState([]);
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
          `${BACKEND_API_URL}/songs/order-by-performances/?page=${page}&page_size=${pageSize}`
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


    return (
    <Container>
        <h1 style={{marginTop:"65px"}}>All Songs ordered by the Number of Performances</h1>

        {loading && <CircularProgress />}
        {!loading && songs.length == 0 && <div>No songs found</div>}
        {!loading && songs.length > 0 && (
          <>
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 800 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
								<TableCell>#</TableCell>
                                <TableCell align="center">Song name</TableCell>
								<TableCell align="right">Composer</TableCell>
								<TableCell align="right">Genre</TableCell>
								<TableCell align="right">Year of release</TableCell>
								<TableCell align="center">No. of performances</TableCell>
							</TableRow>
                    </TableHead>
                    <TableBody>
                        {songs.map((song:SongsOrdNoPerf, index) => (
                            <TableRow key={song.id}>
                                <TableCell component="th" scope="row">
                                    {index + 1}
                                </TableCell>
                                <TableCell align="center">{song.song_name}</TableCell>
                                <TableCell align="center">{song.composer}</TableCell>
                                <TableCell align="center">{song.genre}</TableCell>
                                <TableCell align="center">{song.year_of_release}</TableCell>
                                <TableCell align="center">{song.no_of_performances}</TableCell>
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