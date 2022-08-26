import React from 'react'
import {CreateAccountButton} from "./CreateAccount";
import InputField from "./InputField";
import {ConfirmPasswordV, NickNameV, UserNameV} from "./Validations";
import {Link} from "react-router-dom";
function InputFields({setUser, nav, setToken}) {
    return (
        <div className="col">
            <div>
                <h2>Sign Up</h2>
                <p className="create-p">Already have an account? <Link to={"/"} className="nav-link">Login</Link></p>
            </div>
            <hr/>
            <div className="input-div">
                <InputField id={"user-name"} type={"email"} headline={"User Name (6 or more characters)"} inBoxText={"Enter your Email"} validate={UserNameV}/>
                <InputField id={"password"} type={"password"}headline={"Password  (6 or more characters)"} inBoxText={"Enter password"}/>
                <InputField id={"confirm-password"} type={"password"}headline={"Confirm Password"} inBoxText={"Enter again"} validate={ConfirmPasswordV}/>
                <InputField id={"display-name"} type={"email"}headline={"Display Name"} inBoxText={"Your display name will be visible to your contacts in ChatOS"} validate={NickNameV}/>

                </div>
            <CreateAccountButton setUser={setUser} navigate={nav} setToken={setToken}/>
        </div>
    );
}
export default InputFields;