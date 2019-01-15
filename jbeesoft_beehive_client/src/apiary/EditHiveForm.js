import React, { Component } from 'react';
import './ApiaryList.css';
import { Form, Input, Modal, Select } from 'antd';
import { getAllHiveTypes } from '../util/APIUtils';
import LoadingIndicator  from '../common/LoadingIndicator';
const FormItem = Form.Item;
const Option = Select.Option;


class EditHiveForm extends Component {

	_isMounted = false;

	constructor(props) {
		super(props);
		const date = new Date();
		this.state = {
			hiveTypes: [],
			date: date,
			isLoading: false,
			oldDate: date
		}
	}

	render() {

		const {
			visible, onCancel, onCreate, form, onTypeChange, onBoxNumChange
		} = this.props;

		const boxNumber = [{id: 1, title: 1}, {id: 2, title: 2}, {id: 3, title: 3}];

		const boxNumberDrop = boxNumber.map((type =>
				<Option key={type.id} value={type.id}>{type.title}</Option>
		));

		const hiveTypeDrop = this.state.hiveTypes.map((type =>
				<Option key={type.id} value={type.id}>{type.value}</Option>
		));

		const { getFieldDecorator } = form;
		return (
		<Modal
			visible={visible}
			title="Edit hive"
			okText="Edit"
			onCancel={onCancel}
			onOk={onCreate}
		>
			<Form layout="vertical">
				<FormItem label="Hive name:">
				{getFieldDecorator('name', {
					rules: [{ required: true, message: 'This field is required.' }],
					initialValue: this.props.hiveData.name
				})(
					<Input
						name="name"
						type="text"
						placeholder="Name" />					
				)}
				</FormItem>
				
				{
					!this.state.isLoading && this.state.hiveTypes.length > 0 ? (
					<FormItem label="Hive type:">
					{getFieldDecorator('hive_type_id', {
						rules: [{ required: true, message: 'This field is required.' }],
						initialValue: this.props.hiveData.typeName
					})(
						<Select name='hive_type_id' placeholder='Hive type' onChange={onTypeChange}>
							{hiveTypeDrop}
						</Select>				
					)}
					</FormItem> ) : null
				}
				{
					this.state.isLoading ? 
					<LoadingIndicator /> : null
				}

				<FormItem label="Box number:">
				{getFieldDecorator('boxNumber', {
					rules: [{ required: true, message: 'This field is required.' }],
					initialValue: this.props.hiveData.boxNumber
				})(
					<Select name='boxNumber' onChange={onBoxNumChange} placeholder='Box number' >
						{boxNumberDrop}
					</Select>					
				)}
				</FormItem>
			</Form>
		</Modal>
		);
	}

	loadHiveTypes() {
		let promise = getAllHiveTypes();

		if(!promise) {
			return;
		}

		this.setState({isLoading: true});

		promise
		.then(response => {
			if(this._isMounted) {
				this.setState({
					hiveTypes: response,
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
			this.loadHiveTypes();
			const date = this.state.date;
			this.setState({
				oldDate: date
			})
		}
	}

	componentDidMount() {
		this._isMounted = true;
		this.loadHiveTypes();
	}

	componentWillUnmount() {
		this._isMounted = false;
	}
}

export default EditHiveForm;