import React from "react";
import {addNotification} from "../../util/APIUtils";
import {Button, Form, Input} from "antd";
import {NotifUsersForm} from "./NotifUsersForm";

const FormItem = Form.Item;

/**
 * props = {
 *   date:String, username:String, userId:Number,
 *   onNotifsChange:Function(success:Boolean, action:String, notif:Notification)
 * }
 */
class NotifCreateForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            usersId: [this.props.userId],
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleUsersChange = this.handleUsersChange.bind(this);
    }

    handleSubmit(event) {
        let parent = this;
        event.preventDefault();
        this.props.form.validateFields((err, values) => {
            if(!err) {
                let dict = Object.assign({}, values, {
                    date: parent.props.date,
                    isRealize: false,
                    usersId: parent.state.usersId,
                });
                addNotification(dict).then(response => {
                    parent.props.onNotifsChange(true, "create", dict);
                }).catch(error => {
                    parent.props.onNotifsChange(false, "create", dict);
                });
            }
        });
    }

    handleUsersChange(usersId) {
        console.log("USERS CHANGE", usersId);
        this.setState({
            usersId: usersId,
        });
    }

    render() {
        const {getFieldDecorator} = this.props.form;
        const initialUsers = {};
        initialUsers[this.props.username] = this.props.userId;

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
                <NotifUsersForm initialUsers={initialUsers}
                                onUsersChange={this.handleUsersChange}/>
                <Button htmlType="submit" type="primary">Submit</Button>
            </Form>
        );
    }
}

/**
 * props = {
 *   date:String, username:String, userId:Number,
 *   onNotifsChange:Function(success:Boolean, action:String, notif:Notification)
 * }
 */
export const WrappedNotifCreateForm = Form.create()(NotifCreateForm);