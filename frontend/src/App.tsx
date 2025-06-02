
import './App.css'
import {Route, Routes} from "react-router-dom";
import MovieGalerie from "./pages/MovieGalerie.tsx";
import NavBar from "./NavBar.tsx";
import HomePage from "./pages/HomePage.tsx";
import MovieDetail from "./pages/MovieDetail.tsx";

function App() {


  return (
    <>
      <NavBar/>
      <Routes>
          <Route path={'/'} element={<HomePage/>}/>
          <Route path={'/movies'} element={<MovieGalerie/>}/>
          <Route path="/movies/:id" element={<MovieDetail />} />
      </Routes>

    </>
  )
}

export default App
