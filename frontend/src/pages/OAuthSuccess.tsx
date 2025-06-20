
import {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";

export default function OAuthSuccess(){
    const navigate = useNavigate();
    const [error,setError] = useState<string>();

    useEffect(() => {
        const params = new URLSearchParams(window.location.search);
        const token = params.get("token");

        if(token){
            localStorage.setItem("authToken", token);
            axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
            navigate("/");
        }else{
            setError("OAuth Login failed: no token recieved.");

        }
    }, []);

    return (
        <div className="text-center mt-10">
            {error ? (
                <p className="text-red-500">{error}</p>
            ) : (
                <p>Login Successful, loading the page now... </p>
            )}
        </div>

    )
}