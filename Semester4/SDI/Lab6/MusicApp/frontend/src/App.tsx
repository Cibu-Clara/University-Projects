import './App.css'
import * as React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { AppHome } from "./components/AppHome";
import { AppMenu } from "./components/AppMenu";
import { ShowSongs } from "./components/songs/ShowSongs";
import { SongDetails } from "./components/songs/SongDetails";
import { DeleteSong } from "./components/songs/DeleteSong";
import { AddSong } from "./components/songs/AddSong";
import {EditSong} from "./components/songs/EditSong";
import {FilterSongsByYear} from "./components/songs/FilterSongsByYear";

function App() {
	return (
		<React.Fragment>
			<Router>
				<AppMenu />
				<Routes>
					<Route path="/" element={<AppHome />} />
					<Route path="/songs" element={<ShowSongs />} />
					<Route path="/songs/:songId/details" element={<SongDetails />} />
					<Route path="/songs/:songId/edit" element={<EditSong />} />
					<Route path="/songs/:songId/delete" element={<DeleteSong />} />
					<Route path="/songs/add" element={<AddSong />} />
					<Route path="/songs/filter-by-year/:year" element={<FilterSongsByYear />} />
				</Routes>
			</Router>
		</React.Fragment>
	);
}

export default App;