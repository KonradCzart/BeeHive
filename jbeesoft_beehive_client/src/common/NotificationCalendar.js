import React from "react"
import {getNotifications} from "../util/APIUtils"
import {Calendar} from "antd"
import LoadingIndicator from "./LoadingIndicator";
import ErrorIndicator from "./ErrorIndicator";

export class NotificationCalendar extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            error: false,
        };
        this.dateCellRender = this.dateCellRender.bind(this);
        this.onSelect = this.onSelect.bind(this);
    }

    componentDidMount() {
        getNotifications().then(response => {
            this.state.loading = false;
            // TODO wyciągnij z response'a listę powiadomień, wsadź do state'a
        }).catch(error => {
            this.state.loading = false;
            this.state.error = true;
        })
    }

    dateCellRender(date) {
        if(this.state.loading)
            return (<LoadingIndicator />);
        else if(this.state.error)
            return (<ErrorIndicator />);
        else
            return (<p>date</p>); // TODO this.state[date] - jeśli niepusty, iteruj
    }

    onSelect(date) {
        // TODO okno dialogowe z podglądem, edycją, usuwaniem, dodawaniem
        alert(date + ": hello there")
    }

    render() {
        return (
            // TODO mała wersja?
            <Calendar dateCellRender={this.dateCellRender} onSelect={this.onSelect}/>
        );
    }
}