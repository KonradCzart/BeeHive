import React from "react";
import {grantPrivileges} from "../util/APIUtils";
import {Checkbox, Form, Modal} from "antd";

const FormItem = Form.Item;

/**
 * __props__:
 * - username: _String_,
 * - visible: _Boolean_,
 * - onCancel: _Function_,
 * - __initialPrivileges__,
 * - onModifySuccess: _Function_(username: _String_),
 * - onModifyError: _Function_(username: _String_),
 * - userId: _Number_,
 * - apiaryId: _Number_
 *
 * __initialPrivileges__:
 * - ownerPrivilege: _Boolean_,
 * - hiveEditing: _Boolean_,
 * - apiaryEditing: _Boolean_,
 * - hiveStatsReading: _Boolean_,
 * - apiaryStatsReading: _Boolean_
 */
class RawModifyContributorModal extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            help: "",
            validateStatus: "success",
        };
        this.modifyContributor = this.modifyContributor.bind(this);
        this.validateCheckboxes = this.validateCheckboxes.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.valid = this.valid.bind(this);
    }

    valid() {
        return this.state.validateStatus === "success";
    }

    handleChange(event) {
        const dict = {};
        dict[event.target.id] = event.target.checked;
        this.props.form.setFieldsValue(dict);
        this.validateCheckboxes();
    }

    validateCheckboxes() {
        const fields = this.props.form.getFieldsValue(["ownerPrivilege",
            "hiveEditing",
            "apiaryEditing",
            "hiveStatsReading",
            "apiaryStatsReading"]);
        for(let key in fields)
            if(fields[key]) {
                this.setState({
                    validateStatus: "success",
                    help: "",
                });
                return;
            }
        this.setState({
            validateStatus: "error",
            help: "Please check at least one privilege.",
        });
    }

    modifyContributor(event) {
        event.preventDefault();
        const parent = this;
        this.props.form.validateFields((errors, values) => {
            if(!errors) {
                const privileges = [];

                if(values.ownerPrivilege)
                    privileges.push("OWNER_PRIVILEGE");
                if(values.hiveEditing)
                    privileges.push("HIVE_EDITING");
                if(values.apiaryEditing)
                    privileges.push("APIARY_EDITING");
                if(values.hiveStatsReading)
                    privileges.push("HIVE_STATS_READING");
                if(values.apiaryStatsReading)
                    privileges.push("APIARY_STATS_READING");

                const dict = {
                    targetUser: parent.props.userId,
                    privileges: privileges,
                    affectedApiaryId: parent.props.apiaryId,
                };
                grantPrivileges(dict).then(response => {
                    parent.props.onModifySuccess(parent.props.username);
                }).catch(error => {
                    parent.props.onModifyError(parent.props.username);
                });
            }
        })
    }

    render() {
        const {getFieldDecorator} = this.props.form;
        const okButtonProps = {disabled: !this.valid()};
        const initPrivileges = this.props.initialPrivileges;

        return (
            <Modal visible={this.props.visible} onCancel={this.props.onCancel}
                onOk={this.modifyContributor} okButtonProps={okButtonProps}>
                <h2>Privileges of @{this.props.username}</h2>
                <Form>
                    <FormItem help={this.state.help}
                        validateStatus={this.state.validateStatus}>
                        {getFieldDecorator("ownerPrivilege", {
                            valuePropName: "checked",
                            initialValue: initPrivileges.ownerPrivilege,
                        })(<Checkbox onChange={this.handleChange}>Owner
                            privilege</Checkbox>)}
                        {getFieldDecorator("hiveEditing", {
                            valuePropName: "checked",
                            initialValue: initPrivileges.hiveEditing,
                        })(<Checkbox onChange={this.handleChange}>Hive
                            editing</Checkbox>)}
                        {getFieldDecorator("apiaryEditing", {
                            valuePropName: "checked",
                            initialValue: initPrivileges.apiaryEditing,
                        })(<Checkbox onChange={this.handleChange}>Apiary
                            editing</Checkbox>)}
                        {getFieldDecorator("hiveStatsReading", {
                            valuePropName: "checked",
                            initialValue: initPrivileges.hiveStatsReading,
                        })(<Checkbox onChange={this.handleChange}>Hive stats
                            reading</Checkbox>)}
                        {getFieldDecorator("apiaryStatsReading", {
                            valuePropName: "checked",
                            initialValue: initPrivileges.apiaryStatsReading,
                        })(<Checkbox onChange={this.handleChange}>Apiary stats
                            reading</Checkbox>)}
                    </FormItem>
                </Form>
            </Modal>
        );
    }
}

/**
 * __props__:
 * - username: _String_,
 * - visible: _Boolean_,
 * - onCancel: _Function_,
 * - __initialPrivileges__,
 * - onModifySuccess: _Function_(username: _String_),
 * - onModifyError: _Function_(username: _String_),
 * - userId: _Number_,
 * - apiaryId: _Number_
 *
 * __initialPrivileges__:
 * - ownerPrivilege: _Boolean_,
 * - hiveEditing: _Boolean_,
 * - apiaryEditing: _Boolean_,
 * - hiveStatsReading: _Boolean_,
 * - apiaryStatsReading: _Boolean_
 */
export const ModifyContributorModal = Form.create()(RawModifyContributorModal);