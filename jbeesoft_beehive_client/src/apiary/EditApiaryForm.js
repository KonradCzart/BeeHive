import React, { Component } from 'react';
import './ApiaryList.css';
import { Form, Input, Modal } from 'antd';
const FormItem = Form.Item;


class EditApiaryForm extends Component {
	render() {
		const {
			visible, onCancel, onCreate, form,
		} = this.props;
		const {getFieldDecorator} = form;
		return (
			<Modal
				visible={visible}
				title="Edit apiary"
				okText="Edit"
				onCancel={onCancel}
				onOk={onCreate}
			>
				<Form layout="vertical">
					<FormItem label="Apiary name:">
						{getFieldDecorator('name', {
							rules: [{
								required: true,
								message: 'This field is required.'
							}],
							initialValue: this.props.apiaryData.name
						})(
							<Input
								name="name"
								type="text"
								placeholder="Name"/>
						)}
					</FormItem>
					<FormItem label="Country:">
						{getFieldDecorator('country', {
							rules: [{
								required: true,
								message: 'This field is required.'
							}],
							initialValue: this.props.apiaryData.country
						})(
							<Input
								name="country"
								type="text"
								placeholder="Country"/>
						)}
					</FormItem>
					<FormItem label="City:">
						{getFieldDecorator('city', {
							rules: [{
								required: true,
								message: 'This field is required.'
							}],
							initialValue: this.props.apiaryData.city
						})(
							<Input
								name="city"
								type="text"
								placeholder="City"/>
						)}
					</FormItem>
				</Form>
			</Modal>
		);
	}
}

export default EditApiaryForm;