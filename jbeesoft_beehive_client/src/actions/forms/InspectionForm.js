import React, { Component } from 'react';
import './ApiaryList.css';
import { Form, Input, InputNumber, Modal, Checkbox } from 'antd';
import LoadingIndicator  from '../../common/LoadingIndicator';
const FormItem = Form.Item;


class InspectionForm extends Component {

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
			title="Inspection"
			okText="Submit"
			onCancel={onCancel}
			onOk={onCreate}
		>
			<Form layout="vertical">

				<FormItem label="Is maggot present:">
				{
					getFieldDecorator('isMaggotPresent', {
					rules: [{ required: true, message: 'This field is required!' }],
					initialValue: false
				})(
					<Checkbox
						name="isMaggotPresent" />
				)}
				</FormItem>

				<FormItem label="Is lair present:">
				{
					getFieldDecorator('isLairPresent', {
					rules: [{ required: true, message: 'This field is required!' }],
					initialValue: false
				})(
					<Checkbox
						name="isLairPresent" />
				)}
				</FormItem>

				<FormItem label="Hive strength:">
				{getFieldDecorator('hiveStrength', {
					rules: [{ required: true, message: 'This field is required.' }]
				})(
					<InputNumber
						name="hiveStrength"
						min={0}
						placeholder="Hive strength" 
						style={{ width: '100%' }}
					/>					
				)}
				</FormItem>

				<FormItem label="Frames with wax foundation:">
				{getFieldDecorator('framesWithWaxFoundation', {
					rules: [{ required: true, message: 'This field is required.' }]
				})(
					<InputNumber
						name="framesWithWaxFoundation"
						min={0}
						placeholder="Frames with wax foundation" 
						style={{ width: '100%' }}
					/>					
				)}
				</FormItem>

				<FormItem label="Description:">
				{getFieldDecorator('decription', {
					rules: [{ required: true, message: 'This field is required.' }],
				})(
					<Input
						name="decription"
						type="text"
						placeholder="Description" />					
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

export default InspectionForm;