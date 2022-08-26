import React from "react";
import {useState} from "react";
import {Modal, Button} from "react-bootstrap";
import getMedia from "./getMedia";
import "./Upload.css"

function Upload({user, friend, update, setUpdate, addTime}) {
    const [showRec, setShowRec] = useState(false);
    const [showErr, setShowErr] = useState(false)
    let media
    let type

    // Handles the opening and closing of the modals
    const handleCloseRec = () => setShowRec(false);
    const handleShowRec = () => setShowRec(true);
    const handleCloseErr = () => setShowErr(false)
    const handleShowErr = () => setShowErr(true)


    // uploads video or image, depending on the type of file.
    const uploadImgVidHandler = (event) => {
        console.log(type)
        if (event.target.files[0] === null) {
            return
        }
        let url = URL.createObjectURL(event.target.files[0])
        let friend_chat = user.friends.get(friend)
        if (type === "image" && (event.target.files[0].name.endsWith(".png") || event.target.files[0].name.endsWith(".jpg") || event.target.files[0].name.endsWith(".jpeg"))) {
            friend_chat.messages.push(<img alt={"Message to friend"} src={url} className={"m-2"}/>)
            friend_chat.type.push('image')
        } else if (type === "video" && event.target.files[0].name.endsWith(".mp4")) {
            friend_chat.messages.push(<video src={url} className={"m-2"} controls/>)
            friend_chat.type.push('video')
        } else {
            handleShowErr()
            return;
        }
        addTime(friend_chat)
        friend_chat.sender.push(user)
        setUpdate(update + 1)
    }

    // Stops the recording of audio
    const stopRecordAudio = (() => {
        let startRec = document.createElement("Button")
        startRec.setAttribute("variant", "primary")
        startRec.onclick = recordAudio
        startRec.setAttribute("id", "start-recording")
        startRec.textContent = "Record"
        document.getElementById("stop-recording").replaceWith(startRec)
        document.getElementById("stop-record").disabled = false
        setShowRec(false)
        media.then(recorder => {
            recorder.stop()
        })
    })

    // Starts recording the audio
    const recordAudio = (() => {
        let stopRec = document.createElement("Button")
        stopRec.variant = "secondary"
        stopRec.onclick = stopRecordAudio
        stopRec.setAttribute("id", "stop-recording")
        stopRec.textContent = "Stop recording"
        document.getElementById("start-recording").replaceWith(stopRec)
        document.getElementById("stop-record").disabled = true
        media = getMedia(user, setUpdate, friend, update, addTime)
        media.then(recorder => {
            recorder.start()
        })
    })

    return (
        <div className="d-flex justify-content-between add-files-menu">
            <label htmlFor={"upload-image"}>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                     type="file"
                     className="bi bi-image hoverClick" viewBox="0 0 16 16">
                    <path d="M6.002 5.5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0z"/>
                    <path
                        d="M2.002 1a2 2 0 0 0-2 2v10a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V3a2 2 0 0 0-2-2h-12zm12 1a1 1 0 0 1 1 1v6.5l-3.777-1.947a.5.5 0 0 0-.577.093l-3.71 3.71-2.66-1.772a.5.5 0 0 0-.63.062L1.002 12V3a1 1 0 0 1 1-1h12z"/>
                    <input type="file"/>
                </svg>
                <input id="upload-image" type="file" className="none-display" onChange={(e) => {type="image"; uploadImgVidHandler(e);}}
                       accept=".png, .jpg, .jpeg"/>
            </label>
            <label htmlFor={"upload-video"}>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                     className="bi bi-file-earmark-play hoverClick" viewBox="0 0 16 16">
                    <path
                        d="M6 6.883v4.234a.5.5 0 0 0 .757.429l3.528-2.117a.5.5 0 0 0 0-.858L6.757 6.454a.5.5 0 0 0-.757.43z"/>
                    <path
                        d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2zM9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5v2z"/>
                </svg>
                <input id="upload-video" type="file" className="none-display" onChange={(e) => {type="video"; uploadImgVidHandler(e);}}
                       accept="video/mp4,video/x-m4v,video/*"/>
            </label>
            <label htmlFor={"record-audio"}>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                     className="bi bi-mic-fill hoverClick" viewBox="0 0 16 16">
                    <path d="M5 3a3 3 0 0 1 6 0v5a3 3 0 0 1-6 0V3z"/>
                    <path
                        d="M3.5 6.5A.5.5 0 0 1 4 7v1a4 4 0 0 0 8 0V7a.5.5 0 0 1 1 0v1a5 5 0 0 1-4.5 4.975V15h3a.5.5 0 0 1 0 1h-7a.5.5 0 0 1 0-1h3v-2.025A5 5 0 0 1 3 8V7a.5.5 0 0 1 .5-.5z"/>
                </svg>
                <input id={"record-audio"} onClick={handleShowRec} type={"button"}/>
            </label>
            <Modal show={showRec} onHide={handleCloseRec}>
                <Modal.Header closeButton>
                    <Modal.Title>Record audio</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className={"d-flex justify-content-center"}>
                        <Button id="start-recording"
                                variant="primary" onClick={recordAudio}>
                            Record
                        </Button>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button id={"stop-record"} variant="outline-secondary" onClick={handleCloseRec}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
            <Modal show={showErr} onHide={handleCloseErr}>
                <Modal.Header closeButton>
                    <Modal.Title>Wrong file type</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className={"d-flex justify-content-center"}>
                        <p className={"err-message"}>Wrong file type, image file types are: .mpg/.jpg/.jpeg.<br/>
                            video file types are: .mp4</p>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button id={"stop-record"} className={"d-flex justify-content-center"} variant="outline-secondary" onClick={handleCloseErr}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    )
}

export default Upload