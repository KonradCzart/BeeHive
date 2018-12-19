import React from "react"
import {NotificationCalendar} from "../../common/NotificationCalendar";
import {notification} from "antd";

/**
 * props = {userId}
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
        return (
            <div>
                {/* TODO pogoda */}
                <NotificationCalendar userId={this.props.userId}
                                      onAddNotifError={Notifications.handleError}
                                      onAddNotifSuccess={Notifications.handleSuccess}/>
            </div>
        );
    }
}