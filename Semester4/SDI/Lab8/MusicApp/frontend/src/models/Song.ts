import {Artist} from "./Artist";
import { User } from "./User";

export interface Song {
    id?: number;
    song_name: string;
    composer: string;
    genre: string;
    year_of_release: number;
    artists?: Artist[];
    added_by?: User;
    added_by_id?: number;
}