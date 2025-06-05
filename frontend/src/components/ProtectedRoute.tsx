import {Navigate, Outlet} from "react-router-dom";

type ProtectedRouteProps = {
    user: string|undefined|null
}

export default function ProtectedRoute(props: Readonly<ProtectedRouteProps>){
    if(props.user === undefined || props.user === null){
        <h3>Loading...</h3>
    }

    return(
        props.user ? <Outlet /> :<Navigate to={"/"}/>
    )
}