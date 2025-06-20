import {Link, useNavigate, useParams} from "react-router-dom";
import {useContext, useEffect, useState} from "react";
import type {IMovie} from "../interface/IMovie.ts";
import axios from "axios";
import {UserContext} from "../context/UserContext.tsx";
import useWatchlist from "../context/useWatchlist.tsx";

export default function MovieDetail(){
    const { id } = useParams<{ id: string }>();
    const [movie, setMovie] = useState<IMovie | null>(null);
    const [loading, setLoading] = useState(true);
    const{user}= useContext(UserContext);
    const{handleAdd}= useWatchlist(user)
    const navigate = useNavigate();

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
        <>
            <div className="p-8 max-w-3xl mx-auto">
                <h1 className="text-3xl font-bold mb-4">{movie.title}</h1>
                <img src={`https://image.tmdb.org/t/p/w500${movie.posterUrl}`} alt={movie.title}
                     className="rounded-lg mb-6 w-full h-96 object-cover"/>
                <hr className="border-t border-gray-300 my-6" />
                <p className="text-lg text-gray-700 mb-2">
                    <strong>Genre: </strong> {movie.category.join(' / ')}
                </p>
                <hr className="border-t border-gray-300 my-6" />
                <p className="text-lg text-gray-700 mb-2">
                    <strong>Rating: </strong> {movie.rating} ({movie.ratingCount} votes)
                </p>
                <hr className="border-t border-gray-300 my-6" />
                <p className="text-lg text-gray-700 mb-2">
                    <strong>Release Date: </strong> {movie.releaseDate}
                </p>
                <hr className="border-t border-gray-300 my-6" />
                <p className="text-lg text-gray-700 mb-2">
                    <strong>Language: </strong> {movie.language}
                </p>
                <hr className="border-t border-gray-300 my-6" />
                <p className="text-lg text-gray-700 mb-2">
                    <strong>Actors: </strong> {movie.actors.join("/")}
                </p>
                <hr className="border-t border-gray-300 my-6" />
                <p className="text-lg text-gray-700 mt-4">
                    <strong>Description: </strong>{movie.description}</p>

            </div>

            <div className="flex justify-center mb-8">
                <Link
                    to="/movies"
                    className="px-4 py-2 m-2 bg-brightblue hover:bg-blue-600 text-white font-semibold rounded-lg shadow-md transition"
                >
                    Back to Gallery
                </Link>
                <button onClick={(e) => {
                    e.preventDefault();
                    e.stopPropagation();
                    handleAdd(movie.tmdbId);
                }}
                        className="px-4 py-2 m-2 bg-brightblue hover:bg-blue-800 text-white font-semibold rounded-lg transition">
                    + Watchlist
                </button>
                <button onClick={(e) => {
                    e.preventDefault();
                    e.stopPropagation();
                    navigate(`/movies/${movie.tmdbId}/review`, {
                        state: {
                            movieTitle: movie?.title,
                            posterUrl: movie?.posterUrl,
                            tmdbRating: movie?.rating,
                            category: movie?.category
                        }
                    });
                }}
                        className="px-4 py-2 m-2 bg-brightblue hover:bg-blue-800 text-white font-semibold rounded-lg transition">
                    + Review
                </button>
            </div>
        </>
    );
}