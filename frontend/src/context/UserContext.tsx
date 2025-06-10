import {createContext} from "react";
import type {IUser} from "../interface/IUser.ts";

interface IUserContext {
    user: IUser | null | undefined;
}

export const UserContext = createContext<IUserContext>({
    user: undefined,
});
// user 实际上是一个对象，结构是 { user: IUser | null | undefined }，不是直接的字符串