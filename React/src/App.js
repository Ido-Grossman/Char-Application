import React, {useEffect, useState} from 'react'
import Login from "./Pages/Login";
import Chats from "./Pages/Chats/Chats";
import Register from "./Pages/Register";
import './Pages/Style.css'
import {BrowserRouter, Routes, Route} from "react-router-dom";

function App() {
    // This is the user that is logged in
    let [user, setUser] = useState('')
    let [token, setToken] = useState(localStorage.getItem('token'))
    useEffect(() => {
        if (sessionStorage.getItem('token') !== '')
            setToken(sessionStorage.getItem('token'))
        else
            setToken(localStorage.getItem('token'))
    }, [])
    const handleToken = (givenToken, isSession) => {
        if (isSession) {
            sessionStorage.setItem('token', givenToken)
        } else {
            localStorage.setItem('token', givenToken)
        }
        setToken(givenToken)
    }
    let page
    if (user !== ''){
        page = <Chats user={user} setter={setUser} token={token} setToken={setToken}/>
    } else {
        page = <Login usernames={setUser} setToken={setToken}
                      handletoken={handleToken} token={token}/>
    }
    return (
        <div>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={page}/>
                    <Route path="/register" element={<Register setUser={setUser} setToken={setToken}/>}/>
                </Routes>
            </BrowserRouter>
        </div>
    )
}

export default App