import React from "react";
import {grantPrivileges} from "../util/APIUtils";
import {Icon, Modal} from "antd";
import {ModifyContributorModal} from "./ModifyContributorModal";

/**
 * __props__:
 * - contributor: ___Contributor___,
 * - ownerPrivilege: _Boolean_,
 * - apiaryId: _Number_,
 * - onModifySuccess: _Function_(username: _String_),
 * - onModifyError: _Function_(username: _String_),
 * - onRemoveSuccess: _Function_(username: _String_),
 * - onRemoveError: _Function_(username: _String_)
 *
 * __Contributor__:
 * - userId: _Number_,
 * - username: _String_,
 * - email: _String_,
 * - privileges: _Array_<__Privilege__>
 *
 * __Privilege__:
 * - id: _Number_
 * - name: _String_
 * - readableName: _String_
 * - description: _String_
 */
export class ContributorItem extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            modifyModal: false,
            ownerPrivilege: false,
            privileges: this.props.contributor.privileges,
        };
        this.readablePrivilegesJSX = this.readablePrivilegesJSX.bind(this);
        this.showModal = this.showModal.bind(this);
        this.dismiss = this.dismiss.bind(this);
        this.removeContributor = this.removeContributor.bind(this);
        this.contributorPrivileges = this.contributorPrivileges.bind(this);
        this.handleModifySuccess = this.handleModifySuccess.bind(this);
    }

    handleModifySuccess(username) {
        this.setState({modifyModal: false});
        this.props.onModifySuccess(username);
    }

    showModal() {
        this.setState({modifyModal: true});
    }

    dismiss() {
        this.setState({modifyModal: false});
    }

    modifyPrivilege() {
        const parent = this;
        const dict = {
            targetUser: this.props.contributor.userId,
            privileges: this.state.privileges,
            affectedApiaryId: this.props.apiaryId,
        };
        grantPrivileges(dict).then(response => {
            parent.setState({modifyModal: false});
            parent.props.onModifySuccess(parent.props.contributor.username);
        }).catch(errors => {
            parent.props.onModifySuccess(parent.props.contributor.username);
        });
    }

    removeContributor() {
        const parent = this;
        const dict = {
            targetUser: this.props.contributor.userId,
            privileges: [],
            affectedApiaryId: this.props.apiaryId,
        };
        grantPrivileges(dict).then(response => {
            parent.props.onRemoveSuccess(parent.props.contributor.username);
        }).catch(errors => {
            parent.props.onRemoveError(parent.props.contributor.username);
        })
    }

    readablePrivilegesJSX() {
        const jsx = [];
        this.props.contributor.privileges.forEach((privilege, idx) => {
            jsx.push((<div key={idx}>{privilege.readableName}</div>));
        });
        return (<div>{jsx}</div>);
    }

    contributorPrivileges() {
        const map = {
            ownerPrivilege: false,
            hiveEditing: false,
            apiaryEditing: false,
            hiveStatsReading: false,
            apiaryStatsReading: false,
        };
        for(let privilege of this.props.contributor.privileges)
            map[privilege.name.toLowerCase()
                .replace(/_\w/g, (match) => match[1].toUpperCase())] = true;
        return map;
    }

    render() {
        const privilegesJSX = this.readablePrivilegesJSX();
        const initPrivileges = this.contributorPrivileges();
        const display = this.props.ownerPrivilege? "inline-block" : "none";

        return (
            <div className="contrItem">
                <div className="contrItem-userInfo">
                    <h3>@{this.props.contributor.username}</h3>
                    <h4>{this.props.contributor.email}</h4>
                    {privilegesJSX}
                </div>
                <Icon style={{display: display}} type="solution"
                    className="contrItem-solutionIcon"
                    onClick={this.showModal}/>
                <Icon style={{display: display}} type="delete"
                    className="contrItem-deleteIcon"
                    onClick={this.removeContributor}/>
                <Modal visible={this.state.modifyModal}
                    onOk={this.modifyPrivilege} onCancel={this.dismiss}>
                    <ModifyContributorModal
                        username={this.props.contributor.username}
                        visible={this.state.modifyModal} onCancel={this.dismiss}
                        initialPrivileges={initPrivileges}
                        onModifySuccess={this.handleModifySuccess}
                        onModifyError={this.props.onModifyError}
                        userId={this.props.contributor.userId}
                        apiaryId={this.props.apiaryId}/>
                </Modal>
            </div>
        );
    }
}