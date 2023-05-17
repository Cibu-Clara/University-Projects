import {Song} from "./Song";
import {Album} from "./Album";

export interface Artist {
    id?: number;
    artist_name: string;
    real_name: string;
    country: string;
    email: string;
    nr_albums?: number;
    songs?: Song[];
    albums? : Album[];
}