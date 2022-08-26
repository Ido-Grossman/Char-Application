import React from 'react'
import $ from 'jquery'

// this js file checks if the username and password are correct and if it exists on the DB

function CheckUser(userName, password, set_user, set_password, navigate, usernames, handleShow, setToken
                   , remember, handleToken) {
    // we get the class of the account through here
    const jsonData = {
        "username": userName,
        "password": password,
    };
    if (remember === true) {
        $.ajax({
            // if we wish to remember the users login credential
            url: 'https://localhost:7225/api/users/Login',
            type: 'POST',
            contentType: "application/json",
            xhrFields: {
                withCredentials: true
            },
            data: JSON.stringify(jsonData),
            success: function (data) {
                let sentence = data.split(" ")
                handleToken(sentence[0], false)
                usernames(userName)
            },
            error: function () {
                set_user('')
                set_password('')
                handleShow()
            },
        })
    } else {
        $.ajax({
            // if we dont want to remember the credentials we dont send the withCredentials field
            url: 'https://localhost:7225/api/users/Login',
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(jsonData),
            success: function (data) {
                let sentence = data.split(" ")
                handleToken(sentence[0], true)
                usernames(userName)
            },
            error: function () {
                set_user('')
                set_password('')
                handleShow()
            },
        })
    }
}


export default CheckUser