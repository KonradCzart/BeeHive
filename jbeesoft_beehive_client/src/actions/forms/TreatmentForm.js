import React, { Component } from 'react';
import './ApiaryList.css';
import { Form, Input, InputNumber, Modal } from 'antd';
import LoadingIndicator  from '../../common/LoadingIndicator';
const FormItem = Form.Item;


class TreatmentForm extends Component {

	_isMounted = false;

	constructor(props) {
		super(props);
		const date = new Date();
		this.state = {
			date: date,
			isLoading: false,
			oldDate: date
		}
	}

	render() {

		const {
			visible, onCancel, onCreate, form
		} = this.props;

		const { getFieldDecorator } = form;

		return (
		<Modal
			visible={visible}
			title="Treatment"
			okText="Submit"
			onCancel={onCancel}
			onOk={onCreate}
		>
			<Form layout="vertical">

				<FormItem label="Disease type:">
				{getFieldDecorator('deseaseType', {
					rules: [{ required: true, message: 'This field is required.' }],
				})(
					<Input
						name="deseaseType"
						type="text"
						placeholder="Disease type" />					
				)}
				</FormItem>

				<FormItem label="Applied medicine:">
				{getFieldDecorator('appliedMedicine', {
					rules: [{ required: true, message: 'This field is required.' }],
				})(
					<Input
						name="appliedMedicine"
						type="text"
						placeholder="Applied medicine" />					
				)}
				</FormItem>

				<FormItem label="Dose:">
				{getFieldDecorator('dose', {
					rules: [{ required: true, message: 'This field is required.' }]
				})(
					<InputNumber
						name="dose"
						min={0}
						step={0.01}
						placeholder="Dose" 
						style={{ width: '100%' }}
					/>					
				)}
				</FormItem>

				<FormItem label="Price:">
				{getFieldDecorator('price', {
					rules: [{ required: true, message: 'This field is required.' }]
				})(
					<InputNumber
						name="price"
						min={0}
						step={0.01}
						placeholder="Price" 
						style={{ width: '100%' }}
					/>					
				)}
				</FormItem>
				
				{
					this.state.isLoading ? 
					<LoadingIndicator /> : null
				}

			</Form>
		</Modal>
		);
	}

	componentDidUpdate(nextProps) {
		if(this.state.date !== this.state.oldDate) {
			const date = this.state.date;
			this.setState({
				oldDate: date
			})
		}
	}

	componentDidMount() {
		this._isMounted = true;
	}

	componentWillUnmount() {
		this._isMounted = false;
	}
}

export default TreatmentForm;