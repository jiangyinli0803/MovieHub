import {useState} from "react";
import {useNavigate} from "react-router-dom";

export default function SearchButton(){
    const [query, setQuery] = useState("");
    const navigate = useNavigate();

    const handleSearch = () => {
        if (query.trim()) {
            navigate(`/search/${encodeURIComponent(query)}`);
        }
    };

    const handleEnter = (e: React.KeyboardEvent<HTMLInputElement>) => {
        if (e.key === "Enter") {
            e.preventDefault();
            handleSearch();
        }
    };

    return(
        <>
            <div  className="px-2 my-2 sticky top-0 z-40 bg-white">
            <div className="flex items-center gap-2 pl-2 rounded-full border-2 border-blue-400 shadow-md w-full focus-within:border-blue-800">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none"
                     viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor"
                     className="size-6 text-gray-500">
                    <path strokeLinecap="round" strokeLinejoin="round"
                          d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z"/>
                </svg>
                <input
                    name="query"
                    placeholder="Search by movie name or actors..."
                    className="w-full outline-none bg-transparent text-lg text-gray-700 placeholder-gray-400"
                    value={query}
                    onChange={e => setQuery(e.target.value)}
                    onKeyDown={handleEnter}
                />
                <button
                    onClick={handleSearch}
                    className="bg-dark hover:bg-blue-400 text-white text-lg px-4 py-1.5 rounded-full"
                >
                    Search
                </button>
            </div>
            </div>
        </>
    )
}