
type Props= {
    switchToLogin: ()=>void;
}

export default function SignUp({switchToLogin}: Props) {

    return (
        <div className="w-full max-w-md mx-auto bg-white p-8 rounded-xl shadow-md">
            <h2 className="text-2xl font-semibold text-center mb-6">Sign Up</h2>

            <form className="space-y-4">
                <div>
                    <label className="font-medium block mb-1 text-sm">Name</label>
                    <input
                        type="text"
                        placeholder="Your name"
                        className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                </div>

                <div>
                    <label className="font-medium block mb-1 text-sm">Email</label>
                    <input
                        type="email"
                        placeholder="you@example.com"
                        className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                </div>

                <div>
                    <label className="font-medium block mb-1 text-sm">Password</label>
                    <input
                        type="password"
                        placeholder="At least 5 characters"
                        className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                </div>

                <div>
                    <label className="font-medium block mb-1 text-sm">Confirm Password</label>
                    <input
                        type="password"
                        placeholder="Re-enter password"
                        className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                </div>

                <button
                    type="submit"
                    className="w-full bg-blue-600 text-white py-2 mb-4 rounded-md hover:bg-blue-700 transition-colors"
                >
                    Sign Up
                </button>
            </form>

            <p className="text-sm text-center">
                Already Have An Account?{"  "}
                <button className="text-blue-600 hover:underline"
                        onClick={switchToLogin}
                >Log in</button>
            </p>

        </div>
    );
}
