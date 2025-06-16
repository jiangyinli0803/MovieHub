import type {IMovie} from "../interface/IMovie.ts";
import {Link} from "react-router-dom";
import {useContext} from "react";
import {UserContext} from "../context/UserContext.tsx";
import axios from "axios";
import {useWatchlist} from "../context/WatchlistContext.tsx";

type Props = {
    movie: IMovie;
}

export default function MovieWatchlistCard({movie}: Readonly<Props>) {

    const fullImageUrl = `https://image.tmdb.org/t/p/w500${movie.posterUrl}`;
    const{user}= useContext(UserContext)
    const { handleShowWatchlist } = useWatchlist();

    function handleDelete(){
        axios.delete("/api/watchlist/delete",
            {data: {tmdbId: movie.tmdbId, userId: user?.id}, withCredentials: true}
         )
            .then(response => {alert(response.data); handleShowWatchlist(); })
            .catch(error => {
                alert("Delete Movie Error: " + error)
            })
    }

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
            <button onClick={handleDelete}
                    className="w-full bg-brightblue hover:bg-blue-800 text-white font-semibold py-2 px-4 rounded-lg transition">
                Remove from Watchlist
            </button>
        </div>
        </>
    )
}