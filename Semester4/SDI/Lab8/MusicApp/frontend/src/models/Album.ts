import {Artist} from "./Artist";
import { User } from "./User";

export interface Album {
    id?: number;
    album_title: string;
    nr_of_tracks: number;
    label: string;
    year_of_release: number;
    main_artist?: Artist;
    main_artist_id?: number;
    added_by?: User;
    added_by_id?: number;
}