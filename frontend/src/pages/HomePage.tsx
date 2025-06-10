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
        <h1 className="text-2xl font-bold mb-4">Movie Categories</h1>
        <ul className="grid grid-cols-4 gap-1">
            {categories.map((category) => (
                <li key={category}>
                    <Link
                        to={`/category/${category.toLowerCase().replace(/\s/g, '-')}`}
                        className="block p-6 bg-brightblue rounded-2xl shadow hover:shadow-lg transition duration-300 text-lg text-center font-semibold text-white hover:bg-dark"
                    >
                        {category}
                    </Link>
                </li>
            ))}
        </ul>
    </>
    )
}