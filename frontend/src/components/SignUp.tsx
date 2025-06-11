import {useState} from "react";
import axios from "axios";

type Props= {
    switchToLogin: ()=>void;
    onClose: ()=>void;
}

export default function SignUp({switchToLogin}: Props) {

        const [email, setEmail] = useState("");
        const [password, setPassword] = useState("");
        const [username, setUserName] = useState("");
        const [message, setMessage] = useState("");

    function handleRegister(e: React.FormEvent) {
        e.preventDefault();

            axios.post("/api/form/register", {
                username,
                email,
                password
            }).then(response =>  {setMessage(response.data)})
                .catch(err => {
                    if (err.response?.status === 400) {
                        setMessage("This email is already registered");
                    }
                    else{
                        const serverMessage = err.response?.data?.message || "Registration failed";
                        setMessage(serverMessage);
                    }
                });

    }

    return (
        <div className="w-full max-w-md mx-auto bg-white p-8 rounded-xl shadow-md">
            <h2 className="text-2xl font-semibold text-center mb-6">Sign Up</h2>

            <form className="space-y-4" onSubmit={handleRegister}>
                <div>
                    <label className="font-medium block mb-1 text-sm">Name</label>
                    <input
                        type="text"
                        placeholder="Your name"
                        value={username}
                        onChange={e => setUserName(e.target.value)}
                        className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                </div>

                <div>
                    <label className="font-medium block mb-1 text-sm">Email</label>
                    <input
                        type="email"
                        placeholder="you@example.com"
                        value={email}
                        onChange={e => setEmail(e.target.value)}
                        className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                </div>

                <div>
                    <label className="font-medium block mb-1 text-sm">Password</label>
                    <input
                        type="password"
                        placeholder="At least 5 characters"
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                        className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                </div>

                <div>
                    <label className="font-medium block mb-1 text-sm">Confirm Password</label>
                    <input
                        type="password"
                        placeholder="Re-enter password"
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                        className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                </div>

                <button
                    type="submit"
                    className="w-full bg-blue-600 text-white py-2 mb-4 rounded-md hover:bg-blue-700 transition-colors"
                >
                    Sign Up
                </button>

                {message && <p className="text-red-600">{message}</p>}
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
