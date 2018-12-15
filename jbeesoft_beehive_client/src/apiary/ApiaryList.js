import React, { Component } from 'react';
import { addApiary, getAllApiaries } from '../util/APIUtils';
import './ApiaryList.css';
import { Form, Input, Button, notification, Modal, Table } from 'antd';
import { withRouter } from 'react-router-dom';
import { NotificationCalendar} from "../common/NotificationCalendar";

const FormItem = Form.Item;

class ApiaryList extends Component {

	constructor(props) {
		super(props);
		this.state = {
			apiaries: [],
			page: 0,
			size: 10,
			totalElements: 0,
			totalPages: 0,
			last: true,
			currentVotes: [],
			isLoading: false
		};
		this.loadApiaryList = this.loadApiaryList.bind(this);
	}

	render() {
		const columns = [{
			title: 'Name',
			dataIndex: 'name',
			key: 'name'
		}, {
			title: 'Country',
			dataIndex: 'country',
			key: 'country',
		}, {
			title: 'City',
			dataIndex: 'city',
			key: 'city',
		}];
		const WrappedAddApiaryForm = Form.create()(AddApiaryForm);
		return (
			<div className="apiary-list">
				<Button style={{float: 'right'}} type="primary" onClick={this.showModal}>New apiary</Button>
				<WrappedAddApiaryForm
					wrappedComponentRef={this.saveFormRef}
					visible={this.state.visible}
					onCancel={this.handleCancel}
					onCreate={this.handleCreate}
					userID={this.props.currentUser.id}
					/>
				<h1>Your apiaries:</h1>
				<Table className="col-1-2" rowKey={record => record.id} columns={columns} dataSource={this.state.apiaries} />
			</div>
		);
	}

	loadApiaryList(page = 0, size = 30) {
		let promise;

		promise = getAllApiaries();

		if(!promise) {
			return;
		}

		this.setState({
			isLoading: true
		});

		promise			
		.then(response => {

			this.setState({
				apiaries: response,
				isLoading: false
			})
		}).catch(error => {
			this.setState({
				isLoading: false
			})
		});	
		
	}

	componentDidMount() {
		this.loadApiaryList();
	}

	componentDidUpdate(nextProps) {
        if(this.props.isAuthenticated !== nextProps.isAuthenticated) {
            // Reset State
            this.setState({
                polls: [],
                page: 0,
                size: 10,
                totalElements: 0,
                totalPages: 0,
                last: true,
                currentVotes: [],
                isLoading: false
            });    
            this.loadPollList();
        }
    }

	state = {
		visible: false,
	};

	showModal = () => {
		this.setState({ visible: true });
	}

	handleCancel = () => {
		this.setState({ visible: false });
	}

	handleCreate = () => {
		const form = this.formRef.props.form;
		form.validateFields((err, values) => {
			if (err) {
				return;
			}

			const apiaryRequest = values;
			apiaryRequest.owner_id=this.props.currentUser.id;
			form.resetFields();
			this.setState({ visible: false });
			addApiary(apiaryRequest)
			.then(response => {
					notification.success({
						message: 'BeeHive App',
						description: apiaryRequest.name + " created successfully!"
					});
				}).catch(error => {
					if(error.status === 401) {
						notification.error({
							message: 'BeeHive App',
							description: 'Data is incorrect. Please try again!'
						});					
					} else {
						notification.error({
							message: 'BeeHive App',
							description: 'Sorry! Something went wrong. Please try again!'
						});											
					}
				});
		});
	}

	saveFormRef = (formRef) => {
		this.formRef = formRef;
	}
}


class AddApiaryForm extends Component {
	render() {
		const {
			visible, onCancel, onCreate, form,
			} = this.props;
		const { getFieldDecorator } = form;
		return (
		<Modal
			visible={visible}
			title="Create a new apiary"
			okText="Create"
			onCancel={onCancel}
			onOk={onCreate}
		>
			<Form layout="vertical">
				<FormItem label="Apiary name:">
				{getFieldDecorator('name', {
					rules: [{ required: true, message: 'This field is required.' }],
				})(
					<Input
						name="name"
						type="text"
						placeholder="Name" />					
				)}
				</FormItem>
				<FormItem label="Country:">
				{getFieldDecorator('country', {
					rules: [{ required: true, message: 'This field is required.' }],
				})(
					<Input
						name="country"
						type="text"
						placeholder="Country" />					
				)}
				</FormItem>
				<FormItem label="City:">
				{getFieldDecorator('city', {
					rules: [{ required: true, message: 'This field is required.' }],
				})(
					<Input
						name="city"
						type="text"
						placeholder="City" />					
				)}
				</FormItem>
			</Form>
		</Modal>
		);
	}
}

export default withRouter(ApiaryList);