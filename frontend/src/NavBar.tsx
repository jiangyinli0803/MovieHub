import {Link, useNavigate} from "react-router-dom";
import {useContext, useState} from "react";
import AuthModal from "./components/AuthModal.tsx";
import {UserContext} from "./context/UserContext.tsx";
import axios from "axios";


export default function NavBar(){
    const [query, setQuery] = useState("");
    const navigate = useNavigate();
    const handleEnter = (e: React.KeyboardEvent<HTMLInputElement>) => {
        if (e.key === "Enter") {
            navigate(`/search/${encodeURIComponent(query)}`);
        }
    };

    const {user} = useContext(UserContext);
    const [showAuthModal, setShowAuthModal] = useState(false);
    const handleLogout = () => {
        const confirmLogout = window.confirm("Are you sure you want to log out?");

        if (!confirmLogout) return;

        axios.post('http://localhost:8080/logout', {}, { withCredentials: true })
            .then(() => {
                window.location.href = "http://localhost:5173";  // 登出成功后跳转
            })
            .catch(() => {
                alert('Logout failed');
            });
    };

    return(
        <>
        <div className="flex items-center w-screen space-x-8 bg-dark px-6 py-4 mb-6 h-20">
            <img src="/assets/movie-logo.PNG" alt="logo" className="h-16 w-auto" />
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

        {/* check if user logged in */}
            {user ? (
                <>
                <span className="text-red-600">Hello {user.name}!</span>
                <Link to={"/dashboard"} className="text-white font-medium">Dashboard</Link>
                    {/* Log out */}
                <button onClick={handleLogout}
                        className="absolute right-4 px-4 py-1.5 border-2 border-white font-medium text-white rounded-md hover:bg-white hover:text-dark transition"
                >Logout</button>
            </>) : (<>
                    {/* Log in */}
                <button onClick={() => setShowAuthModal(true)}
                        className="absolute right-4 px-4 py-1.5 border-2 border-white font-medium text-white rounded-md hover:bg-white hover:text-dark transition"
                >Login</button>
            </>)}

            {/* 通过onClose传给子组件关闭函数，来控制弹窗Modal关闭， 在AuthModal中点击button时，激活关闭函数 */}
            {showAuthModal && <AuthModal onClose={() => setShowAuthModal(false)} />}

        </div>
        </>
        )
}