import React from "react";
import {AutoComplete, Icon} from "antd";
import "./NotificationCalendar.css";
import {getUsersLike} from "../../util/APIUtils";

/**
 * props = {initialUsers: {username1: userId1} onUsersChange: function(usersId)}
 */
export class NotifUsersForm extends React.Component {

    constructor(props) {
        super(props);

        const map = Object.assign({}, this.props.initialUsers);
        const usernames = [];
        for(let username in map) usernames.push(username);

        this.state = {
            usernames: usernames,
            map: map,
        };
        this.handleUserAdd = this.handleUserAdd.bind(this);
        this.handleUserDelete = this.handleUserDelete.bind(this);
    }

    handleUserAdd(id, username) {

        if(!this.state.map[username]) {
            const map = Object.assign({}, this.state.map);
            map[username] = id;
            const usernames = [...this.state.usernames];
            usernames.push(username);

            const ids = [];
            for(let username in map)
                ids.push(map[username]);

            this.setState({
                usernames: usernames,
                map: map,
            });

            this.props.onUsersChange(ids);
        }
    }

    handleUserDelete(username) {
        if(this.state.map[username]) {
            const map = Object.assign({}, this.state.map);
            delete map[username];
            const usernames = [...this.state.usernames];
            const idx = usernames.indexOf(username);
            usernames.splice(idx, 1);

            const ids = [];
            for(let username in map)
                ids.push(this.state.map[username]);

            this.setState({
                usernames: usernames,
                map: map,
            });

            this.props.onUsersChange(ids);
        }
    }

    render() {
        return (
            <div className={"h-1-2 v-wrap-content b-marg-32"}>
                <h4>Users involved:</h4>
                <NotifUsersList usernames={this.state.usernames}
                                onUserDelete={this.handleUserDelete}/>
                <NotifUserAdder onUserAdd={this.handleUserAdd}/>
            </div>
        );
    }
}

/**
 * props = {usernames: [username1, ...], onUserDelete: function(username)}
 */
class NotifUsersList extends React.Component {

    constructor(props) {
        super(props);
        this.userEntryJSX = this.userEntryJSX.bind(this);
    }

    userEntryJSX(index = 0, username) {
        const parent = this;
        const handleClick = () => {
            parent.props.onUserDelete(username);
        };

        return (
            <div key={index} className={"in-fl center-children wrap-content border-8 r-marg-16"}>
                <h5 className={"in-bl marg-8"}>{username}</h5>
                <Icon className={"in-bl marg-8"} type="delete" onClick={handleClick}/>
            </div>
        );
    }

    render() {
        const usersJSX = [];
        let idx = 0;
        this.props.usernames.forEach(username => {
            usersJSX.push(this.userEntryJSX(idx++, username));
        });

        return (
            <div className={"fl v-wrap-content h-match-parent b-marg-16"}>
                {usersJSX}
            </div>
        );
    }
}

/**
 * props = {onUserAdd:Function(id, username)}
 */
class NotifUserAdder extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            dataSource: [],
            map: {},
        };
        this.handleSelect = this.handleSelect.bind(this);
        this.handleSearch = this.handleSearch.bind(this);
    }

    handleSelect(username) {
        this.props.onUserAdd(this.state.map[username], username);
    }

    handleSearch(string) {
        const parent = this;
        getUsersLike(string).then(users => {

            const usernames = [];
            const map = {};
            users.forEach(user => {
                usernames.push(user.value);
                map[user.value] = user.id;
            });

            parent.setState({
                dataSource: usernames,
                map: map,
            });
        });
    }

    render() {
        return (
            <AutoComplete dataSource={this.state.dataSource}
                          placeholder={"username"} onSelect={this.handleSelect}
                          onSearch={this.handleSearch}/>
        );
    }
}