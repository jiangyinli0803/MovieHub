import {Link} from "react-router-dom";
import {useContext, useState} from "react";
import AuthModal from "./components/AuthModal.tsx";
import {UserContext} from "./context/UserContext.tsx";
import axios from "axios";
import SearchButton from "./components/SearchButton.tsx";


export default function NavBar(){
    const {user, setUser} = useContext(UserContext);
    const [showAuthModal, setShowAuthModal] = useState(false);
    const handleLogout = () => {
        const confirmLogout = window.confirm("Are you sure you want to log out?");

        if (!confirmLogout) return;

        axios.post('http://localhost:8080/logout', {})
            .then(() => {
                localStorage.removeItem("authToken");
               // delete axios.defaults.headers.common['Authorization'];
                setUser(null);
                window.location.href = "http://localhost:5173";  // 登出成功后跳转
            })
            .catch(() => {
                alert('Logout failed');
            });
    };

    const handleWatchlistClick = (e: React.MouseEvent<HTMLAnchorElement>) => {
        if (!user|| user.username == undefined || user.username == "Unknown user") {
            e.preventDefault(); // 阻止跳转
            alert("Please log in to view your watchlist.");
        }
        // 如果 user 存在则正常跳转，不需要额外操作
    };

    return(
        <>
        <div className="flex items-center w-screen space-x-8 bg-dark px-6 py-4 h-30">
            <Link to={"/"}><img src="/assets/movie-logo.PNG" alt="logo" className="h-24 w-auto" /></Link>
            <Link to={"/"} className="text-white font-medium text-lg hover:text-red-600">Home</Link>
            <Link to={"/movies"} className="text-white font-medium text-lg hover:text-red-600">Movie Gallery</Link>
            <Link to={"/movies/watchlist"}  onClick={handleWatchlistClick} className="text-white font-medium text-lg hover:text-red-600">Watchlist</Link>
            <Link to={"/movies/reviews"} className="text-white font-medium text-lg hover:text-red-600">User Reviews</Link>

        {/* check if user logged in */}
            {(!user || !user.username || user.username === "Unknown user") ? (<>
                {/* Log in */}
                <button onClick={() => setShowAuthModal(true)}
                        className="absolute right-4 px-4 py-1.5 border-2 border-white text-lg font-medium text-white rounded-md hover:bg-white hover:text-dark transition"
                >Login</button>
            </>): (
                <>
                    <span className="absolute right-52 text-lg text-red-600">Hello {user.username}!</span>
                    <Link to={"/dashboard"} className="absolute right-20 px-3 py-1.5 border-2 border-white text-lg font-medium text-white rounded-md hover:bg-white hover:text-dark transition">Dashboard</Link>
                    {/* Log out */}
                    <button onClick={handleLogout}
                            className="absolute right-4 px-3 py-1.5 border-2 border-white text-lg font-medium text-white rounded-md hover:bg-white hover:text-dark transition"
                    >Logout</button>
                </>) }

            {/* 通过onClose传给子组件关闭函数，来控制弹窗Modal关闭， 在AuthModal中点击button时，激活关闭函数 */}
            {showAuthModal && <AuthModal onClose={() => setShowAuthModal(false)} />}

        </div>

            {/*-------------*Navbar------------------------------------------ */}
            <SearchButton />
        </>
        )
}