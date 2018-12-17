import React from "react"
import {addNotification, getNotifications} from "../util/APIUtils"
import {Button, Calendar, Form, Input, Modal} from "antd"
import LoadingIndicator from "./LoadingIndicator";
import ErrorIndicator from "./ErrorIndicator";
import moment from "moment";

const FormItem = Form.Item;

/**
 * props = {userId, onAddNotifError:Function}
 */
export class NotificationCalendar extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            error: false,
            visible: false,
            notifications: [],
            editedDate: moment().format("YYYY-MM-DD"),
        };
        this.dateCellRender = this.dateCellRender.bind(this);
        this.onSelect = this.onSelect.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.refreshNotifs = this.refreshNotifs.bind(this);
        this.dismiss = this.dismiss.bind(this);
    }

    refreshNotifs() {
        let parent = this;
        getNotifications().then(json => {
            this.state.loading = false;
            parent.setState({
                loading: false,
                notifications: json,
            });
        }).catch(error => {
            parent.setState({
                loading: false,
                error: true,
            });
        });
    }

    componentDidMount() {
        this.refreshNotifs();
    }

    dateCellRender(date) {
        if(this.state.loading)
            return (<LoadingIndicator/>);
        if(this.state.error)
            return (<ErrorIndicator/>);
        if(this.state.notifications[date.format("YYYY-MM-DD")]) {
            const notifsJson = this.state.notifications[date.format("YYYY-MM-DD")];
            return (<h3>{notifsJson.length}</h3>);
        }
        return (<h3>0</h3>);
    }


    onSelect(date) {
        this.setState({
            visible: true,
            editedDate: date.format("YYYY-MM-DD"),
        });
    }

    currentDateNotifs() {
        let todayNotifsJson = this.state.notifications[this.state.editedDate];
        let todayNotifsJsx = [];
        if(todayNotifsJson)
            todayNotifsJson.forEach((value, index)=> {
                todayNotifsJsx.push((<NotifItem key={index} {...value}/>));
            });
        return (
            <div>
                {todayNotifsJsx}
            </div>
        );
    }

    handleSubmit(json) {
        let dict = Object.assign({}, json, {
            date: this.state.editedDate,
            isRealize: false,
            usersId: [this.props.userId],
        });
        console.log("ABOUT TO ADD NOTIFICATION");
        console.log(dict);
        addNotification(dict).then(json => {
            this.refreshNotifs();
        }).catch(error => {
            this.props.onAddNotifError();
        });
    }

    dismiss(event) {
        this.setState({
            visible: false
        });
    }

    render() {
        let currentDateNotifs = this.currentDateNotifs();

        return (
            <div>
                <Calendar dateCellRender={this.dateCellRender}
                          onSelect={this.onSelect}/>
                <Modal visible={this.state.visible} onCancel={this.dismiss} onOk={this.dismiss}>
                    {currentDateNotifs}
                    <WrappedNotificationForm onSubmit={this.handleSubmit}/>
                </Modal>
            </div>
        );
    }

}

/**
 * props = {id, title, date, description, isRealized}
 */
function NotifItem(props) {
    return (
        <div>
            <h3>{props.title}</h3>
            <h4>{props.description}</h4>
        </div>
    );
}

/**
 * props = {onSubmit:Function(output)}
 * output = {title, description}
 */
class NotificationForm extends React.Component {

    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();
        this.props.form.validateFields((err, values) => {
            if(!err) {
                this.props.onSubmit(values);
            }
        });
    }

    render() {
        const {getFieldDecorator} = this.props.form;

        return (
            <Form onSubmit={this.handleSubmit}>
                <FormItem label="Title">
                    {getFieldDecorator("title", {
                        rules: [
                            {
                                required: true,
                                message: "Title should not be empty."
                            },
                        ],
                    })(<Input/>)}
                </FormItem>
                <FormItem label="Description">
                    {getFieldDecorator("description", {})(<Input/>)}
                </FormItem>
                <Button htmlType="submit" type="primary">Submit</Button>
            </Form>
        );
    }
}

/**
 * props = {onSubmit:Function(output)}
 * output = {title, description}
 */
const WrappedNotificationForm = Form.create()(NotificationForm);