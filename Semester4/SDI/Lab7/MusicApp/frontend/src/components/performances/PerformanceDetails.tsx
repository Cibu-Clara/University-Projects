import { Card, CardActions, CardContent, IconButton } from "@mui/material";
import { Container } from "@mui/system";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { BACKEND_API_URL } from "../../constants";
import { PerformsOn } from "../../models/PerformsOn";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";

export const PerformanceDetails = () => {
	const { performanceId } = useParams();
	const [performance, setPerformance] = useState<PerformsOn>();

	useEffect(() => {
		const fetchPerformances = async () => {
			const response = await fetch(`${BACKEND_API_URL}/performances/${performanceId}/`);
			const performance = await response.json();
			setPerformance(performance);
		};
		fetchPerformances();
	}, [performanceId]);

	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/performances`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<h1>Performance Details</h1>
					<p>Artist name: {performance?.artist?.artist_name}</p>
					<p>Song name: {performance?.song?.song_name}</p>
					<p>Number of views: {performance?.nr_of_views}</p>
					<p>Duration: {performance?.duration}</p>
				</CardContent>
				<CardActions>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/performances/${performanceId}/edit`}>
						<EditIcon />
					</IconButton>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/performances/${performanceId}/delete`}>
						<DeleteForeverIcon sx={{ color: "red" }} />
					</IconButton>
				</CardActions>
			</Card>
		</Container>
	);
};