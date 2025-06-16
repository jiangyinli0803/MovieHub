import {Link} from "react-router-dom";

export default function NeZha(){
    return(
        <>
            <h1 className="text-4xl font-bold m-6 text-center">Ne Zha 2</h1>
            <img src="/assets/nezha1.png" alt="Ne-Zha-2" className="w-full h-120 object-contain"/>

            <div className="mx-10">
                <hr className="border-t border-gray-300 my-4" />
            <p className="text-lg text-gray-700 mb-2">
                <strong>Genre: </strong> Animation/Fantasy/Action/Adventure
            </p>
            <hr className="border-t border-gray-300 my-6" />
            <p className="text-lg text-gray-700 mb-2">
                <strong>Rating: </strong> 8.5/10
            </p>
            <hr className="border-t border-gray-300 my-4" />
            <p className="text-lg text-gray-700 mb-2">
                <strong>Release Date: </strong> January 29, 2025 (China)
            </p>
                <hr className="border-t border-gray-300 my-6" />
            <p className="text-lg text-gray-700 mb-2">
                <strong>Language: </strong> Chinese/English
            </p>
                <hr className="border-t border-gray-300 my-6" />
            <p className="text-lg text-gray-700 mb-2">
                <strong>Director: </strong> Yu Yang
            </p>
                <hr className="border-t border-gray-300 my-6" />
            <p className="text-lg text-gray-700 mt-4">
                <strong>Storyline: </strong>After a great catastrophe, the souls of Nezha and Aobing are saved, but their bodies face ruin. To give them new life, Taiyi Zhenren turns to the mystical seven-colored lotus in a daring bid to rebuild them and change their fate.</p>
                <hr className="border-t border-gray-300 my-6" />
            <p className="text-lg text-gray-700 mb-2">
                <strong>Did You Know: </strong>
                Ne Zha 2 became the highest-grossing Chinese and non-English film of all time. It also became the first non-Hollywood production to join the billion dollar club.
            </p>
                <hr className="border-t border-gray-300 my-6" />
                <Link
                    to="/movies"
                    className="px-4 py-2 m-8 bg-blue-400 hover:bg-dark text-lg text-white font-semibold rounded-lg shadow-md transition"
                >
                    Back to Gallery
                </Link>
            </div>
        </>
    )
}