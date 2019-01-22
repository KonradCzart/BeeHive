import React, {Component} from "react";
import "./Index.css";
import logoSrc from "../logo.svg"
import ApiaryList from "../apiary/ApiaryList"

function WelcomeScreen() {
    return (
        <div className="index-welcome">
            <img className="logo" src={logoSrc} alt=""/>
            <h1>Welcome to BeeHive - absolutely the best place for your
                bees!</h1>
        </div>
    );
}

/*class UserPanel extends Component {

    render() {
        return (
            <div className="index-welcome">
                <h2>Dear {this.props.currentUser.name}, here will be your user
                    panel.</h2>
            </div>
        );
    }
}*/

class Index extends Component {

    render() {
        return this.props.isAuthenticated? <ApiaryList {...this.props}/> :
            <WelcomeScreen/>;
    }
}

export default Index;