import {createContext, type ReactNode, useCallback, useContext, useState} from "react";
import axios from "axios";
import {UserContext} from "./UserContext.tsx";
import type {IMovie} from "../interface/IMovie.ts";


// 创建 context
interface WatchlistContextType {
    watchlist: IMovie[];
    showWatchlist: boolean;
    handleShowWatchlist: () => void;  // 一定要写这里
    setShowWatchlist: React.Dispatch<React.SetStateAction<boolean>>;
}

const WatchlistContext = createContext<WatchlistContextType | null>(null);

interface WatchlistProviderProps {
    children: ReactNode;
}

// Provider 组件
export const WatchlistProvider= ({ children}: WatchlistProviderProps) => {
    const { user } = useContext(UserContext);
    const [watchlist, setWatchlist] = useState<IMovie[]>([]);
    const [showWatchlist, setShowWatchlist] = useState(false);

    const handleShowWatchlist = useCallback(() => {
        if (!user?.id) return;
        axios
            .get(`/api/watchlist/${user.id}`, { withCredentials: true })
            .then((response) => {
                setWatchlist(response.data);
                setShowWatchlist(true);
            })
            .catch((error) => console.error("Failed to fetch watchlist", error));
    }, [user?.id]);

    return (
        <WatchlistContext.Provider
            value={{
                watchlist,
                showWatchlist,
                handleShowWatchlist,
                setShowWatchlist,
            }}
        >
            {children}
        </WatchlistContext.Provider>
    );
};

// 自定义 hook
// 自定义 hook
export const useWatchlist = () => {
    const context = useContext(WatchlistContext);
    if (!context) throw new Error("useWatchlist must be used within WatchlistProvider");
    return context;
};