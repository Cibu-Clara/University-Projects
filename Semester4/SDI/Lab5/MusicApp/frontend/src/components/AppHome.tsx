import { CssBaseline, Container, Typography } from "@mui/material";
import React from "react";

export const AppHome = () => {
	return (
		<React.Fragment>
			<CssBaseline />
			<Container maxWidth="xl">
				<Typography variant="h1" component="h1" gutterBottom margin="100px">
					Welcome to music app! Use the menu above to navigate.
				</Typography>
			</Container>
		</React.Fragment>
	);
};