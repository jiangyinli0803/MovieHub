
import './App.css'
import {Route, Routes} from "react-router-dom";
import MovieGalerie from "./pages/MovieGalerie.tsx";
import NavBar from "./NavBar.tsx";
import HomePage from "./pages/HomePage.tsx";
import MovieDetail from "./pages/MovieDetail.tsx";
import CategoryPage from "./pages/CategoryPage.tsx";
import SearchBar from "./pages/SearchBar.tsx";
import {useEffect} from "react";
import Dashboard from "./pages/Dashboard.tsx";
import {UserProvider} from "./context/UserContext.tsx";
import Footer from "./components/footer.tsx";
import NeZha from "./pages/NeZha.tsx";
import {WatchlistProvider} from "./context/WatchlistContext.tsx";
import WatchlistPage from "./pages/WatchlistPage.tsx";
import UserReview from "./pages/UserReview.tsx";
import UserReviewDetail from "./pages/UserReviewDetail.tsx";
import axios from "axios";
import OAuthSuccess from "./pages/OAuthSuccess.tsx";


function App() {

    useEffect(() => {
        const token = localStorage.getItem("authToken");
        if (token) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
        }
    }, []);

  return (
      <>
        <UserProvider>
        <WatchlistProvider>

            <NavBar/>

    <div className="min-h-screen flex flex-col w-full overflow-x-hidden px-4">
      <Routes>
          <Route path={'/'} element={<HomePage/>}/>
          <Route path={'/oauth-success'} element={<OAuthSuccess/>} />
          <Route path={"/dashboard"} element={<Dashboard />} />
          <Route path={'/movies'} element={<MovieGalerie/>}/>
          <Route path="/movies/:id" element={<MovieDetail />} />
          <Route path="/category/:category" element={<CategoryPage />} />
          <Route path="/search/:query" element={<SearchBar/>}/>
          <Route path={'/movies/watchlist'} element={<WatchlistPage/>}/>
          <Route path="/movie/nezha2" element={<NeZha />}/>
          <Route path="/movies/:tmdbId/review" element={<UserReview />} />
          <Route path="/review/:tmdbId" element={<UserReviewDetail />} />
      </Routes>
    </div>
        </WatchlistProvider>
        </UserProvider>
         <Footer />
    </>
  )
}

export default App
