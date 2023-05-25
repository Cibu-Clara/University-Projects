import {Song} from "./Song";
import {Album} from "./Album";
import { User } from "./User";

export interface Artist {
    id?: number;
    artist_name: string;
    real_name: string;
    country: string;
    email: string;
    nr_albums?: number;
    songs?: Song[];
    albums? : Album[];
    added_by?: User;
    added_by_id?: number;
}