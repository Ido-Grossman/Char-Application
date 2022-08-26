import React, {useEffect, useState} from "react";
import './Friends.css'
import $ from 'jquery'
import logo from './default-icon.png'
function Friends({enter_chat, token, update, currentFriend}) {
    // Checks the type of the last message, if it was text it returns the text, else it returns the type of message.
    const [friends_keys, setFriends_keys] = useState([]);
    useEffect(async () => {
        if (token !== null) {
            const data = await $.ajax({
                url: 'https://localhost:7225/api/Contacts',
                type: 'GET',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + token)
                }
            })
            setFriends_keys(data);
        }
    }, [update, token, currentFriend])
    const m = friends_keys.map((friend, index) =>  {
        let messageNum = friend.lastMessageId - friend.lastMessageRead
        let numOfUnread = null
        if (friend.lastDate !== null && friend.lastDate !== undefined) {
            friend.lastDate = friend.lastDate.split(" ")[1]
        }
        if (messageNum > 0) {
            numOfUnread = <span className="badge float-right text-bg-secondary bg-primary">{messageNum}</span>
        }
        if (currentFriend !== null && currentFriend !== undefined && currentFriend !== '' && currentFriend === friend)
            numOfUnread = null
        return (
            <li className={"d-flex friend justify-content-between border-bottom border-light"} key={index} id={friend.id}
                onClick={() => {
                    enter_chat(friend)
                }}>
                <div className="d-flex justify-content-start">
                    <img className="friend_image rounded-circle border border-1"
                         src={logo} height="45px" width="45px" alt={undefined}/>
                    <div className={"friend-details mx-2"}>
                        <p id={"friend-name"} className="align-self-start no-margin">{friend.name}{numOfUnread}</p>
                        {friend.last}
                    </div>
                </div>
                <p className={"float-end align-self-end border-1 m-1 mb-1"}>{friend.lastDate}</p>
            </li>
        )})
    // The names of all the friends of the user
    return (
        <div>
            <ul className='friends-list'>
                {m}
            </ul>
        </div>
    )
}

export default Friends