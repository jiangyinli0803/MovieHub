import type {IMovie} from "../interface/IMovie.ts";
import {Link} from "react-router-dom";


export default function MovieCard(prop: {movie: IMovie}) {
    const movie = prop.movie;
    const fullImageUrl = `https://image.tmdb.org/t/p/w500${movie.posterUrl}`;

    return (
        <>
            <Link to={`/movies/${movie.id}`} className="block">
            <div className="flex flex-col h-full rounded-2xl overflow-hidden shadow-lg bg-white dark:bg-gray-800 p-4 transition hover:shadow-xl hover:scale-[1.02]">
                <img
                    src={fullImageUrl}
                    alt={movie.title}
                    className="w-full h-64 object-cover rounded-lg mb-2"
                />
                <div className="flex-1">
                <p className="text-xl font-semibold text-gray-800 dark:text-white mb-1">
                    {movie.title}
                </p>
                <p className="text-sm text-gray-500 dark:text-gray-300 mb-2">
                    {movie.category.join('/')}
                </p>
                <p className="text-sm text-gray-700 dark:text-gray-200 mb-4">
                    Rating: <span className="font-medium">{movie.rating}</span> ({movie.ratingCount})
                </p>
                </div>
                <button className="w-full bg-brightblue hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded-lg transition">
                    + Watchlist
                </button>
            </div>
            </Link>

        </>
    )
}