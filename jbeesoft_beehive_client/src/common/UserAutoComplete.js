import React from "react";
import {getUsersLike} from "../util/APIUtils";
import {AutoComplete} from "antd";

/**
 * props = {onUserSelect:Function(id, username)}
 */
export class UserAutoComplete extends React.Component {

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
        this.props.onUserSelect(this.state.map[username], username);
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