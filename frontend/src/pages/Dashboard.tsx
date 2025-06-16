
import {useContext, useEffect} from "react";
import {UserContext} from "../context/UserContext.tsx";
import MovieWatchlistCard from "../components/MovieWatchlistCard.tsx";
import {useWatchlist} from "../context/WatchlistContext.tsx";

export default function Dashboard(){
    const{user} = useContext(UserContext);

    const { watchlist, showWatchlist, handleShowWatchlist } = useWatchlist();
    useEffect(() => {
        handleShowWatchlist(); // 页面加载时请求一次 watchlist
    }, []);

    return(
        <>
            <div className="h-40 bg-gray-200 w-full flex items-center space-x-10 pl-12  border border-dark">
                <img src={user?.avatarUrl} alt={user?.username}
                     className="w-32 h-32 rounded-full object-cover"/>
                <h2 className="text-blue-900 mt-2 text-4xl font-bold">{user?.username}</h2>
                <h1 className="ml-16 text-2xl font-semibold">Dashboard</h1>
            </div>
            <div className="flex">
                <aside className="w-64 h-screen bg-gray-200 text-dark p-6 border border-dark">
                    <ul className="space-y-4">
                        <li className="hover:bg-dark hover:text-white text-center font-bold text-lg py-4 rounded cursor-pointer">Profile</li>
                        <li onClick={handleShowWatchlist}
                            className="hover:bg-dark hover:text-white text-center font-bold text-lg py-4 rounded cursor-pointer">Your Watchlist</li>
                        <li className="hover:bg-dark hover:text-white text-center font-bold text-lg py-4 rounded cursor-pointer">Your Reviews</li>
                    </ul>
                </aside>

                <main className="flex-1 p-6">

                    {showWatchlist && (
                        <div className="mt-6">
                            <h2 className="text-4xl font-semibold mb-4">Your Watchlist</h2>
                            {Array.isArray(watchlist) && watchlist.length > 0 ? (
                                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                                    {watchlist.map((movie) => (
                                        <MovieWatchlistCard key={movie.id} movie={movie} />
                                    ))}
                                </div>
                            ) : (
                                <p className="text-center text-2xl mt-4 text-gray-500">No movies in your watchlist.</p>
                            )}
                        </div>)}
                </main>
            </div>
        </>
    )
}