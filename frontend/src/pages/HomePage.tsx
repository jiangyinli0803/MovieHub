import {Link} from "react-router-dom";


export default function HomePage() {
    const categories: string[] = [
        "Action",
        "Adventure",
        "Animation",
        "Comedy",
        "Family",
        "Documentary",
        "Romance",
        "Science Fiction",
        "Horror",
        "Crime",
        "Fantasy",
        "War"
    ];
    return(
    <>
    {/*-------------------------Welcome Banner------------------*/}
    <div className="relative w-full">
         <img src="/assets/home-banner.jpg" alt="welcome" className="w-full h-80 object-cover"/>
        <div className="absolute inset-0 flex flex-col items-center justify-center bg-black/40">
        <p className="font-bold text-4xl text-white">Welcome to JAVANGELS MOVIES</p>
        <p className="text-2xl text-white">Every movie tells a story. Explore now</p>
        </div>
    </div>
   {/*-------------------------Movie Categories------------------*/}
        <h2 className="text-4xl font-bold px-4 mt-10 mb-6">Movie Categories</h2>
        <ul className="grid grid-cols-4 gap-1 px-2 w-full">
            {categories.map((category) => (
                <li key={category}>
                    <Link
                        to={`/category/${category.toLowerCase().replace(/\s/g, '-')}`}
                        className="block p-6 bg-blue-400 rounded-2xl shadow hover:shadow-lg transition duration-300 text-2xl text-center font-semibold text-white hover:bg-dark"
                    >
                        {category}
                    </Link>
                </li>
            ))}
        </ul>

   {/*-------------------------Hot Movie ------------------*/}
        <h2 className="text-4xl font-bold px-4 mt-10 mb-6">Popular Movies</h2>
        <div className="flex flex-row items-center space-x-10 border border-dark mx-4 rounded-lg">
            <div className="flex-1 max-w-3xl aspect-video">
                <iframe
                    src="https://www.youtube.com/embed/gsiAYjyiIBM"
                    title="Trailer"
                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                    allowFullScreen
                    className="w-full h-full rounded-lg shadow-lg"
                ></iframe>
            </div>

        <div>
            <p className="text-3xl font-bold mb-4">Ne Zha 2</p>
            <p className="text-lg text-gray-700 mb-2">
                <strong>Genre: </strong>Animation/Fantasy/Action/Adventure
            </p>
            <p className="text-lg text-gray-700 mb-2">
                <strong>Release Date: </strong> January 29, 2025(China)
            </p>
            <p className="text-lg text-gray-700 mb-2">
                <strong>Rating: </strong> 8.5/10
            </p>
            <p className="text-lg text-gray-700 mb-2">
                <strong>Language: </strong> Chinese/English
            </p>
            <button className="bg-blue-400 text-white text-lg font-semibold hover:bg-dark p-4 m-8 rounded-lg cursor">
            <Link to={"/movie/nezha2"}>
                More Details </Link></button>

        </div>
    </div>

    </>
    )
}