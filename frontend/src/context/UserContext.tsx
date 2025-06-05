import {createContext} from "react";

export const UserContext = createContext<
    {user: string|null|undefined}
>({user: undefined})

// user 实际上是一个对象，结构是 { user: string | null | undefined }，不是直接的字符串