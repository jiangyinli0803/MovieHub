import {useEffect, useState} from "react";
import type {IMovie} from "../interface/IMovie.ts";
import {useParams} from "react-router-dom";
import axios from "axios";
import MovieCard from "../components/MovieCard.tsx";

export default function SearchBar(){
    const [movies, setMovies] = useState<IMovie[]|null>(null);
    const {query} = useParams();

    useEffect(() => {
        if(!query) return;
        axios.get("/api/search", {params: {query}})
            .then(resp => {setMovies(resp.data); console.log("API response:", resp.data);})
            .catch(err => {
                console.error("Search error: ", err);
                setMovies([]);
            })
    }, [query]);

    return(
        <>
            {movies === null ? (
                <h2 className="text-xl font-semibold">Loading results for "{query}"...</h2>
            ) : movies.length === 0 ? (
                <h2 className="text-xl font-semibold">There is no movie that matches your search "{query}"</h2>
            ) : (
                <div className="grid grid-cols-4 gap-4">
                    {movies.map(m => (
                        <MovieCard key={m.id} movie={m} />
                    ))}
                </div>
            )}
        </>
    )
}