import {useWatchlist} from "../context/WatchlistContext.tsx";
import MovieWatchlistCard from "../components/MovieWatchlistCard.tsx";
import {useEffect} from "react";

export default function WatchlistPage(){
    const { watchlist, handleShowWatchlist } = useWatchlist();

    useEffect(() => {
        handleShowWatchlist();
    }, [handleShowWatchlist]); // 页面加载时拉取数据

    return (
        <div>
            <h2 className="text-4xl font-bold px-4 mt-10 mb-6">Your Watchlist</h2>
            {Array.isArray(watchlist) && watchlist.length > 0 ? (
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                    {watchlist.map((movie) => (
                        <MovieWatchlistCard key={movie.id} movie={movie} />
                    ))}
                </div>
            ) : (
                <p className="text-center text-2xl mt-4 text-gray-500">No movies in your watchlist.</p>
            )}

        </div>
    );
}