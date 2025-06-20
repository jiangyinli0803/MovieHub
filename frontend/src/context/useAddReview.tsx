
import axios from "axios";
import {useEffect, useState} from "react";
import type {IReviewResponse} from "../interface/IReviewResponse.ts";
import type {IReviewInput} from "../interface/IReviewInput.tsx";

export default function useAddReview() {
    const [review, setReview] = useState<IReviewResponse | null>(null);
    const [message, setMessage] = useState<string|null>(null);
    const [allReviews, setAllReviews] = useState<IReviewResponse[]>([]);

    const handleAddReview = async (tmdbId: number, reviewInput: IReviewInput) => {
        try {
            const response = await axios.post(
                "/api/review/add",
                { tmdbId, reviewInput },
                { withCredentials: true }
            );
            setReview(response.data);
            setMessage("Your review is successfully added.");
            return response.data; // ✅ 返回新评论（如果你想用）
        } catch (error) {
            setReview(null);
            setMessage("Add Review Error: " + error);
            return null;
        }
    };

    // 获取所有评论
    const fetchReviews = async (tmdbId: number) => {
        try {
            const res = await axios.get(`/api/review/${tmdbId}`);
            setAllReviews(res.data);
            return res.data;
        } catch (error) {
            console.error("Error fetching reviews:", error);
            return [];
        }
    };

    // 自动清除 message
    useEffect(() => {
        if (message) {
            const timer = setTimeout(() => {
                setMessage(""); // 清空消息
            }, 3000); // 3 秒后消失

            return () => clearTimeout(timer); // 清理旧定时器
        }
    }, [message]);

    return {handleAddReview, fetchReviews, review, allReviews, message};

}