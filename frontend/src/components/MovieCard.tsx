import type {IMovie} from "../interface/IMovie.ts";
import {Link} from "react-router-dom";
import {useContext} from "react";
import {UserContext} from "../context/UserContext.tsx";
import useWatchlist from "../context/useWatchlist.tsx";


export default function MovieCard(prop: {movie: IMovie}) {
    const movie = prop.movie;
    const fullImageUrl = `https://image.tmdb.org/t/p/w500${movie.posterUrl}`;
    const{user}= useContext(UserContext)
    const{handleAdd}= useWatchlist(user)

    return (
        <>
            <div  className="flex flex-col w-full max-w-sm rounded-2xl bg-gray-200 transition hover:shadow-xl hover:scale-[1.02]">
            <Link to={`/movies/${movie.id}`} className="block h-full">
            <div className="h-full flex flex-col justify-between overflow-hidden shadow-lg bg-white dark:bg-gray-800">
                <img
                    src={fullImageUrl}
                    alt={movie.title}
                    className="w-full h-64 object-cover rounded-lg mb-2"
                />
                <div className="flex-1 flex flex-col space-y-2 px-4 pb-1 overflow-hidden">
                <p className="text-xl font-semibold text-gray-800 dark:text-white">
                    {movie.title}
                </p>
                <p className="text-sm text-gray-500 dark:text-gray-300">
                    {movie.category.join('/')}
                </p>
                <p className="text-sm text-gray-700 dark:text-gray-200">
                    Rating: <span className="font-medium">{movie.rating}</span> ({movie.ratingCount})
                </p>
                </div>
            </div>
            </Link>
            <button onClick={(e) => {
                e.preventDefault();      // 防止默认行为
                e.stopPropagation();     // 阻止冒泡触发 Link
                handleAdd(movie.tmdbId);
            }}
                    className="w-full bg-brightblue hover:bg-blue-800 text-white font-semibold py-2 px-4 rounded-lg transition">
                + Watchlist
            </button>
            </div>
        </>
    )
}