import {useLocation, useNavigate} from "react-router-dom";
import type {IReviewResponse} from "../interface/IReviewResponse.ts";

// 在UserReview Page中通过Naviage state出入了all Reviews数据

export default function UserReviewDetail(){
    const location = useLocation();
    const navigate = useNavigate();
    const reviews: IReviewResponse[] = location.state?.allReviews;

    // 防御性检查：如果 reviews 不存在，说明用户直接刷新或手动访问了该页面
    if (!reviews) {
        return (
            <div className="text-center mt-10 text-gray-600">
                <p>Oops! No review data found.</p>
                <button
                    onClick={() => navigate(-1)}
                    className="mt-4 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
                >
                    Go Back
                </button>
            </div>
        );
    }
// 如果没有评论，直接返回提示
    if (reviews.length === 0) {
        return <p className="text-center text-gray-500">There are no reviews for this movie.</p>;
    }

    // 从第一条评论中提取电影信息（因为每条评论都带了电影数据）
    const { posterUrl, movieTitle, tmdbRating } = reviews[0];

    return (
        <div className="w-full mx-auto flex flex-col gap-8 p-8">
            <h2 className="text-4xl font-bold mb-6 text-gray-800">Reviews of this movie</h2>
            {/* 页首：电影信息 */}

                <div className="flex flex-row items-center justify-center gap-20 border border-blue-200 shadow rounded-lg py-2">
                    <div className="h-[400px] w-[500px] overflow-hidden rounded border border-gray-300">
                        <img
                            src={`https://image.tmdb.org/t/p/w500${posterUrl}`}
                            alt={movieTitle}
                            className="w-full h-auto object-cover object-top"
                        />
                    </div>
                    <div className="flex flex-col justify-between">
                        <p className="text-4xl font-bold text-gray-800">{movieTitle}</p>
                        <p className="text-gray-600 text-2xl font-bold mt-6">TMDB Rating: {tmdbRating}</p>
                    </div>
                </div>


            {/* 评论列表 */}
            <div className="flex flex-col gap-6 border border-blue-200 shadow rounded-lg p-2">
                {reviews
                    .sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime())
                    .map((review: IReviewResponse, index: number) => (
                    <div key={index} className="border-none rounded-lg shadow p-4 bg-gray-200 w-full mx-auto">
                        <div className="flex justify-between gap-4 mb-2">
                            <div className="flex flex-row gap-2">
                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="size-6 mr-2">
                                    <path fillRule="evenodd" d="M7.5 6a4.5 4.5 0 1 1 9 0 4.5 4.5 0 0 1-9 0ZM3.751 20.105a8.25 8.25 0 0 1 16.498 0 .75.75 0 0 1-.437.695A18.683 18.683 0 0 1 12 22.5c-2.786 0-5.433-.608-7.812-1.7a.75.75 0 0 1-.437-.695Z" clipRule="evenodd" />
                                </svg>
                                <span className="text-dark font-semibold">{review.username}</span>
                            </div>
                          <span  className="text-dark font-semibold">{review.createdAt}</span>
                        </div>
                        <p>⭐&nbsp;&nbsp;&nbsp;&nbsp;{review.rating} / 10 </p>

                        <div className="flex flex-row items-start gap-2 mt-4">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6">
                            <path strokeLinecap="round" strokeLinejoin="round" d="M8.625 12a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm0 0H8.25m4.125 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm0 0H12m4.125 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm0 0h-.375M21 12c0 4.556-4.03 8.25-9 8.25a9.764 9.764 0 0 1-2.555-.337A5.972 5.972 0 0 1 5.41 20.97a5.969 5.969 0 0 1-.474-.065 4.48 4.48 0 0 0 .978-2.025c.09-.457-.133-.901-.467-1.226C3.93 16.178 3 14.189 3 12c0-4.556 4.03-8.25 9-8.25s9 3.694 9 8.25Z" />
                        </svg>
                            <div className="rounded-md bg-gray-300 p-2 pb-6 text-gray-700 w-full">
                            <p className="text-2xl font-semibold text-gray-800 mb-1">{review.title}</p>
                            <p>{review.comment}</p>
                            </div>
                        </div>
                    </div>

                ))}
            </div>
        </div>
    );
}