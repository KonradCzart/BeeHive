import React from "react";
import {
    deleteNotification,
    realizeNotification,
    unrealizeNotification
} from "../util/APIUtils";
import {Checkbox, Icon} from "antd";
import {WrappedNotifEditItem} from "./NotifEditItem";
import "./notifications.css"

const USERNAMES_LIMIT = 8;

/**
 * ## props:
 * * userId : _Integer_;
 * * notif : _Notification_;
 * * onNotifsChange : _Function(success, action, notif)_;
 *
 * ## onNotifsChange:
 * * success : _Boolean_ was action successful?
 * * action : _String_ title of action;
 * * notif : __Notification__ notification that was impacted.
 */
export class NotifItem extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            edited: false,
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleEdit = this.handleEdit.bind(this);
        this.handleDelete = this.handleDelete.bind(this);
        this.handleDecline = this.handleDecline.bind(this);
        this.fromToSpan = this.fromToSpan.bind(this);
        this.isAuthor = this.isAuthor.bind(this);
    }

    handleChange(event) {
        let targetValue = event.target.checked;
        let parent = this;
        if(targetValue) {
            realizeNotification(this.props.notif.id).then(response => {
                parent.props.onNotifsChange(true, "realize",
                    parent.props.notif);
            }).catch(error => {
                parent.props.onNotifsChange(false, "realize",
                    parent.props.notif);
            });
        }
        else {
            unrealizeNotification(this.props.notif.id).then(response => {
                parent.props.onNotifsChange(true, "unrealize",
                    parent.props.notif);
            }).catch(error => {
                parent.props.onNotifsChange(false, "unrealize",
                    parent.props.notif);
            });
        }
    }

    handleEdit() {
        this.setState({
            edited: true,
        });
    }

    handleDecline() {
        this.setState({
            edited: false,
        });
    }

    handleDelete() {
        let parent = this;
        deleteNotification(this.props.notif.id).then(response => {
            parent.props.onNotifsChange(true, "delete", parent.props.notif);
        }).catch(error => {
            parent.props.onNotifsChange(false, "delete", parent.props.notif);
        });
    }

    fromToSpan() {
        let authorJsx = (<span>
            From <span className="username">
                @{this.props.notif.author.username}
            </span> to
        </span>);

        let usersJsx = [];
        let idx = 0;
        for(let user of this.props.notif.users) {
            if(idx > USERNAMES_LIMIT) {
                usersJsx.push((<span key={idx}>...</span>));
                break;
            }
            if(user.id !== this.props.notif.author.id)
                usersJsx.push((
                    <span key={idx++} className="username">
                        @{user.username},
                    </span>));
        }

        return (<span>{authorJsx} {usersJsx}</span>);
    }

    isAuthor() {
        return this.props.notif.author.id === this.props.userId;
    }

    render() {
        if(this.state.edited)
            return (<WrappedNotifEditItem notif={this.props.notif}
                onDecline={this.handleDecline}
                onNotifsChange={this.props.onNotifsChange}/>);

        const fromToSpan = this.fromToSpan();

        return (
            <div
                className={"fl v-center-child h-match-parent v-wrap-content b-marg-32"}>
                <Checkbox className={"in-bl wrap-content r-marg-16"}
                    checked={this.props.notif.isRealize}
                    onChange={this.handleChange}/>
                <div className={"match-parent in-bl"}>
                    <h3 className={this.props.notif.isRealize? "text-checked" : ""}>
                        {this.props.notif.title}
                    </h3>
                    <h4 className={this.props.notif.isRealize? "text-checked" : ""}>
                        {this.props.notif.description}
                    </h4>
                    <h5 className={this.props.notif.isRealize? "text-checked" : ""}>
                        {fromToSpan}
                    </h5>
                </div>
                <div className={"in-fl wrap_content right-children"}
                    style={{display: this.isAuthor()? "inline-float" : "none"}}>
                    <Icon type="edit" onClick={this.handleEdit}
                        className={"in-bl r-marg-32 size-24"}/>
                    <Icon type="delete" onClick={this.handleDelete}
                        className={"in-bl r-marg-32 size-24"}/>
                </div>
            </div>
        );
    }
}