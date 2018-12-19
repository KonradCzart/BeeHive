import React, { Component } from 'react';
import './ApiaryList.css';
import { Form, Input, Button, notification, Modal, Table, Select, DatePicker, Checkbox } from 'antd';
import { withRouter } from 'react-router-dom';
import { getHiveData, getQueenRaces, addQueenToHive, editQueenInHive } from '../util/APIUtils';
import LoadingIndicator  from '../common/LoadingIndicator';
import moment from 'moment';
const FormItem = Form.Item;
const Option = Select.Option;

class Hive extends Component {
	_isMounted = false;

	constructor(props) {
		super(props);
		const date = new Date();
		this.receivedElements = [];
		this.queenData = [];
		this.loadHiveData = this.loadHiveData.bind(this);

		this.state = {
			hiveData: [],
			isLoading: false,
			date: date,
			oldDate: date,
			error: null
		};
	}

	render() {

		const {isLoading} = this.state;

		if(isLoading || this.state.hiveData.name === undefined) {
			return <LoadingIndicator />;
		}

		const WrappedAddQueenForm = Form.create()(AddQueenForm);
		const WrappedEditQueenForm = Form.create()(EditQueenForm);
		return (
			<div className="apiary-list">
				{
					this.state.hiveData.queenDTO == null ? (
						<Button style={{float: 'right'}} type="primary" onClick={this.showModal}>Add queen</Button>
					) : (
						<Button style={{float: 'right'}} type="primary" onClick={this.showModal}>Edit queen</Button>
					)
				}
				<h1>Hive name: <span className='apiary-name'>{this.state.hiveData.name}</span></h1>
				<h1>Type: <span className='apiary-name'>{this.state.hiveData.typeName}</span></h1>
				<h1>Box number: <span className='apiary-name'>{this.state.hiveData.boxNumber}</span></h1>
				{
					!this.state.isLoading && this.state.hiveData.queenDTO === null ? (
						<div>
						<WrappedAddQueenForm wrappedComponentRef={this.saveQueenFormRef} visible={this.state.visible}
							onCancel={this.handleCancel} onCreate={this.handleCreate} {...this.props} />
						<div className="no-polls-found">
							<h2>You haven't got queen in this hive.</h2>
						</div>	
						</div>
					) : null
				}
				{
					!this.state.isLoading && this.state.hiveData.queenDTO !== null ? (
						<div>
						<WrappedEditQueenForm wrappedComponentRef={this.saveQueenFormRef} visible={this.state.visible}
							onCancel={this.handleCancel} onCreate={this.handleEdit} queenData={this.state.hiveData.queenDTO}
							{...this.props} />
						<div className="no-polls-found">
							<h1>Queen:</h1>
							<h2>Race: <span className='apiary-name'>{this.state.hiveData.queenDTO.raceName}</span></h2>
							<h2>Age: <span className='apiary-name'>{this.state.hiveData.queenDTO.age}</span></h2>
							<h2>Color: <span className='apiary-name'>{this.state.hiveData.queenDTO.color}</span></h2>
							<h2>Is reproducing: <span className='apiary-name'>{this.state.hiveData.queenDTO.isReproducting ? 'Yes' : 'No'}</span></h2>
							<h2>Description: <span className='apiary-name'>{this.state.hiveData.queenDTO.description}</span></h2>
						</div>	
						</div>
					) : null
				}
				{
					this.state.isLoading ? 
					<LoadingIndicator /> : null
				}
			</div>
		)
	}

	state = {
		visible: false
	};

	showModal = () => {
		this.setState({ visible: true });
	}

	handleCancel = () => {
		this.setState({ visible: false });
	}

	handleCreate = () => {
		const form = this.queenFormRef.props.form;
		form.validateFields((err, values) => {
			if (err) {
				return;
			}

			const queenRequest = values;
			queenRequest.age = values['age'].format('YYYY-MM-DD');
			queenRequest.hiveId = this.props.match.params.id;

			form.resetFields();
			this.setState({ visible: false });
			addQueenToHive(queenRequest)
			.then(response => {
				notification.success({
					message: 'BeeHive App',
					description: response.message
				});
				this.setState({date: new Date()})
			}).catch(error => {
				notification.error({
					message: 'BeeHive App',
					description: error.message
				});
			});
		});
	}

	handleEdit = () => {
		const form = this.queenFormRef.props.form;
		form.validateFields((err, values) => {
			if (err) {
				return;
			}

			const queenRequest = values;
			queenRequest.age = values['age'].format('YYYY-MM-DD');
			queenRequest.id = this.state.hiveData.queenDTO.id;

			form.resetFields();
			this.setState({ visible: false });
			editQueenInHive(queenRequest)
			.then(response => {
				notification.success({
					message: 'BeeHive App',
					description: response.message
				});
				this.setState({date: new Date()})
			}).catch(error => {
				notification.error({
					message: 'BeeHive App',
					description: error.message
				});
			});
		});
	}

	loadHiveData() {
		let promise = getHiveData(this.props.match.params.id);

		if(!promise) {
			return;
		}

		this.setState({isLoading: true});

		promise
		.then(response => {
			if(this._isMounted) {
				this.setState({
					hiveData: response,
					isLoading: false
				});
			}
		})
		.catch(error => {
			this.setState({error, isLoading: false});
		});
	}

	componentDidUpdate(nextProps) {
		if(this.props.isAuthenticated !== nextProps.isAuthenticated) {
			this.setState({
				hiveData: [],
				isLoading: false
			});
			this.loadHiveData();
		}

		if(this.state.date !== this.state.oldDate) {
			this.loadHiveData();
			const date = this.state.date;
			this.setState({
				oldDate: date
			})
		}
	}

	componentDidMount() {
		this._isMounted = true;
		this.loadHiveData();
	}

	componentWillUnmount() {
		this._isMounted = false;
	}

	saveQueenFormRef = (queenFormRef) => {
		this.queenFormRef = queenFormRef;
	}
}




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

				<FormItem label="Age:">
				{getFieldDecorator('age', {
					rules: [{ type: 'object', required: true, message: 'Please select time!' }],
				})(
					<DatePicker
						format="YYYY-MM-DD"
						name="age"
						type="text"
						placeholder="Select age" />
				)}
				</FormItem>

				<FormItem label="Is reproducing:">
				{getFieldDecorator('isReproducting', {
					rules: [{ required: false, message: 'This field is required!' }], initialValue: false
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
			visible, onCancel, onCreate, form, queenData
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

export default withRouter(Hive);