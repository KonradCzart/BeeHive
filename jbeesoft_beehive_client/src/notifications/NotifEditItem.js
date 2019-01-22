import {Button, Form, Input} from "antd";
import React from "react";
import {modifyNotification} from "../util/APIUtils";

const FormItem = Form.Item;

/**
 * props = {notif:Notification, onNotifsChange:Function, onDecline:Function}
 * onNotifsChange = Function(success:Boolean, action:String, notif:Notification)
 */
class NotifEditItem extends React.Component {

    constructor(props) {
        super(props);
        this.handleApply = this.handleApply.bind(this);
        this.handleDecline = this.handleDecline.bind(this);
    }

    handleApply(event) {
        let parent = this;
        event.preventDefault();
        this.props.form.validateFields((errors, values) => {
            if(!errors) {
                let dict = Object.assign({}, parent.props.notif, values);
                modifyNotification(dict).then(response => {
                    parent.props.onNotifsChange(true, "modify", dict);
                    parent.handleDecline();
                }).catch(error => {
                    parent.props.onNotifsChange(false, "modify", parent.props.notif);
                });
            }

        });
    }

    handleDecline() {
        this.props.onDecline();
    }

    render() {
        const {getFieldDecorator} = this.props.form;

        return (
            <Form onSubmit={this.handleApply}
                  className={"bl h-1-2 wrap-content b-marg-32"}>
                <FormItem label="Title">
                    {getFieldDecorator("title", {
                        initialValue: this.props.notif.title,
                        rules: [
                            {
                                required: true,
                                message: "Please input the title."
                            },
                        ],
                    })(<Input/>)}
                </FormItem>
                <FormItem label="Description">
                    {getFieldDecorator("description", {
                        initialValue: this.props.notif.description
                    })(<Input/>)}
                </FormItem>
                <div className={"bl h-match-parent v-wrap-content"}>
                    <div className={"in-bl h-1-2 v-wrap-content"}>
                        <Button type="secondary" onClick={this.handleDecline}>
                            Cancel
                        </Button>
                    </div>
                    <div className={"in-bl h-1-2 v-wrap-content"}>
                        <Button type="primary" htmlType="submit">
                            Apply
                        </Button>
                    </div>
                </div>
            </Form>
        );
    }
}

/**
 * props = {notif:Notification, onNotifsChange:Function, onDecline:Function}
 * onNotifsChange = Function(success:Boolean, action:String, notif:Notification)
 */
export const WrappedNotifEditItem = Form.create()(NotifEditItem);