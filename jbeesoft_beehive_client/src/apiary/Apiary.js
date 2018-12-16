import React, { Component } from 'react';
import './ApiaryList.css';
import { Form, Input, Button, notification, Modal, Table, Menu, Dropdown } from 'antd';
import { withRouter } from 'react-router-dom';
import { addHive, getAllApiaries } from '../util/APIUtils';
const FormItem = Form.Item;


class Apiary extends Component {
	render() {

		const WrappedAddHiveForm = Form.create()(AddHiveForm)
		return (
			<div className="apiary-list">
				<Button style={{float: 'right'}} type="primary" onClick={this.showModal}>New hive</Button>
				<h1>Hives in apiary <span className='apiary-name'>{this.props.match.params.id}</span>:</h1>
				<WrappedAddHiveForm
					wrappedComponentRef={this.saveFormRef}
					visible={this.state.visible}
					onCancel={this.handleCancel}
					onCreate={this.handleCreate}
				/>
			</div>
		)
	}

	constructor(props) {
		super(props);
		const date = new Date();
		this.state = {
			apiaries: [],
			page: 0,
			size: 10,
			totalElements: 0,
			totalPages: 0,
			last: true,
			currentVotes: [],
			isLoading: false,
			date: date,
			oldDate: date
		};
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
			addHive(apiaryRequest)
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

class AddHiveForm extends Component {
	render() {
		const {
			visible, onCancel, onCreate, form,
			} = this.props;

		const hiveType = [{id: 1, title: 'aaa'}, {id: 2, title: 'bbb'}, {id: 3, title: 'ccc'}];
		const hiveTypeDrop = [];
		hiveType.forEach((id, title) => {
			hiveTypeDrop.push(<Menu.Item key={id}>{title}</Menu.Item>)
		});
		const { getFieldDecorator } = form;
		return (
		<Modal
			visible={visible}
			title="Create a new hive"
			okText="Create"
			onCancel={onCancel}
			onOk={onCreate}
		>
			<Form layout="vertical">
				<FormItem label="Hive name:">
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
				<FormItem label="Box number:">
				{getFieldDecorator('boxNumber', {
					rules: [{ required: true, message: 'This field is required.' }],
				})(
					<Input
						name="boxNumber"
						type="text"
						placeholder="Box number" />					
				)}
				</FormItem>
			</Form>
		</Modal>
		);
	}
}

export default withRouter(Apiary);