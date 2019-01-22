import React from "react";
import {grantPrivileges} from "../util/APIUtils";
import {Checkbox, Form, Modal} from "antd";
import {UserAutoComplete} from "../common/UserAutoComplete";

const FormItem = Form.Item;

/**
 * __props__:
 * - visible: _Boolean_,
 * - onCancel: _Function_,
 * - validateUser: _Function_(rule, __user__, callback),
 * - onAddSuccess: _Function_(username: _String_),
 * - onAddError: _Function_(username: _String_),
 * - apiaryId: _Number_
 *
 * __user__:
 * - id: _Number_,
 * - username: _String_
 */
class RawAddContributorModal extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            help: "",
            validateStatus: "success",
            validUsername: false,
        };
        this.addContributor = this.addContributor.bind(this);
        this.handleUserSelect = this.handleUserSelect.bind(this);
        this.validateCheckboxes = this.validateCheckboxes.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.valid = this.valid.bind(this);
    }

    valid() {
        return this.state.validateStatus === "success" && this.state.validUsername;
    }

    handleChange(event) {
        const dict = {};
        dict[event.target.id] = event.target.checked;
        this.props.form.setFieldsValue(dict);
        this.validateCheckboxes();
    }

    addContributor(event) {
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
                    targetUser: values.user.id,
                    privileges: privileges,
                    affectedApiaryId: parent.props.apiaryId,
                };
                grantPrivileges(dict).then(response => {
                    parent.props.onAddSuccess(values.user.username);
                }).catch(error => {
                    parent.props.onAddError(values.user.username);
                });
            }
        })
    }

    handleUserSelect(id, username) {
        const parent = this;
        this.props.form.setFieldsValue({
            user: {id: id, username: username},
        });
        this.props.form.validateFields(errors => {
            parent.setState({validUsername: !errors});
        });
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

    render() {
        const {getFieldDecorator} = this.props.form;
        const okButtonProps = {disabled: !this.valid()};

        return (
            <Modal visible={this.props.visible} onCancel={this.props.onCancel}
                onOk={this.addContributor} okButtonProps={okButtonProps}>
                <Form>
                    <FormItem label="Username">
                        {getFieldDecorator("user", {
                            rules: [{validator: this.props.validateUser}],
                        })(<UserAutoComplete
                            onUserSelect={this.handleUserSelect}/>)}
                    </FormItem>
                    <FormItem help={this.state.help}
                        validateStatus={this.state.validateStatus}>
                        {getFieldDecorator("ownerPrivilege", {
                            valuePropName: "checked",
                            initialValue: false,
                        })(<Checkbox onChange={this.handleChange}>Owner
                            privilege</Checkbox>)}
                        {getFieldDecorator("hiveEditing", {
                            valuePropName: "checked",
                            initialValue: true,
                        })(<Checkbox onChange={this.handleChange}>Hive
                            editing</Checkbox>)}
                        {getFieldDecorator("apiaryEditing", {
                            valuePropName: "checked",
                            initialValue: true,
                        })(<Checkbox onChange={this.handleChange}>Apiary
                            editing</Checkbox>)}
                        {getFieldDecorator("hiveStatsReading", {
                            valuePropName: "checked",
                            initialValue: true,
                        })(<Checkbox onChange={this.handleChange}>Hive stats
                            reading</Checkbox>)}
                        {getFieldDecorator("apiaryStatsReading", {
                            valuePropName: "checked",
                            initialValue: true,
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
 * - visible: _Boolean_,
 * - onCancel: _Function_,
 * - validateUser: _Function_(rule, __user__, callback),
 * - onAddSuccess: _Function_(username: _String_),
 * - onAddError: _Function_(username: _String_),
 * - apiaryId: _Number_
 *
 * __user__:
 * - id: _Number_,
 * - username: _String_
 */
export const AddContributorModal = Form.create()(RawAddContributorModal);