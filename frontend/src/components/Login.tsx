
type Props= {
    switchToSignUp: ()=>void;
}

export default function Login({switchToSignUp}: Props){

    return(
        <>
            <div className="space-y-4">
                <h2 className="text-xl font-semibold text-center">Log in with</h2>

                <div className="flex flex-col space-y-2">
                    <button className="flex items-center justify-center border px-4 py-2 rounded hover:bg-gray-100">
                        <img src="../../public/assets/google-icon.svg" alt="Google" className="w-5 h-5 mr-2" />
                        Google
                    </button>
                    <button className="flex items-center justify-center border px-4 py-2 rounded hover:bg-gray-100">
                        <img src="../../public/assets/github-mark.svg" alt="Github" className="w-5 h-5 mr-2" />
                        Github
                    </button>
                </div>

                <form onSubmit={(e) => e.preventDefault()} className="space-y-4">
                    <div className="w-full flex items-center space-x-3">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M21.75 6.75v10.5a2.25 2.25 0 0 1-2.25 2.25h-15a2.25 2.25 0 0 1-2.25-2.25V6.75m19.5 0A2.25 2.25 0 0 0 19.5 4.5h-15a2.25 2.25 0 0 0-2.25 2.25m19.5 0v.243a2.25 2.25 0 0 1-1.07 1.916l-7.5 4.615a2.25 2.25 0 0 1-2.36 0L3.32 8.91a2.25 2.25 0 0 1-1.07-1.916V6.75" />
                    </svg>
                    <input
                        type="email"
                        placeholder="Email address"
                        required
                        className="flex-1 px-3 py-2 border rounded"
                    />
                    </div>
                    <div className="w-full flex items-center space-x-3">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M16.5 10.5V6.75a4.5 4.5 0 1 0-9 0v3.75m-.75 11.25h10.5a2.25 2.25 0 0 0 2.25-2.25v-6.75a2.25 2.25 0 0 0-2.25-2.25H6.75a2.25 2.25 0 0 0-2.25 2.25v6.75a2.25 2.25 0 0 0 2.25 2.25Z" />
                    </svg>
                    <input
                        type="password"
                        placeholder="Password"
                        required
                        className="flex-1 px-3 py-2 border rounded"
                    />
                    </div>
                    <button type="submit" className="w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600">
                        Login
                    </button>
                </form>

                <p className="text-sm text-center mt-4">
                    Don’t have an account?{" "}
                    <button className="text-blue-600 hover:underline" onClick={switchToSignUp}>
                        Sign up now
                    </button>
                </p>
            </div>
        </>
    )
}
