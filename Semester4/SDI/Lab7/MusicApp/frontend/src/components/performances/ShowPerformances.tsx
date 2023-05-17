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
import {PerformsOn} from "../../models/PerformsOn";
import {BACKEND_API_URL} from "../../constants";
import AddIcon from "@mui/icons-material/Add";
import ReadMoreIcon from "@mui/icons-material/ReadMore";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import { Paginator } from "../pagination/Pagination";

export const ShowPerformances = () => {
    const [loading, setLoading] = useState(false);
    const [performances, setPerformances] = useState<PerformsOn[]>([]);
    // const [order, setOrder] = useState("asc");
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

    const fetchPerformances = async () => {
        setLoading(true);
        const response = await fetch(
          `${BACKEND_API_URL}/performances/?page=${page}&page_size=${pageSize}`
        );
        const { count, next, results } = await response.json();
        setPerformances(results);
        setTotalRows(count);
        setIsLastPage(!next);
        setLoading(false);
      };

      useEffect(() => {
        fetchPerformances();
      }, [page]);

/*    const sorting = () => {
        if (order === "asc") {
            const sorted = [...performances].sort((p1, p2) =>
                    p1.artist?.artist_name.toLowerCase() > p2.artist?.artist_name.toLowerCase() ? 1 : -1);
            setPerformances(sorted);
            setOrder("desc");
        }
        if (order === "desc") {
            const sorted = [...performances].sort((p1, p2) =>
                p1.artist?.artist_name.toLowerCase() < p2.artist?.artist_name.toLowerCase() ? 1 : -1);
            setPerformances(sorted);
            setOrder("asc");
        }
    }*/

        return (
		<Container>
			<h1>All Performances </h1>
			{loading && <CircularProgress />}
			{!loading && performances.length === 0 && <p>No performances found</p>}
			{!loading && (
				<IconButton component={Link} sx={{ mr:155 }} to={`/performances/add`}>
					<Tooltip title="Add a new performance" arrow>
						<AddIcon color="primary" />
					</Tooltip>
				</IconButton>
			)}
			{!loading && performances.length > 0 && (
                <>
				<TableContainer component={Paper}>
					<Table sx={{ minWidth: 650 }} aria-label="simple table">
						<TableHead>
							<TableRow>
								<TableCell>#</TableCell>
                                <TableCell align="center">Artist name</TableCell>
								<TableCell align="right">Song name</TableCell>
								<TableCell align="right">Number of views</TableCell>
								<TableCell align="right">Duration</TableCell>
								<TableCell align="center">Operations</TableCell>
							</TableRow>
						</TableHead>
						<TableBody>
                            {performances.map((performance, index) => (
								<TableRow key={performance.id}>
									<TableCell component="th" scope="row">
										{index + 1}
									</TableCell>
									<TableCell component="th" scope="row">
										<Link to={`/performances/${performance.id}/details`} title="View performance details">
											{performance.artist?.artist_name}
										</Link>
									</TableCell>
									<TableCell align="center">{performance.song?.song_name}</TableCell>
									<TableCell align="right">{performance.nr_of_views}</TableCell>
									<TableCell align="right">{performance.duration}</TableCell>
									<TableCell align="right">
										<IconButton
											component={Link}
											sx={{ mr: 3 }}
											to={`/performances/${performance.id}/details`}>
											<Tooltip title="View performance details" arrow>
												<ReadMoreIcon color="primary" />
											</Tooltip>
										</IconButton>
										<IconButton component={Link} sx={{ mr: 3 }} to={`/performances/${performance.id}/edit`}>
											<EditIcon />
										</IconButton>
										<IconButton component={Link} sx={{ mr: 3 }} to={`/performances/${performance.id}/delete`}>
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