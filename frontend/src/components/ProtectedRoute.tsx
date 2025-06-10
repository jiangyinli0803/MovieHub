import {Navigate, Outlet} from "react-router-dom";
import type {IUser} from "../interface/IUser.ts";

type ProtectedRouteProps = {
    user: IUser|undefined|null
}

export default function ProtectedRoute(props: Readonly<ProtectedRouteProps>){
    if(props.user === undefined || props.user === null){
        return <h3>Loading...</h3>
    }

    return(
        props.user ? <Outlet /> :<Navigate to={"/"}/>
    )
}