import './App.css'
import * as React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { AppHome } from "./components/AppHome";
import { AppMenu } from "./components/AppMenu";
import { ShowSongs } from "./components/songs/ShowSongs";
import { SongDetails } from "./components/songs/SongDetails";
import { SongStatistics } from "./components/songs/SongStatistics";
import { DeleteSong } from "./components/songs/DeleteSong";
import { AddSong } from "./components/songs/AddSong";
import {EditSong} from "./components/songs/EditSong";
import {FilterSongsByYear} from "./components/songs/FilterSongsByYear";
import {ShowArtists} from "./components/artists/ShowArtist";
import {ArtistDetails} from "./components/artists/ArtistDetails";
import {EditArtist} from "./components/artists/EditArtist";
import {DeleteArtist} from "./components/artists/DeleteArtist";
import {AddArtist} from "./components/artists/AddArtist";
import {ShowAlbums} from "./components/albums/ShowAlbums";
import {AddAlbum} from "./components/albums/AddAlbum";
import {EditAlbum} from "./components/albums/EditAlbum";
import {AlbumDetails} from "./components/albums/AlbumDetails";
import {DeleteAlbum} from "./components/albums/DeleteAlbum";
import {ShowPerformances} from "./components/performances/ShowPerformances";
import {PerformanceDetails} from "./components/performances/PerformanceDetails";
import {DeletePerformance} from "./components/performances/DeletePerformance";
import {AddPerformance} from "./components/performances/AddPerformance";
import {EditPerformance} from "./components/performances/EditPerformance";
import {Statistics} from "./components/Statistics";
import {ArtistsStatistics} from "./components/artists/ArtistsStatistics";

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

					<Route path="/artists" element={<ShowArtists />} />
					<Route path="/artists/:artistId/details" element={<ArtistDetails />} />
					<Route path="/artists/:artistId/edit" element={<EditArtist />} />
					<Route path="/artists/:artistId/delete" element={<DeleteArtist />} />
					<Route path="/artists/add" element={<AddArtist />} />

					<Route path="/albums" element={<ShowAlbums />} />
					<Route path="/albums/:albumId/details" element={<AlbumDetails />} />
					<Route path="/albums/:albumId/edit" element={<EditAlbum />} />
					<Route path="/albums/:albumId/delete" element={<DeleteAlbum />} />
					<Route path="/albums/add" element={<AddAlbum />} />

					<Route path="/performances" element={<ShowPerformances />} />
					<Route path="/performances/:performanceId/details" element={<PerformanceDetails />} />
					<Route path="/performances/:performanceId/edit" element={<EditPerformance />} />
					<Route path="/performances/:performanceId/delete" element={<DeletePerformance />} />
					<Route path="/performances/add" element={<AddPerformance />} />

					<Route path="/statistics" element={<Statistics />} />
					<Route path="/statistics/songs-ordered-by-performances" element={<SongStatistics />} />
					<Route path="/statistics/artists-ordered-by-avg-views" element={<ArtistsStatistics />} />
				</Routes>
			</Router>
		</React.Fragment>
	);
}

export default App;