import type {IUser} from "../interface/IUser.ts";

type Props = {
    user: IUser|undefined|null
}

export default function Dashboard({user}: Readonly<Props>){
    return(
        <>
        <h1>Dashboard</h1>

        <h2 className="text-2xl mt-2">Hello, {user?.username}</h2>
        <img src={user?.avatarUrl} alt={user?.username}/>

        </>
    )
}