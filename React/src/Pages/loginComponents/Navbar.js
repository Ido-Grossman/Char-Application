import React from 'react'
import logo from "./imagesLogin/nobckg/without_Name.png"
import {Link} from "react-router-dom";
import Button from 'react-bootstrap/Button';
import Navbar from 'react-bootstrap/Navbar';
import {useNavigate} from "react-router";
import "./login.css";
function NavBar() {
    let navigate = useNavigate()
    const goHome = (() => {
        navigate("/")
    })
    return (
        <Navbar bg="light" expand="lg">
            <div className="container-fluid justify-content-between">
            <Link to={"/"}><Button variant="outline-primary">Try ChatOS Premium</Button></Link>
                <a className="navbar-brand hoverClick" onClick={goHome}>
                    <img src={logo} alt="" width="50" height="44" className="d-inline-block rounded"/>
                    ChatOS
                </a>
                <div>
                <Link to="/" className="nav-link float-right"> Download ChatOS </Link>
                <Link to="//localhost:7073" className="nav-link float-right"> Rate Us! </Link>
                </div>
            </div>
        </Navbar>
        
    );
}

export default NavBar;