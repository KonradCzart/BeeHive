import React from "react";
import {Button, Icon, notification} from "antd";

const MESSAGE = "BeeHive App";
const DESCRIPTION = "You are not privileged to perform this action.";

/**
 * ## props:
 * * history : _Any_ Router history;
 * * ...props : _Any_ goes to Icon props.
 */
export class BackIcon extends React.Component {

    constructor(props) {
        super(props);
        this.handleClick = this.handleClick.bind(this);
    }

    handleClick() {
        this.props.history.goBack();
    }

    render() {
        return (<Icon type="arrow-left"
            onClick={this.handleClick} {...this.props}/>);
    }
}

/**
 * ## props:
 * * privileges : _Array_(Privilege) user's privileges;
 * * privilege : _Privilege_ required privilege;
 * * history : _Any_ Router history;
 * * path : _String_ path to redirect to after clicking the button;
 * * ...props : _Any_ goes to Button props.
 */
export class RedirectButton extends React.Component {

    constructor(props) {
        super(props);
        this.handleClick = this.handleClick.bind(this);
    }

    handleClick() {
        for(let privilege of this.props.privileges) {
            if(privilege.name === this.props.privilege) {
                this.props.history.push(this.props.path);
                return;
            }
        }

        notification.warning({
            message: MESSAGE,
            description: DESCRIPTION,
        });
    }

    render() {
        return (<Button
            onClick={this.handleClick} {...this.props}>{this.props.children}</Button>);
    }
}