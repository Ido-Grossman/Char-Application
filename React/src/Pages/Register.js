import React, {useState} from 'react'
import './loginComponents/login.css';
import './loginComponents/registerComponents/register.css';
import NavBar from "./loginComponents/Navbar";
import photo from "./Chats/default-icon.png"
import InputFields from "./loginComponents/registerComponents/InputFields";
import default_photo from "./Chats/default-icon.png";
import {useNavigate} from "react-router";

let p_photo=photo;

function Register({setUser, setToken}) {
    const [p_photo, setPhoto] = useState(default_photo)
    let nav = useNavigate()
    const uploadImg = (img) => {
        if (img.target.files[0] === null){}
        else if (img.target.files[0].name.endsWith(".png") || img.target.files[0].name.endsWith(".jpg") || img.target.files[0].name.endsWith(".jpeg")) {
            setPhoto(URL.createObjectURL(img.target.files[0]))
        }
    }
    return(
        <div>
            <NavBar />
            <br/>
            <div className="container">
                <div className="row">
                    <div className="col">
                        <img src={p_photo} id="profile-photo" alt={"profile image"} height={"400"} width={"400"} className="center"/>
                        <div className="pad_left">

                            <div className="btn"><h5>Upload profile image:</h5></div>

                            <div className="input-group">
                                <div className="custom-file">
                                    <input type="file" className="custom-file-input" id="inputGroupFile04" onChange={uploadImg}/>
                                </div>
                            </div>
                        </div>
                    </div>
                   <InputFields setUser={setUser} nav={nav} setToken={setToken}/>
                </div>
            </div>
        </div>
    )
}

export default Register;