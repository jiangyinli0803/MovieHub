
import './App.css'
import {Route, Routes} from "react-router-dom";
import MovieGalerie from "./pages/MovieGalerie.tsx";
import NavBar from "./NavBar.tsx";
import HomePage from "./pages/HomePage.tsx";
import MovieDetail from "./pages/MovieDetail.tsx";
import CategoryPage from "./pages/CategoryPage.tsx";
import SearchBar from "./pages/SearchBar.tsx";
import ProtectedRoute from "./components/ProtectedRoute.tsx";
import {useEffect, useState} from "react";
import axios from "axios";
import Dashboard from "./pages/Dashboard.tsx";
import {UserContext} from "./context/UserContext.tsx";
import type {IUser} from "./interface/IUser.ts";
import Footer from "./components/footer.tsx";
import NeZha from "./pages/NeZha.tsx";
import {WatchlistProvider} from "./context/WatchlistContext.tsx";
import WatchlistPage from "./pages/WatchlistPage.tsx";



function App() {
    const [user, setUser] = useState<IUser|null|undefined>();
    const loadUser = () => {
        axios.get("/api/auth", { withCredentials: true })
            .then(response => {setUser(response.data);
                console.log(user?.username)
            })
            .catch(() => setUser(null))
    }
    useEffect(() => {
        loadUser()
    }, []);

  return (
    <>

    <UserContext.Provider value={{user, setUser, loadUser}}>
        <WatchlistProvider>
        <NavBar/>

    <div className="min-h-screen flex flex-col w-full overflow-x-hidden px-4">
      <Routes>
          <Route path={'/'} element={<HomePage/>}/>
          <Route element={<ProtectedRoute user={user} /> }>
              <Route path={"/dashboard"} element={<Dashboard />} />
          </Route>
          <Route path={'/movies'} element={<MovieGalerie/>}/>
          <Route path="/movies/:id" element={<MovieDetail />} />
          <Route path="/category/:category" element={<CategoryPage />} />
          <Route path="/search/:query" element={<SearchBar/>}/>
          <Route path={'/movies/watchlist'} element={<WatchlistPage/>}/>
          <Route path="/movie/nezha2" element={<NeZha />}/>
      </Routes>
    </div>
        </WatchlistProvider>
    </UserContext.Provider>
         <Footer />
    </>
  )
}

export default App
