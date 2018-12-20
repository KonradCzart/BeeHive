import {Form, Icon, Input} from "antd";
import React from "react";
import {modifyNotification} from "../../util/APIUtils";
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

    handleApply() {
        let parent = this;
        this.props.form.validateFields((errors, values) => {
            if(!errors) {
                let dict = Object.assign({}, parent.props.notif, values);
                modifyNotification(dict).then(response => {
                    parent.props.onNotifsChange(true, "modify", dict);
                }).catch(error => {
                    parent.props.onNotifsChange(false, "modify", parent.props.notif);
                });
            }

        });
    }

    handleDecline() {
        this.props.onDecline();
    }

    // TODO pole formularza usersId

    render() {
        const {getFieldDecorator} = this.props.form;

        return (
            <Form
                className={"fl v-wrap-content h-match-parent v-center-child b-marg-32"}>
                <div className={"in-bl to-left wrap-content"}>
                    <FormItem label="Title">
                        {getFieldDecorator("title", {})(<Input/>)}
                    </FormItem>
                    <FormItem label="Description">
                        {getFieldDecorator("description", {})(<Input/>)}
                    </FormItem>
                </div>
                <Icon onClick={this.handleApply} type="check"
                      className={"in-bl to-right wrap-content"}/>
                <Icon onClick={this.handleDecline} type="close"
                      className={"in-bl to-right wrap-content"}/>
            </Form>
        );
    }
}
/**
 * props = {notif:Notification, onNotifsChange:Function, onDecline:Function}
 * onNotifsChange = Function(success:Boolean, action:String, notif:Notification)
 */
export const WrappedNotifEditItem = Form.create()(NotifEditItem);