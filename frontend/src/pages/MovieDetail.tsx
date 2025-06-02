import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import type {IMovie} from "../interface/IMovie.ts";
import axios from "axios";

export default function MovieDetail(){
    const { id } = useParams();
    const [movie, setMovie] = useState<IMovie | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        axios.get(`/api/movies/${id}`)
            .then(res => {
                setMovie(res.data);
                setLoading(false);
            })
            .catch(err => {
                console.error("Error fetching movie:", err);
                setLoading(false);
            });
    }, [id]);

    if (loading) return <h2 className="text-center">Loading...</h2>;
    if (!movie) return <h2 className="text-center">Movie not found.</h2>;

    return (
        <div className="p-8 max-w-3xl mx-auto">
            <h1 className="text-3xl font-bold mb-4">{movie.title}</h1>
            <img src={movie.posterUrl} alt={movie.title} className="rounded-lg mb-6 w-full h-96 object-cover" />
            <p className="text-lg text-gray-700 mb-2">
                <strong>Category:</strong> {movie.category.join(' / ')}
            </p>
            <p className="text-lg text-gray-700 mb-2">
                <strong>Rating:</strong> {movie.rating} ({movie.ratingCount} votes)
            </p>
            <p className="text-md text-gray-600 mt-4">{movie.description}</p>
        </div>
    );
}