export interface IMovie{
    id: string;
    tmdbId: number;
    title: string;
    language: string;
    rating: number;
    ratingCount: number;
    releaseDate: string;  // "yyyy-MM-dd"
    actors: string[];
    posterUrl: string;
    description: string;
    category: string[];
    created_at: string;  //"2025-05-28T12:00:00"
}