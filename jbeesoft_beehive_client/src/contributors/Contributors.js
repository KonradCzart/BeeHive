import React from "react";
import {Button, notification} from "antd";
import {getContributors} from "../util/APIUtils";
import "./Contributors.css";
import {ContributorItem} from "./ContributorItem";
import {AddContributorModal} from "./AddContributorModal";

/**
 * ## props:
 * * apiaryId : _Number_;
 * * userId: _Number_;
 */
export class Contributors extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            addModalVisible: false,
            ownerPrivilege: false,
            loading: true,
            error: false,
            contributors: [],
        };
        this.userListJSX = this.userListJSX.bind(this);
        this.refreshContributors = this.refreshContributors.bind(this);
        this.showAddModal = this.showAddModal.bind(this);
        this.dismissAddModal = this.dismissAddModal.bind(this);
        this.validateUser = this.validateUser.bind(this);
        this.handleAddSuccess = this.handleAddSuccess.bind(this);
        this.handleAddError = this.handleAddError.bind(this);
        this.handleModifySuccess = this.handleModifySuccess.bind(this);
        this.handleModifyError = this.handleModifyError.bind(this);
        this.handleRemoveSuccess = this.handleRemoveSuccess.bind(this);
        this.handleRemoveError = this.handleRemoveError.bind(this);
        this.refreshContributors();
    }

    handleModifySuccess(username) {
        notification.success({
            message: username,
            description: "Successfully modified privileges.",
        });
        this.refreshContributors();
    }

    handleModifyError(username) {
        notification.error({
            message: username,
            description: "An error occured while modifying privileges.",
        });
    }

    handleRemoveSuccess(username) {
        notification.success({
            message: username,
            description: "Successfully removed contributor.",
        });
        this.refreshContributors();
    }

    handleRemoveError(username) {
        notification.error({
            message: username,
            description: "An error occured while removing contributor.",
        });
    }

    refreshContributors() {
        const parent = this;
        getContributors(this.props.apiaryId).then(contributors => {

            let ownerPrivilege = false;
            for(let contributor of contributors) {
                if(contributor.userId === parent.props.userId)
                    for(let privilege of contributor.privileges)
                        if(privilege.name === "OWNER_PRIVILEGE") {
                            ownerPrivilege = true;
                            break;
                        }
            }

            parent.setState({
                ownerPrivilege: ownerPrivilege,
                loading: false,
                error: false,
                contributors: contributors,
            });
        }).catch(() => {
            parent.setState({
                loading: false,
                error: true,
            });
        });
    }

    userListJSX() {
        const parent = this;
        const jsx = [];
        this.state.contributors.forEach((contributor, idx) => {
            jsx.push((<ContributorItem key={idx}
                onModifySuccess={this.handleModifySuccess}
                onModifyError={this.handleModifyError}
                onRemoveSuccess={this.handleRemoveSuccess}
                onRemoveError={this.handleRemoveError}
                ownerPrivilege={this.state.ownerPrivilege && parent.props.userId !== contributor.userId}
                contributor={contributor} apiaryId={this.props.apiaryId}/>));
        });
        return (<div className="contrItems">{jsx}</div>);
    }

    showAddModal() {
        this.setState({
            addModalVisible: true,
        });
    }

    dismissAddModal() {
        this.setState({
            addModalVisible: false,
        });
    }

    validateUser(rule, value, callback) {
        for(let contributor of this.state.contributors)
            if(contributor.userId === value.id ||
                contributor.username === value.username) {
                return callback("User is already in the contributors")
            }
        return callback();
    }

    handleAddSuccess(username) {
        this.setState({
            addModalVisible: false,
        });
        notification.success({
            message: username,
            description: "Successfully added a contributor."
        });
        this.refreshContributors();
    }

    handleAddError(username) {
        notification.error({
            message: username,
            description: "An error occured while adding a contributor",
        });
    }

    render() {
        const userListJSX = this.userListJSX();

        return (
            <div className="contributors">
                <h2>Contributors:</h2>
                {userListJSX}
                <Button disabled={!this.state.ownerPrivilege} type="primary"
                    htmlType="button" onClick={this.showAddModal}>
                    Add contributor
                </Button>
                <AddContributorModal visible={this.state.addModalVisible}
                    apiaryId={this.props.apiaryId}
                    onCancel={this.dismissAddModal}
                    validateUser={this.validateUser}
                    onAddSuccess={this.handleAddSuccess}
                    onAddError={this.handleAddError}/>
            </div>
        );
    }
}

