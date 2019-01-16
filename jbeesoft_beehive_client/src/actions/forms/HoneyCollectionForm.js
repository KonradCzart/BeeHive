import React, { Component } from 'react';
import './ApiaryList.css';
import { Form, InputNumber, Modal, Select } from 'antd';
import { getAllHoneyTypes } from '../../util/APIUtils';
import LoadingIndicator  from '../../common/LoadingIndicator';
const FormItem = Form.Item;
const Option = Select.Option;


class HoneyCollectionForm extends Component {

	_isMounted = false;

	constructor(props) {
		super(props);
		const date = new Date();
		this.state = {
			honeyTypes: [],
			date: date,
			isLoading: false,
			oldDate: date
		}
	}

	render() {

		const {
			visible, onCancel, onCreate, form
		} = this.props;

		const honeyTypeDrop = this.state.honeyTypes.map((type =>
				<Option key={type.id} value={type.id}>{type.value}</Option>
		));

		const { getFieldDecorator } = form;
		return (
		<Modal
			visible={visible}
			title="Collect honey"
			okText="Collect"
			onCancel={onCancel}
			onOk={onCreate}
		>
			<Form layout="vertical">
				<FormItem label="Honey amount [kg]:">
				{getFieldDecorator('honeyAmount', {
					rules: [{ required: true, message: 'This field is required.' }]
				})(
					<InputNumber
						name="honeyAmount"
						min={0}
						step={0.01}
						placeholder="Honey amount" 
						style={{ width: '100%' }}
					/>					
				)}
				</FormItem>
				
				{
					!this.state.isLoading && this.state.honeyTypes.length > 0 ? (
					<FormItem label="Honey type:">
					{getFieldDecorator('honeyTypeId', {
						rules: [{ required: true, message: 'This field is required.' }]
					})(
						<Select name='honeyTypeId' placeholder='Honey type'>
							{honeyTypeDrop}
						</Select>				
					)}
					</FormItem> ) : null
				}
				{
					this.state.isLoading ? 
					<LoadingIndicator /> : null
				}
			</Form>
		</Modal>
		);
	}

	loadHoneyTypes() {
		let promise = getAllHoneyTypes();

		if(!promise) {
			return;
		}

		this.setState({isLoading: true});

		promise
		.then(response => {
			if(this._isMounted) {
				this.setState({
					honeyTypes: response,
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
			this.loadHoneyTypes();
			const date = this.state.date;
			this.setState({
				oldDate: date
			})
		}
	}

	componentDidMount() {
		this._isMounted = true;
		this.loadHoneyTypes();
	}

	componentWillUnmount() {
		this._isMounted = false;
	}
}

export default HoneyCollectionForm;