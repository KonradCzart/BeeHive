import React, { Component } from 'react';
import './ApiaryList.css';
import { Form, Input, Modal, Select, DatePicker, Checkbox, InputNumber } from 'antd';
import { getQueenRaces } from '../util/APIUtils';
import LoadingIndicator  from '../common/LoadingIndicator';
const FormItem = Form.Item;
const Option = Select.Option;


class AddQueenForm extends Component {

	_isMounted = false;

	constructor(props) {
		super(props);
		const date = new Date();
		this.state = {
			queenRaces: [],
			date: date,
			isLoading: false,
			oldDate: date
		}
	}

	render() {

		const {
			visible, onCancel, onCreate, form
		} = this.props;

		const queenRaceDrop = this.state.queenRaces.map((type =>
				<Option key={type.id} value={type.id}>{type.value}</Option>
		));

		const { getFieldDecorator } = form;
		return (
		<Modal
			visible={visible}
			title="Add queen to hive"
			okText="Add"
			onCancel={onCancel}
			onOk={onCreate}
		>
			<Form layout="vertical">
				
				{
					!this.state.isLoading && this.state.queenRaces.length > 0 ? (
					<FormItem label="Race:">
					{getFieldDecorator('raceId', {
						rules: [{ required: true, message: 'This field is required.' }],
					})(
						<Select name='raceId' placeholder='Race'>
							{queenRaceDrop}
						</Select>				
					)}
					</FormItem> ) : null
				}
				{
					this.state.isLoading ? 
					<LoadingIndicator /> : null
				}

				<FormItem label="Color:">
				{getFieldDecorator('color', {
					rules: [{ required: true, message: 'This field is required.' }],
				})(
					<Input
						name="color"
						type="text"
						placeholder="Color" />					
				)}
				</FormItem>

				<FormItem label="Date of birth:">
				{getFieldDecorator('age', {
					rules: [{ type: 'object', required: true, message: 'Please select time!' }],
				})(
					<DatePicker
						format="YYYY-MM-DD"
						name="age"
						type="text"
						placeholder="Select date of birth"
						style={{ width: '100%' }}
					/>
				)}
				</FormItem>

				<FormItem label="Is reproducing:">
				{getFieldDecorator('isReproducting', {
					rules: [{ required: false, message: 'This field is required!' }],
					initialValue: false
				})(
					<Checkbox
						name="isReproducting" />
				)}
				</FormItem>

				<FormItem label="Description:">
				{getFieldDecorator('description', {
					rules: [{ required: true, message: 'This field is required.' }],
				})(
					<Input
						name="description"
						type="text"
						placeholder="Description" />					
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
			</Form>
		</Modal>
		);
	}

	loadQueenRaces() {
		let promise = getQueenRaces();

		if(!promise) {
			return;
		}

		this.setState({isLoading: true});

		promise
		.then(response => {
			if(this._isMounted) {
				this.setState({
					queenRaces: response,
					isLoading: false
				});
			}
		})
		.catch(error => {
			this.setState({error, isLoading: false});
		});
	}

	componentDidUpdate(nextProps) {
		if(this.state.date !== this.state.oldDate) {
			this.loadQueenRaces();
			const date = this.state.date;
			this.setState({
				oldDate: date
			})
		}
	}

	componentDidMount() {
		this._isMounted = true;
		this.loadQueenRaces();
	}

	componentWillUnmount() {
		this._isMounted = false;
	}

}


export default AddQueenForm;