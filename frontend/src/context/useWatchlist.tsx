import type {IUser} from "../interface/IUser.ts";
import axios from "axios";

export default function useWatchlist(user: IUser|null|undefined){
    const handleAdd = async (tmdbId: number) => {
        if (!user?.id) {
            alert("Please log in to add movies to your watchlist.");
            return;
        }

        try {
            const response = await axios.post(
                "/api/watchlist/add",
                { tmdbId, userId: user.id },
                {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem("authToken")}`
                    }
                }
            );
            alert(response.data);
        } catch (error) {
            if (axios.isAxiosError(error)) {
                const message = error.response?.data || "Add Movie Error";
                alert(`Add Movie Error: ${message} !`);
            } else {
                alert("Unexpected error!");
            }
        }
    };

    return { handleAdd };

}