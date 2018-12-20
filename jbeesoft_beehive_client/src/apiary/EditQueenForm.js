import React, { Component } from 'react';
import './ApiaryList.css';
import { Form, Input, Modal, Select, DatePicker, Checkbox } from 'antd';
import { getQueenRaces } from '../util/APIUtils';
import LoadingIndicator  from '../common/LoadingIndicator';
import moment from 'moment';
const FormItem = Form.Item;
const Option = Select.Option;


class EditQueenForm extends Component {

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
				<Option key={type.value} value={type.value}>{type.value}</Option>
		));

		const { getFieldDecorator } = form;
		const val = this.props.queenData.isReproducting ? 'checked' : 'null';
		return (
		<Modal
			visible={visible}
			title="Edit queen in hive"
			okText="Edit"
			onCancel={onCancel}
			onOk={onCreate}
		>
			<Form layout="vertical">
				
				{
					!this.state.isLoading && this.state.queenRaces.length > 0 ? (
					<FormItem label="Race:">
					{getFieldDecorator('raceName', {
						rules: [{ required: true, message: 'This field is required.' }],
						initialValue: this.props.queenData.raceName
					})(
						<Select name='raceName' placeholder='Race'>
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
					initialValue: this.props.queenData.color
				})(
					<Input
						name="color"
						type="text"
						placeholder="Color" />					
				)}
				</FormItem>

				<FormItem label="Age:">
				{getFieldDecorator('age', {
					rules: [{ type: 'object', required: true, message: 'Please select time!' }],
					initialValue: moment(this.props.queenData.age, "YYYY-MM-DD")
				})(
					<DatePicker
						format="YYYY-MM-DD"
						name="age"
						type="text"
						placeholder="Select age" />
				)}
				</FormItem>
				<FormItem label="Is reproducing:">
				{
					getFieldDecorator('isReproducting', {
					rules: [{ required: true, message: 'This field is required!' }],
					initialValue: this.props.queenData.isReproducting,
					valuePropName: val
				})(
					<Checkbox
						name="isReproducting" />
				)}
				</FormItem>

				<FormItem label="Description:">
				{getFieldDecorator('description', {
					rules: [{ required: true, message: 'This field is required.' }],
					initialValue: this.props.queenData.description
				})(
					<Input
						name="description"
						type="text"
						placeholder="Description" />					
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

export default EditQueenForm;