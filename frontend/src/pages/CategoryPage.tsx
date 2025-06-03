import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import type {IMovie} from "../interface/IMovie.ts";
import MovieCard from "../components/MovieCard.tsx";

export default function CategoryPage() {
    const { category } = useParams(); // 获取 URL 中的分类
    const [movies, setMovies] = useState([]);

    useEffect(() => {
        if (!category) return;
        const formattedCategory = category.replace(/-/g, " ");
        axios.get(`/api/movies/category/${formattedCategory}`)
            .then(res => setMovies(res.data))
            .catch(err => console.error("Failed to fetch movies:", err));
    }, [category]);

    return (
        <div>
            <h1 className="text-2xl font-bold mb-4">Category: {category?.replace(/-/g, " ")}</h1>
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
                {movies.map((m: IMovie) => (
                    <MovieCard key={m.id} movie={m} />
                ))}
            </div>
        </div>
    );
}