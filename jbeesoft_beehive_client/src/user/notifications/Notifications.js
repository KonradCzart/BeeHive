import React from "react"
import {NotificationCalendar} from "../../notifications/NotificationCalendar";
import {notification} from "antd";
import Login from "../login/Login";

/**
 * props = {currentUser, isAuthenticated}
 */
export default class Notifications extends React.Component {

    constructor(props) {
        super(props);
        Notifications.handleError = Notifications.handleError.bind(this);
        Notifications.handleSuccess = Notifications.handleSuccess.bind(this);
    }

    static handleError() {
        notification.error({
            message: "Error",
            description: "An error has occured while adding notification.",
        });
    }

    static handleSuccess() {
        notification.success({
            message: "Success",
            description: "Successfully added notification.",
        })
    }

    render() {

        if(this.props.isAuthenticated) {
            return (
                <div>
                    <NotificationCalendar currentUser={this.props.currentUser}
                                          onAddNotifError={Notifications.handleError}
                                          onAddNotifSuccess={Notifications.handleSuccess}{...this.props}/>
                </div>
            );
        }
        else
            return (<Login {...this.props}/>);
    }
}