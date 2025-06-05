type Props = {
    user: string|undefined|null
}

export default function Dashboard({user}: Readonly<Props>){
    return(
        <>
        <h1>Dashboard</h1>
        <h2>Hello, {user}</h2>
        </>
    )
}