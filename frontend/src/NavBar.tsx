import {Link} from "react-router-dom";

export default function NavBar(){

    return(
        <>
            <ul className="flex items-center w-screen space-x-8 bg-dark px-6 py-4 mb-6 h-20">
                <li><Link to={"/"} className="text-white font-medium">Home</Link></li>
                <li><Link to={"/movies"} className="text-white font-medium">Movie Galerie</Link></li>
            </ul>

        </>
    )
}