import React from "react";
import ReactDOM from "react-dom";
import "./index.css"

class App extends React.Component {
    render() {
        return (
            <div className="container">
                <img className="logo" src={require("./logo.png")}
                     alt="logo"/>
                <h1 className="textpiece">Welcome to HivePoint!</h1>
                <SignUp/>
            </div>
        );
    }
}

class SignUp extends React.Component {
    render() {
        return (
            <div className="hmatch-parent vertical-align">
                <TextField type="text" hint="Email"/>
                <TextField type="password" hint="Password"/>
                <div className="vmargin-32 hsize-256 horizontal-align">
                    <Button className="mdc-button--outlined" title="Sign Up"/>
                    <Button className="mdc-button--raised" title="Sign In"/>
                </div>
            </div>
        );
    }
}

function TextField(props) {
    return (
        <div className="textfield">
            <h3>{props.hint}</h3>
            <input type={props.type}/>
        </div>
    )
}

function Button(props) {

    return (
        <button className={"mdc-button themed " + props.className}>{props.title}</button>
    );
}

ReactDOM.render(
    <App/>,
    document.getElementById("root")
);