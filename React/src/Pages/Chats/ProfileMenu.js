import React, {useState} from "react";
import {Button, Modal} from "react-bootstrap";
import "./ProfileMenu.css"
import $ from 'jquery'
import logo from './default-icon.png'

function ProfileMenu({user, setter, friendGet, setFriend, setUpdate, update, token, setToken}) {
    const [show, setShow] = useState(false)
    const [friendToAdd, setFriendToAdd] = useState('')
    const [friendNickname, setFriendNickname] = useState('')
    const [friendServer, setFriendServer] = useState('')

    // Sets the user to empty string and returns to login page.
    const logout = (() => {
        $.ajax({
            url: 'https://localhost:7225/api/users/logout',
            type: 'GET',
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + token)
            },
        })
        localStorage.setItem('token', '')
        sessionStorage.setItem('token', '')
        setToken('')
        setter('')
    })

    // When closing the add friend modal it sets the string to empty string.
    const handleClose = (() => {
        setShow(false)
        setFriendToAdd('')
    });

    // If the user is pressing enter when adding a friend it will activate the addFriend function.
    const handleEnterPress = (event) => {
        if (event.key === 'Enter') {
            addFriend()
        }
    }

    // Shows the modal.
    const handleShow = () => {
        setShow(true);
    }


    const addFriend = async () => {
        let error
        let divError = document.getElementById("error")
        let err = false
        if (friendToAdd === '' || friendServer === '' || friendNickname === '') {
            error = document.createTextNode("At least 1 of the fields is empty, please fill it")
            if (document.getElementById("error-message-exist") !== null) {
                divError = document.getElementById("error-message-exist")
            } else if (document.getElementById("error-message-self") !== null) {
                divError = document.getElementById("error-message-self")
            } else if (document.getElementById("error-message-empty") === null) {
                divError.setAttribute("id", "error-message-empty")
                divError.appendChild(error)
                return;
            } else {
                return;
            }
            divError.setAttribute("id", "error-message-empty")
            divError.replaceChild(error, divError.firstChild)
            return
        } else if (friendToAdd === user) {
            error = document.createTextNode("Unfortunately you can't add yourself.")
            if (document.getElementById("error-message-empty") !== null) {
                divError = document.getElementById("error-message-empty")
            } else if (document.getElementById("error-message-exist") !== null) {
                divError = document.getElementById("error-message-exist")
            } else if (document.getElementById("error-message-self") === null) {
                divError.setAttribute("id", "error-message-self")
                divError.appendChild(error)
                return;
            } else {
                return;
            }
            divError.setAttribute("id", "error-message-self")
            divError.replaceChild(error, divError.firstChild)
            return;
        }
        let friendData = {
            "id": friendToAdd,
            "name": friendNickname,
            "server": friendServer
        }
        let friendInvData = {
            "from": user,
            "to": friendToAdd,
            "server": friendServer
        }
        await $.ajax({
            url: 'https://' + friendServer + '/api/invitations/',
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(friendInvData),
            error: function () {
                error = document.createTextNode("At least 1 of the fields is wrong, Please try again")
                if (document.getElementById("error-message-empty") !== null) {
                    divError = document.getElementById("error-message-empty")
                } else if (document.getElementById("error-message-self") !== null) {
                    divError = document.getElementById("error-message-self")
                } else if (document.getElementById("error-message-exist") === null) {
                    divError.setAttribute("id", "error-message-exist")
                    divError.appendChild(error)
                    return;
                } else {
                    return;
                }
                divError.setAttribute("id", "error-message-exist")
                divError.replaceChild(error, divError.firstChild)
                err = true
            },
        })
        if (err)
            return
        await $.ajax({
            url: 'https://localhost:7225/api/contacts/',
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(friendData),
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + token)
            },
        })
        setShow(false)
        if (friendGet !== undefined) {
            let friendChat = document.getElementById(friendGet.id)
            friendChat.style.backgroundColor = "white"
            setFriend(undefined)
        }
        setUpdate(update + 1)
    }

    return (
        <div id="profile-menu" className="border-bottom border-light">
            <img id="profile" className="rounded-circle border border-1 float-start" src={logo} alt=""
                 height="40px"
                 width="40px"/>
            <a className="data-toggle image-dropdown menu float-end" role="button"
               id="dropdownMenuLink"
               data-bs-toggle="dropdown" aria-expanded="false">
                <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25"
                     fill="currentColor"
                     className="bi bi-list" viewBox="0 0 16 16">
                    <path
                        d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5z"/>
                </svg>
            </a>
            <a className="data-toggle image-dropdown menu float-end" role="button" onClick={handleShow}>
                <svg id="add-friend" xmlns="http://www.w3.org/2000/svg" width="25" height="25"
                     fill="currentColor"
                     className="bi bi-person-plus-fill hoverClick" viewBox="0 0 16 16">
                    <path d="M1 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
                    <path
                        d="M13.5 5a.5.5 0 0 1 .5.5V7h1.5a.5.5 0 0 1 0 1H14v1.5a.5.5 0 0 1-1 0V8h-1.5a.5.5 0 0 1 0-1H13V5.5a.5.5 0 0 1 .5-.5z"/>
                </svg>
            </a>
            <Modal show={show} onHide={handleClose}>
                <Modal.Header>
                    <Modal.Title>
                        <p className="d-flex justify-content-center">Add new Friend</p>
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div id="error" className="error">
                    </div>
                    <input type="text" id="friend-name" className="form-control" onKeyDown={handleEnterPress}
                           placeholder="Please enter the friend's name here" value={friendToAdd}
                           onChange={event => setFriendToAdd(event.target.value)} required/>
                    <br/>
                    <input type="text" id="friend-name" className="form-control" onKeyDown={handleEnterPress}
                           placeholder="Please enter the friend's nickname here" value={friendNickname}
                           onChange={event => setFriendNickname(event.target.value)} required/>
                    <br/>
                    <input type="text" id="friend-name" className="form-control" onKeyDown={handleEnterPress}
                           placeholder="Please enter the friend's server here" value={friendServer}
                           onChange={event => setFriendServer(event.target.value)} required/>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant={"outline-secondary"} onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant={"primary"} type="submit" onClick={addFriend}>
                        Add Friend
                    </Button>
                </Modal.Footer>
            </Modal>
            <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                <li><a className="dropdown-item" href="#">Settings</a></li>
                <li>
                    <hr className="dropdown-divider"/>
                </li>
                <li><a className="dropdown-item" href="#" onClick={logout}>Logout</a></li>
            </ul>
        </div>
    )
}

export default ProfileMenu