import React, {useState, useEffect} from 'react';
import './loginComponents/login.css';
import NavBar from "./loginComponents/Navbar";
import photo from "./loginComponents/imagesLogin/try-this.png";
import CheckUser from './loginComponents/CheckUser';
import {useNavigate} from 'react-router';
import {Modal, Button} from "react-bootstrap";
import {Link} from "react-router-dom";
import $ from 'jquery'

function Login({usernames, setToken, handletoken, token}) {
    // using use state for both password and username
    const [user, set_user] = useState('');
    const [password, set_password] = useState('');
    let navigate = useNavigate();
    const [showModal, setShow] = useState(false);
    const [remember, setRemember] = useState(false)
    useEffect(async () => {
        if (token !== null) {
            // we use an api call to make sure the credentials of the user are correct
            await $.ajax({
                url: 'https://localhost:7225/api/users/loggedin',
                type: 'GET',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + token)
                },
                success: function (data) {
                    let sentence = data.split(" ")
                    setToken(token)
                    usernames(sentence[0])
                },
            })
        }
    }, [token])
    const handleRemember = () => {
        setRemember(!remember)
    }
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    // this function is to submit while pressing the enter button
    function pressedEnter(e) {
        var key = e.key;
        if (key === "Enter") {
            CheckUser(user, password, set_user, set_password, navigate, usernames, handleShow, setToken, remember, handletoken);
        }
    }

    return (
        <div>
            <NavBar/>
            <div className="container">
                <div className="row">

                    <div className="col change">
                        <img className="change" src={photo} alt={undefined}/>
                    </div>

                    <div className="col top-margin">
                        <div className="sign-text">
                            <h2>Sign in</h2>


                            <p className="create-p">or <Link to={"/Register"} className="nav-link">create an
                                account</Link></p>
                        </div>

                        <hr/>

                        <div className="mb-3">


                            <label className="form-label">Username</label>
                            <input value={user} onKeyDown={(e) => pressedEnter(e)}
                                   onChange={event => set_user(event.target.value)} className="form-control"
                                   id="myInput" name="email-val" placeholder="Place your ID" required/>
                            <br/>


                            <label className="form-label">Password</label>
                            <input value={password} onKeyDown={(e) => pressedEnter(e)}
                                   onChange={event => set_password(event.target.value)} type="password"
                                   className="form-control" id="password-login" name="password-val"
                                   placeholder="No one will see.." required/>


                            <div className="form-check">
                                <input className="form-check-input" type="checkbox" value="" id="flexCheckDefault"
                                       onClick={handleRemember}/>
                                <label className="form-check-label">
                                    Remember Me
                                </label>
                            </div>
                            <button
                                onClick={() => CheckUser(user, password, set_user, set_password, navigate, usernames
                                    , handleShow, setToken, remember, handletoken)}
                                id="myBtn" className="btn btn-primary sign-btn">
                                Sign In
                            </button>
                        </div>

                    </div>
                </div>
            </div>

            <Modal show={showModal} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Unable to connect</Modal.Title>
                </Modal.Header>
                <Modal.Body>The Email or password is incorrect, please try again</Modal.Body>
                <Modal.Footer>
                    <Button variant="primary" onClick={handleClose}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>

    )
}

export default Login
