import Upload from "./Upload";
import React from "react";
import send from "./send-button.png"
import "./SendMessage.css"
import {wait} from "@testing-library/user-event/dist/utils";
import $ from "jquery";

function SendMessage({friend, user, message, setMessage, setUpdate, update, token}) {
    // Sends the message to the friend
    const handleSubmit = async function () {
        // Returns if the message is empty.
        if (message === "")
            return
        // Here we are using the different api calls to send a message
        await $.ajax({
            // we do this to get the list of messages from the contact
            url: 'https://localhost:7225/api/contacts/' + friend.id +'/messages' ,
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify({content: message}),
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + token)
            },
            success: function () {
                setMessage('')
                setUpdate(update + 1)
            },
        })
        const tranferDetails = {
            "from": user,
            "to": friend.id,
            "content": message
        }
        $.ajax({
            // we do this to transfer the details of the message to another friend
            url: 'https://' + friend.server + '/api/transfer',
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(tranferDetails),
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + token)
            },
            success: function () {
                setMessage('')
                setUpdate(update + 1)
            },
        })
        // Sets the message to be empty so the user can enter a new message
        wait(60).then(() => {
            document.getElementById('messages').scrollTop = document.getElementById('messages').scrollHeight
        })
    }

    const handleEnterPress = (event) => {
        // If the user press enter it sends the message
        if (event.key === 'Enter') {
            handleSubmit()
        }
    }

    return (
        <div id="bot-Section" className="d-flex border border-light justify-content-around">
            <a className="data-toggle right-bot-section image-dropdown add rounded-circle dropup" href="#"
               role="button"
               data-bs-toggle="dropdown" aria-expanded="false">
                <svg xmlns="http://www.w3.org/2000/svg" width={"auto"} height={"25px"}
                     fill="currentColor"
                     className="bi bi-paperclip add" viewBox="0 0 16 16">
                    <path
                        d="M4.5 3a2.5 2.5 0 0 1 5 0v9a1.5 1.5 0 0 1-3 0V5a.5.5 0 0 1 1 0v7a.5.5 0 0 0 1 0V3a1.5 1.5 0 1 0-3 0v9a2.5 2.5 0 0 0 5 0V5a.5.5 0 0 1 1 0v7a3.5 3.5 0 1 1-7 0V3z"/>
                </svg>
            </a>
            <div className="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <Upload user={user} friend={friend} setUpdate={setUpdate} update={update}/>
            </div>
            <input type="text" id="input" className="rounded-pill border-0 bg-white right-bot-section"
                   onKeyDown={handleEnterPress}
                   placeholder="Please write your message here" value={message}
                   onChange={event => setMessage(event.target.value)}/>
            <div onClick={handleSubmit}
                 className={"rounded-circle bg-info align-self-center send-butt d-flex justify-content-center"}>
                <img alt={"send"} src={send} id="send"
                     className="bi bi-send right-bot-section"/>
            </div>
        </div>
    )
}

export default SendMessage;