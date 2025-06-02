import type { JSX } from "react";

type Props ={
    currentPage: number;
    totalPages: number;
    onPageChange: (page: number)=>void
}
export default function Pagination({ currentPage, totalPages, onPageChange }: Props){
    const buttons: JSX.Element[] = [];

    if (totalPages > 1) {
        // 首页
        if (currentPage > 3) {
            buttons.push(
                <button key={1} onClick={() => onPageChange(1)} className="px-2 py-1 mx-1 border rounded">
                    1
                </button>
            );
            if (currentPage > 4) {
                buttons.push(<span key="start-ellipsis" className="mx-1">...</span>);
            }
        }

        // 当前页 ±2
        const start = Math.max(2, currentPage - 2);
        const end = Math.min(totalPages - 1, currentPage + 2);
        for (let i = start; i <= end; i++) {
            buttons.push(
                <button
                    key={i}
                    onClick={() => onPageChange(i)}
                    className={`px-2 py-1 mx-1 border rounded ${
                        i === currentPage ? "bg-brightblue text-white" : "bg-white"
                    }`}
                >
                    {i}
                </button>
            );
        }

        // 尾页
        if (currentPage < totalPages - 2) {
            if (currentPage < totalPages - 3) {
                buttons.push(<span key="end-ellipsis" className="mx-1">...</span>);
            }
            buttons.push(
                <button key={totalPages} onClick={() => onPageChange(totalPages)} className="px-2 py-1 mx-1 border rounded">
                    {totalPages}
                </button>
            );
        }
    }

    return (
        <div className="flex justify-center mt-4 items-center">
            <button
                onClick={() => onPageChange(Math.max(currentPage - 1, 1))}
                disabled={currentPage === 1}
                className="px-3 py-1 mx-1 border rounded disabled:opacity-50"
            >
                Prev
            </button>

            {buttons}

            <button
                onClick={() => onPageChange(Math.min(currentPage + 1, totalPages))}
                disabled={currentPage === totalPages}
                className="px-3 py-1 mx-1 border rounded disabled:opacity-50"
            >
                Next
            </button>
        </div>
    );
}