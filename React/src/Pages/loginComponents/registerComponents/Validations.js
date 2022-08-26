import $ from "jquery";

export function ValidateAll(){
    return (UserNameV() && ConfirmPasswordV() && NickNameV());

}
//UserNameV makes sure id doesn't exist in DB, and the userName is legal.
export async function UserNameV(){
    let existInDB
    const input = document.getElementById("user-name").value;
    const output = document.getElementById("user-namev");

    // await fetch("https://localhost:7225/api/users/exists", {
    //     method: 'POST',
    //     headers: {'Content-Type': 'application/json'},
    //     body: JSON.stringify(input)
    // }).then((response) => {
    //     existInDB = response.ok
    // })
    await $.ajax({
        // we check if a username exists in the DB on server
        url: 'https://localhost:7225/api/users/exists',
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(input),
        success: function () {
            existInDB = true
        },
        error: function () {
            existInDB = false
        },
    })
    if(input.length < 6) {
        output.innerText = "Minimum 6 chars";
    } else if(existInDB)
        output.innerText = "This User Name already exist";
    else {
        output.innerText = "";
    }
    return output.innerText === "";
}


export async function ConfirmPasswordV(){
    const pass1 = document.getElementById("password");
    const pass2 = document.getElementById("confirm-password");
    const output = document.getElementById("confirm-passwordv");
    if(pass1.value !== pass2.value)
        output.innerText = "Passwords do not match";
    else
        output.innerText = "";
    return output.innerText === "";
}

export async function NickNameV(){
    const input = document.getElementById("display-name").value;
    const output = document.getElementById("display-namev");

    if(input.length < 3)
        output.innerText = "Minimum 3 chars";
    else
        output.innerText = "";
    return output.innerText === "";
}