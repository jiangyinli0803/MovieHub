
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


function App() {
    const [user, setUser] = useState<IUser|null|undefined>();
    const loadUser = () => {
        axios.get("/api/auth")
            .then(response => setUser(response.data))
            .catch(() => setUser(null))
    }
    useEffect(() => {
        loadUser()
    }, []);

  return (
    <>
    <UserContext value={{user}}>
      <NavBar/>
      <Routes>
          <Route path={'/'} element={<HomePage/>}/>
          <Route element={<ProtectedRoute user={user} /> }>
              <Route path={"/dashboard"} element={<Dashboard user={user}/>} />
          </Route>
          <Route path={'/movies'} element={<MovieGalerie/>}/>
          <Route path="/movies/:id" element={<MovieDetail />} />
          <Route path="/category/:category" element={<CategoryPage />} />
          <Route path="/search/:query" element={<SearchBar/>}/>
      </Routes>
    </UserContext>
    </>
  )
}

export default App
