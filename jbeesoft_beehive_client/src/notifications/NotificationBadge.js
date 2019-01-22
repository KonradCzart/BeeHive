import React from "react";
import {getTodayHotNotifs} from "../util/ApiFacade";
import moment from "moment";
import {Badge, Icon} from "antd";

export class NotificationBadge extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            count: 0,
        };

        this.componentDidMount = this.componentDidMount.bind(this);
        this.getTodayNotifications = this.getTodayNotifications.bind(this);
    }

    componentDidMount() {
        this.getTodayNotifications();
    }

    getTodayNotifications() {
        getTodayHotNotifs(moment()).then(json => {
            console.log("TODAY'S NOTIFICATIONS: ", json);
            this.setState({count: json.length})
        }).catch(json => {
            this.setState({count: 0})
        });
    }

    render() {
        return (
            <Badge count={this.state.count}>
                <Icon type="notification"/>
            </Badge>
        );
    }
}