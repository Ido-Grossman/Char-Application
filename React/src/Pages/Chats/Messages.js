import React, {useEffect, useState} from "react";
import "./Messages.css"
import $ from "jquery";
import {wait} from "@testing-library/user-event/dist/utils";

function Messages({friendName, token, changeEffect}) {
    const [friend_messages, setFriend_messages] = useState([])
    useEffect(async () => {
        const data = await $.ajax({
            url: 'https://localhost:7225/api/Contacts/' + friendName.id + '/Messages',
            type: 'GET',
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + token)
            }
        })
        setFriend_messages(data);
    }, [changeEffect, friendName])
    let float, time, color
    wait(60).then(() => {
        document.getElementById('messages').scrollTop = document.getElementById('messages').scrollHeight
    })

    // This function will return all the messages with the user
    const MessageType = friend_messages.map((message, index) => {
        // Gives the message a green background and set it on the left side if the user sent the message
        // Gives the message grey background and set it on the right side if the friend sent the message
        if (message.sent === false) {
            float = "justify-content-end"
            color = "friend-message"
            time = "friend-time"
        } else {
            float = "justify-content-start"
            color = "user-message"
            time = "user-time"
        }
        return (
            <li key={index} className={"d-flex Message-li " + float}>
                <div className={"message " + color}>
                    <p className={"text Message-text mx-2"}>{message.content}</p>
                    <p className={"user-time border-0 m-0 mx-1 " + time}>{message.created.split(" ")[1]}</p>
                </div>
            </li>
        )

    })
    return (
        <ul className={"w-100 "} id="messages-list">
            {MessageType}
        </ul>
    )
}

export default Messages