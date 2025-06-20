import {useEffect, useState} from "react";
import {Link, useLocation, useNavigate, useParams} from "react-router-dom";
import useAddReview from "../context/useAddReview.tsx";


export default function UserReview(){
    const {tmdbId} = useParams();   // 注意是 string，需要转成 number
    const location = useLocation();
    const movie = location.state;
    const navigate = useNavigate();

    const [rating, setRating] = useState<number>(0);
    const [title, setTitle] = useState<string>("");
    const [comment, setComment] = useState<string>("");
    const{handleAddReview, fetchReviews, message, allReviews} = useAddReview();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        const reviewInput = {
            rating,
            title,
            comment
        };
        await handleAddReview(Number(tmdbId), reviewInput);
        const allReviews = await fetchReviews(Number(tmdbId));
        navigate(`/review/${tmdbId}`, { state: {allReviews: allReviews}});

    };

    useEffect(() => {
        if (tmdbId) {
            fetchReviews(Number(tmdbId));
        }
    }, [tmdbId]);

    return(
        <>

            {/* 电影图片及原始评分 */}
            <h2 className="text-4xl font-bold text-center my-6 text-gray-800">Movie Reviews</h2>
            <div className="flex flex-row items-center justify-center gap-20 bg-white p-6 rounded shadow w-full ">
                <div className="h-[400px] w-[500px] overflow-hidden rounded border border-blue-300">
                <img
                    src={`https://image.tmdb.org/t/p/w500${movie.posterUrl}`}
                    alt={movie.movieTitle}
                    className="w-full h-auto object-cover object-top"
                /></div>
                <div className="flex flex-col items-center gap-6">
                    <p className="text-3xl font-bold text-gray-800">{movie.movieTitle}</p>
                    <p className="text-gray-500 text-2xl font-semibold">{movie.category.join("/")}</p>
                    <p className="text-gray-600 text-2xl font-semibold">TMDB Rating: <strong>{movie.tmdbRating}</strong></p>
                </div>
            </div>

            {/*--------------create a new Review----------------*/}

            <h2 className="text-2xl font-bold my-4 text-gray-800">Write your Review</h2>
            <form onSubmit={handleSubmit} className="w-full mx-auto bg-white p-6 rounded-lg shadow-md space-y-4">
                <div>
                    <label className="block text-gray-700 font-medium mb-1">
                        Rating:
                    </label>
                    <select
                        value={rating}
                        onChange={(e) => setRating(Number(e.target.value))}
                        className="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    >
                        <option value="" disabled>Select a rating</option>
                        {[...Array(11)].map((_, i) => (
                            <option key={i} value={i}>{i}</option>
                        ))}
                    </select>
                </div>

                <div>
                    <label className="block text-gray-700 font-medium mb-1">
                        Title:
                    </label>
                    <input
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        className="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                </div>

                <div>
                    <label className="block text-gray-700 font-medium mb-1">
                        Comment:
                    </label>
                    <textarea
                        value={comment}
                        onChange={(e) => setComment(e.target.value)}
                        rows={4}
                        className="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                </div>

                <div className="flex items-center justify-between pt-4">
                    <button
                        type="submit"
                        className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition"
                    >
                        Submit
                    </button>
                    <Link
                        to="/"
                        className="text-gray-500 hover:text-gray-800 transition underline"
                    >
                        Cancel
                    </Link>
                </div>
            </form>
            {message && <p className="text-red-600 text-center mt-4 transition-opacity duration-500">{message}</p>}

            {/*--------------show your Review----------------*/}
            <h2 className="text-2xl font-bold my-4">All Reviews for this movie</h2>
            {allReviews.length > 0 ? (
                allReviews
                    .sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime())
                    .map((review, index) => {
                    return (
                        <div key={index} className="border p-4 rounded shadow my-4">
                            <h3 className="text-lg font-semibold text-gray-800 mb-1">{review.title}</h3>
                            <div className="flex justify-between text-sm text-gray-600 mb-2">
                                <span>{review.username}</span>
                                <span>{review.rating} ⭐</span>
                                <span>{review.createdAt}</span>
                            </div>

                            <div className="border rounded-md bg-gray-50 p-3 text-gray-700">
                                {review.comment}
                            </div>
                        </div>
                    );
                })
            ) : (
                <p className="text-center text-gray-500">There is no reviews for this movie.</p>
            )}
    </>
    )
}