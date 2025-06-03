import {Link, useNavigate} from "react-router-dom";
import {useState} from "react";

export default function NavBar(){
    const [query, setQuery] = useState("");
    const navigate = useNavigate();
    const handleEnter = (e: React.KeyboardEvent<HTMLInputElement>) => {
        if (e.key === "Enter") {
            navigate(`/search/${encodeURIComponent(query)}`);
        }
    };

    return(
        <>
            <div className="flex items-center w-screen space-x-8 bg-dark px-6 py-4 mb-6 h-20">
            <Link to={"/"} className="text-white font-medium">Home</Link>
            <Link to={"/movies"} className="text-white font-medium">Movie Galerie</Link>

            <div className="flex items-center gap-2 p-2 bg-white rounded-full shadow w-full max-w-md">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6">
                    <path strokeLinecap="round" strokeLinejoin="round" d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z" />
                </svg>
                <input name="query" placeholder="Search all movies..."
                       className="w-full outline-none bg-transparent text-gray-700 placeholder-gray-400"
                       value={query}
                       onChange={e => setQuery(e.target.value)}
                       onKeyDown={handleEnter}/>
            </div>
            </div>

        </>
    )
}