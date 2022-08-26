import React, {useState} from 'react'
import {UserNameV, ValidateAll} from "./Validations";
import {useNavigate} from "react-router";
import {Button, Modal} from "react-bootstrap";
import $ from "jquery";



export function CreateAccountButton({setUser, navigate, setToken}){
    const [showModal, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const CreateAccount = (async () => {
        let inputOK = ValidateAll();
        if(inputOK){
            // when we validate the register fields are okay we send the details to the api
            const username = document.getElementById("user-name").value;
            const nickname = document.getElementById("display-name").value;
            const password = document.getElementById("password").value;
            const newUserJson = {
                "username": username,
                "password": password,
                "nickname": nickname,
                "server" : "http://localhost:3000/"
            }
            await $.ajax({
                url: 'https://localhost:7225/api/users/register',
                type: 'POST',
                contentType: "application/json",
                data: JSON.stringify(newUserJson),
                success: function (data) {
                    setToken(data)
                    setUser(username)
                    navigate("/")
                },
            })
        }
        else{
            handleShow(true)
        }
    })
    return (
        <div>
            <button type="button" className="btn btn-secondary btn-lg btn-block" onClick={CreateAccount}>Create My Account</button>
            <div>
                <Modal show={showModal} onHide={handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>Invalid Registration</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>Make sure your information is valid and try again</Modal.Body>
                    <Modal.Footer>
                        <Button variant="primary" onClick={handleClose}>
                            Close
                        </Button>
                    </Modal.Footer>
                </Modal>
            </div>
        </div>

)
}
async function AddAccount(user_name, nickname, password){
    const newUserJson = {
        "username": user_name,
        "password": password,
        "nickname": nickname,
        "server" : "http://localhost:3000/"
    }
    await fetch("https://localhost:7225/api/users/register", {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(newUserJson)
    })
}