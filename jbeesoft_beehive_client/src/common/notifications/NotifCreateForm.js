import React from "react";
import {addNotification} from "../../util/APIUtils";
import {Button, Form, Input} from "antd";

const FormItem = Form.Item;

/**
 * props = {
 *   date:String, usersId:List<Number>,
 *   onNotifsChange:Function(success:Boolean, action:String, notif:Notification)
 * }
 */
class NotifCreateForm extends React.Component {

    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        let parent = this;
        event.preventDefault();
        this.props.form.validateFields((err, values) => {
            if(!err) {
                let dict = Object.assign({}, values, {
                    date: parent.props.date,
                    isRealize: false,
                    usersId: parent.props.usersId,
                });
                addNotification(dict).then(response => {
                    parent.props.onNotifsChange(true, "create", dict);
                }).catch(error => {
                    parent.props.onNotifsChange(false, "create", dict);
                });
            }
        });
    }

    // TODO pole usersId w formularzu

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
 * props = {
 *   date:String, usersId:List<Number>,
 *   onNotifsChange:Function(success:Boolean, action:String, notif:Notification)
 * }
 */
export const WrappedNotifCreateForm = Form.create()(NotifCreateForm);