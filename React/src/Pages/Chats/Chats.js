import React, {useEffect, useState} from "react";
import "./Chats.css"
import Friends from "./Friends";
import Messages from "./Messages";
import Friend_Chat_Details from "./Friend_Chat_Details";
import {useNavigate} from "react-router";
import SendMessage from "./SendMessage";
import ProfileMenu from "./ProfileMenu";
import Welcome from "./Welcome";
import Button from "react-bootstrap/Button";
import {HubConnectionBuilder} from "@microsoft/signalr";


function Chats({user, setter, token, setToken}) {
    let navigate = useNavigate()
    // The friend the user is chatting with now.
    const [friend, setFriend] = useState(undefined)
    const [message, setMessage] = useState('')
    // This useState is used in order to reload the page if needed.
    const [update, setUpdate] = useState(0)
    let connection
    // here we get the SignalR connection with the different methods
    useEffect( () => {
        connection = new HubConnectionBuilder()
            .withUrl('https://localhost:7225/MessageHub')
            .withAutomaticReconnect()
            .build()
        if (connection !== undefined) {
            connection.start()
                .then(() => {
                    let x = 0
                    connection.on('MessageReceived', () => {
                        x = x - 1
                        setUpdate(x)
                    })
                    connection.on('FriendAdded', () => {
                        x = x - 1
                        setUpdate(x)
                    })
                    // Adds the user to the right group
                    connection.invoke('JoinRoom', user)
                })
                .catch(e => console.log('Connection failed: ', e))
        }
    }, [connection])
    let toRet
    // incase a user is not connected
    if (user === '') {
        return (
            <div className={"go-home-butt"}>
                <p>Oops you are not connected, Please press the button below and login</p>
                <Button id={"the-butt"} className={"justify-content-center"} onClick={() => navigate('/')}>Return
                    Home</Button>
            </div>
        )
    }

    // Once the user clicks on a chat it opens the chat with the specific friend he chose.
    const handle_enter_chat = ((selectedFriend) => {
        let friendChat
        // Makes sure a friend is chosen
        if (friend !== undefined) {
            // Sets the background of latest user to be white.
            friendChat = document.getElementById(friend.id)
            friendChat.style.backgroundColor = "white"
        }
        // Paints the background of the active friend chat in grey.
        friendChat = document.getElementById(selectedFriend.id)
        friendChat.style.backgroundColor = "lightgrey"
        setFriend(selectedFriend)
        // Scrolls to the bottom of the chat.
    });
    // Sets the right side to Welcome page if no chat was opened or a specific chat if one was opened.
    if (friend === undefined) {
        toRet = <Welcome/>
    } else {
        toRet =
            <>
                <div id="message-profile"
                     className="bg-info border border-1 border-light align-self-top">
                    <Friend_Chat_Details friend={friend}/>
                </div>
                <div id="messages" className="bg-light d-flex">
                    <Messages friendName={friend} token={token} changeEffect={update}/>
                </div>
                <SendMessage friend={friend} user={user} message={message} setMessage={setMessage}
                             setUpdate={setUpdate} update={update} token={token}/>
            </>
    }

    return (
        <div className="h-100 w-100 row p-0 m-0">
            <div id="leftSide" className="col-4 p-0">
                <ProfileMenu update={update} user={user} setter={setter} setUpdate={setUpdate} setFriend={setFriend}
                             friendGet={friend} token={token} setToken={setToken}/>
                <div id="leftFriends">
                    <Friends user={user} enter_chat={handle_enter_chat} token={token} update={update} currentFriend={friend}/>
                </div>
            </div>
            <div id="rightSide" className="col-8 p-0 d-flex flex-column">
                {toRet}
            </div>
        </div>
    )
}

export default Chats