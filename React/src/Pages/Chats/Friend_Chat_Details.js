import React from "react";
import logo from './default-icon.png'

function Friend_Chat_Details({friend}) {
    return (
        <div className="m-2 d-flex justify-content-start border-light">
            <img className="rounded-circle border border-1 mx-2" src={logo} height="45px" width="45px"/>
            <h5 className="m-2 mx-2 ">{friend.nickname}</h5>
        </div>
    )
}

export default Friend_Chat_Details