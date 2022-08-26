import React from "react";
import "./Welcome.css"
import logo from "./../loginComponents/imagesLogin/nobckg/without_Name.png"

function Welcome() {
    return (
        <div className={"welcome-page d-flex justify-content-center align-items-center flex-column"}>
            <img alt={"logo"} src={logo} height={"100"} width={"auto"}/>
            <h1>Welcome To ChatOS</h1>
            <h2>Choose your friend and start chatting</h2>
        </div>
    )
}

export default Welcome