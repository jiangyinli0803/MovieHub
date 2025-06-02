import {useEffect, useState} from "react";
import type {IMovie} from "../interface/IMovie.ts";
import axios from "axios";
import MovieCard from "../components/MovieCard.tsx";
import Pagination from "../components/Pagination.tsx";

export default function MovieGalerie(){
    const[movies, setMovies] = useState<IMovie[]>([]);
    useEffect(() => {
        axios.get('/api/movies')
            .then(response => setMovies(response.data))
            .catch(error => console.log("Fail to fetch movies", error));
    },[]);

    //Pagination
    const [currentPage, setCurrentPage] = useState(1);
    const moviesPerPage = 20;

    const indexOfLastMovie = currentPage *  moviesPerPage;
    const indexOfFirstMovie = (currentPage - 1 )*  moviesPerPage;
    const currentMovies = movies.slice(indexOfFirstMovie, indexOfLastMovie);
    const totalPages = Math.ceil(movies.length / moviesPerPage);

    /*const pageButtons = [];
    for (let i = 1; i <= numOfPages; i++) {
        pageButtons.push(
            <button
                key={i}
                onClick={() => setCurrentPage(i)}
                className={`px-4 py-2 mx-2 rounded-lg border text-sm font-medium transition
                ${currentPage === i
                    ? "bg-brightblue text-white cursor-default"
                    : "bg-white text-gray-700 hover:bg-blue-100 border-gray-300"}                
                `}
            >
                {i}
            </button>
        );
    }*/



    return(
        <>
            <div className="p-6">
                {currentMovies.length === 0 ? (
                    <h2 className="text-center text-xl">Loading...</h2>
                ) : (
                    <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
                        {currentMovies.map(m => (
                            <MovieCard key={m.id} movie={m} />
                        ))}
                    </div>
                )}
            </div>
            <div className="text-center">
                <Pagination
                    currentPage={currentPage}
                    totalPages={totalPages}
                    onPageChange={setCurrentPage}
                />
            </div>
        </>
        )

}