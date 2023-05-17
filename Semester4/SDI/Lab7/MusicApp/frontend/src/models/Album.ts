import {Artist} from "./Artist";

export interface Album {
    id?: number;
    album_title: string;
    nr_of_tracks: number;
    label: string;
    year_of_release: number;
    main_artist?: Artist;
    main_artist_id?: number;
}