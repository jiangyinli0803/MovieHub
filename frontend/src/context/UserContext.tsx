import {createContext, useEffect, useState} from "react";
import type {IUser} from "../interface/IUser.ts";
import axios from "axios";

interface IUserContext {
    user: IUser | null | undefined;
    setUser: React.Dispatch<React.SetStateAction<IUser | null | undefined>>;
    loadUser: () => void;
}

export const UserContext = createContext<IUserContext>({
    user: undefined,
    setUser: () => {},
    loadUser: () => {}
});
// user 实际上是一个对象，结构是 { user: IUser | null | undefined }，不是直接的字符串

// Provider 组件
export const UserProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const [user, setUser] = useState<IUser | null | undefined>(undefined);

    const loadUser = async () => {
        const jwtToken = localStorage.getItem("authToken");
        if (!jwtToken) {
            setUser(null);
            return;
        }

        try {
            const res = await axios.get("/api/auth", {
                headers: {
                    Authorization: `Bearer ${jwtToken}`
                }
            });
            setUser(res.data);
            console.log("Username new: ", res.data.username)
        } catch (err) {
            console.error("Token verify error:", err);
            localStorage.removeItem("authToken");
            setUser(null);
        }
    };

    useEffect(() => {
        loadUser();
    }, []);

    return (
        <UserContext.Provider value={{ user, setUser, loadUser }}>
            {children}
        </UserContext.Provider>
    );
};