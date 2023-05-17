import {Artist} from "./Artist";

export interface Song {
    id?: number;
    song_name: string;
    composer: string;
    genre: string;
    year_of_release: number;
    artists?: Artist[];
}