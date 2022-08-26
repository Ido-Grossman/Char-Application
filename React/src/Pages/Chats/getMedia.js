import React from "react";

async function getMedia(user, setUpdate, friend, update, addTime) {

    try {
        // Asks the user if it can record audio from his browser.
        let stream = await navigator.mediaDevices.getUserMedia({audio: true});
        // creates a media recorder to record the media from the user.
        const mediaRecorder = new MediaRecorder(stream)
        // The audio of the user.
        const audioChunks = []
        // When the recording is on going it's pushing the media to the audioChunks.
        mediaRecorder.addEventListener("dataavailable", event => {
            audioChunks.push(event.data);
        });
        // this function defines what happens when the recording stops.
        mediaRecorder.addEventListener("stop", () => {
            const audioBlob = new Blob(audioChunks);
            const audioUrl = URL.createObjectURL(audioBlob);
            let friend_chat = user.friends.get(friend)
            // Pushes the url, time, sender and type of message to the correct arrays
            friend_chat.messages.push(<audio className={"m-2"} controls src={audioUrl}/>)
            addTime(friend_chat)
            friend_chat.sender.push(user)
            friend_chat.type.push('audio')
            setUpdate(update + 1)
            // Stops the recording.
            stream.getTracks().forEach(track => {
                track.stop()
            })
        });
        return mediaRecorder

    } catch (err) {

    }
}

export default getMedia