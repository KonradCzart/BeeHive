import React, { Component } from 'react';
import { addApiary } from '../util/APIUtils';
import './ApiaryList.css';
import { Form, Input, Button, notification, Modal } from 'antd';
const FormItem = Form.Item;

class ApiaryList extends Component {

	render() {
		const WrappedAddApiaryForm = Form.create()(AddApiaryForm)
		return (
			<div className="apiary-list">
				<Button type="primary" onClick={this.showModal}>New apiary</Button>
				<WrappedAddApiaryForm
					wrappedComponentRef={this.saveFormRef}
					visible={this.state.visible}
					onCancel={this.handleCancel}
					onCreate={this.handleCreate}
					userID={this.props.currentUser.id}
          		/>
			</div>
		);
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

export default ApiaryList;