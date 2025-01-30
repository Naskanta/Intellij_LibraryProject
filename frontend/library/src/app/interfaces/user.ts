export interface User {
    firstname:string,
    lastname:string,
    username:string,
    password:string,
    email:string,
    role:string
}

export interface Credentials{
    username: string,
    password: string
}


export interface LoggedInUser {
    lastname: string,
    email: string,
    role: string
}
