import {useState} from "react";
import SignUp from "./SignUp.tsx";
import Login from "./Login.tsx";

type Props = {
    onClose: () => void
}

export default function AuthModal({ onClose }: Props){
    const [isLogin, setIsLogin] = useState(true);

    return(
        <>
            <div className="fixed inset-0 z-50 bg-black/50 backdrop-blur-sm flex items-center justify-center">
                <div className="bg-white p-8 rounded-xl shadow-lg w-full max-w-md relative">
                    {/* Close Button， 关闭函数通过父组件NarBar传递 */}
                    <button
                        onClick={onClose}
                        className="absolute top-3 right-4 text-gray-500 hover:text-gray-800 text-2xl"
                    >
                        ×
                    </button>

                    {/* Conditional Rendering 将两个表单整合在一起，使相互转换*/}
                    {isLogin ? (
                        <Login switchToSignUp={() => setIsLogin(false)} onClose={onClose}/>
                    ) : (
                        <SignUp switchToLogin={() => setIsLogin(true)}  onClose={onClose}/>
                    )}
                </div>
            </div>

        </>
    )
}