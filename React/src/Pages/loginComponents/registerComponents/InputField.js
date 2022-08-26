import React from "react";

function InputField({type, id, headline, inBoxText, validate}) {
    return (
        <>
        <label htmlFor="exampleFormControlInput1" className="form-label">{headline}</label>
            <input onBlur={validate} type={type} className="form-control" id={id}
               placeholder={inBoxText}/>
            <nobr className="text-danger no-margin" id={id+'v'}></nobr>
        <br/>
        </>
);
}

export default InputField;