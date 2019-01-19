import React, { Component } from 'react';
import './ApiaryList.css';
import { Form, Input, Button, notification, Modal, Table, Select } from 'antd';
import { withRouter } from 'react-router-dom';
import { addHive, getAllHiveTypes, getAllHives, editApiary, deleteHive } from '../util/APIUtils';
import LoadingIndicator  from '../common/LoadingIndicator';
import EditApiaryForm from './EditApiaryForm';
import ActionForms from '../actions/ActionForms';
import {Contributors} from "../contributors/Contributors";
const FormItem = Form.Item;
const Option = Select.Option;

class Apiary extends Component {
	_isMounted = false;

	constructor(props) {
		super(props);
		const date = new Date();
		this.receivedElements = [];
		this.loadApiaryData = this.loadApiaryData.bind(this);

		this.state = {
			apiaryData: [],
			selectedHivesKeys: [],
			isLoading: false,
			date: date,
			oldDate: date,
			error: null
		};
	}

	render() {
		const columns = [{
			title: 'Name',
			dataIndex: 'name',
			key: 'name',
			render: (text, record) => (
				<a href={'/hive/' + record.id}>{text}</a>
			)
		}, {
			title: 'Hive type',
			dataIndex: 'typeName',
			key: 'typeName',
		}, {
			title: 'Box number',
			dataIndex: 'boxNumber',
			key: 'boxNumber',
		}, {
			title: 'Queen',
			dataIndex: 'queenDTO',
			key: 'queenDTO',
			render : (text, record) => (
				<div>
				{record.queenDTO !== null ? (<span>Yes</span>) : (<span>No</span>)}
				</div>
			)
		}, {
			title: 'Delete',
			dataIndex: 'deleteHive',
			key: 'id',
			render : (text, record) => (
				<Button type="default" onClick={(e) => this.deleteHive(record.id, e)}>Delete</Button>
			)
		}];

		const {isLoading, selectedHivesKeys} = this.state;

		const rowSelection = {
			selectedHivesKeys,
			onChange: this.onSelectChange,
		};


		if(isLoading || this.state.apiaryData.apiaryINFO === undefined) {
			return <LoadingIndicator />;
		}

		const WrappedAddHiveForm = Form.create()(AddHiveForm);
		const WrappedEditApiaryForm = Form.create()(EditApiaryForm);
		return (
			<div className="apiary-list">
				<Button style={{float: 'right'}} type="primary" onClick={this.showModal}>New hive</Button>
				<h1>Apiary name: <span className='apiary-name'>{this.state.apiaryData.apiaryINFO.name}</span></h1>
				
				<Button style={{float: 'right'}} type="primary" onClick={this.showModal1}>Edit Apiary</Button>
				<h1>Country: <span className='apiary-name'>{this.state.apiaryData.apiaryINFO.country}</span></h1>
				
				<Button style={{float: 'right'}} type="primary" onClick={this.handleActionsDetails}>Show performed actions</Button>
				<h1>City: <span className='apiary-name'>{this.state.apiaryData.apiaryINFO.city}</span></h1>
				<WrappedAddHiveForm
					wrappedComponentRef={this.saveFormRef}
					visible={this.state.visible}
					onCancel={this.handleCancel}
					onCreate={this.handleCreate}
					onTypeChange={this.handleTypeChange}
					onBoxNumChange={this.handleBoxNumChange}
					{...this.props}
				/>


				<WrappedEditApiaryForm
					wrappedComponentRef={this.saveFormRef1}
					visible={this.state.visible1}
					onCancel={this.handleCancel1}
					onCreate={this.handleEdit}
					userID={this.props.currentUser.id}
					apiaryData={this.state.apiaryData.apiaryINFO}
				/>
				{/*PAWEMIX INJECT START*/}
				<Contributors apiaryId={this.state.apiaryData.apiaryINFO.id}
							  userId={this.props.currentUser.id}/>
				{/*PAWEMIX INJECT END*/}
				{
					!this.state.isLoading && this.state.apiaryData.hives.length === 0 ? (
						<div className="no-polls-found">
							<h2>There are no hives in this apiary.</h2>
						</div>	
					) : null
				}
				{
					!this.state.isLoading && this.state.apiaryData.hives.length > 0 ? (
						<div>
							<h2>Hives in this apiary:</h2>
							<ActionForms affectedHives={this.state.selectedHivesKeys} apiaryId={this.state.apiaryData.apiaryINFO.id} />
							<Table rowSelection={rowSelection} rowKey={record => record.id} columns={columns} dataSource={this.state.apiaryData.hives} />
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
		visible: false,
		visible1: false
	};

	onSelectChange = (selectedRowKeys) => {
		this.setState({ selectedHivesKeys: selectedRowKeys });
	}

	showModal = () => {
		this.setState({ visible: true });
	}

	showModal1 = () => {
		this.setState({ visible1: true });
	}

	handleCancel = () => {
		this.setState({ visible: false });
	}

	handleCancel1 = () => {
		this.setState({ visible1: false });
	}

	handleTypeChange = (type) => {
		this.receivedElements.type = type;
	}

	handleBoxNumChange = (num) => {
		this.receivedElements.boxNum = num;
	}

	deleteHive = (id, e) => {
		deleteHive(id)
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
	}

	handleCreate = () => {
		const form = this.formRef.props.form;
		form.validateFields((err, values) => {
			if (err) {
				return;
			}

			const apiaryRequest = values;
			apiaryRequest.apiaryId = this.props.match.params.id;
			apiaryRequest.hiveTypeId = this.receivedElements.type;
			apiaryRequest.boxNumber = this.receivedElements.boxNum;
			form.resetFields();
			this.setState({ visible: false });


			addHive(apiaryRequest)
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
		const form = this.formRef1.props.form;
		form.validateFields((err, values) => {
			if(err) {
				return;
			}

			const apiaryRequest = values;
			form.resetFields();
			this.setState({visible1: false});
			
			editApiary(apiaryRequest, this.props.match.params.id)
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

	loadApiaryData() {
		let promise = getAllHives(this.props.match.params.id);

		if(!promise) {
			return;
		}

		this.setState({isLoading: true});

		promise
		.then(response => {
			if(this._isMounted) {
				this.setState({
					apiaryData: response,
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
				apiaryData: [],
				isLoading: false
			});
			this.loadApiaryData();
		}

		if(this.state.date !== this.state.oldDate) {
			this.loadApiaryData();
			const date = this.state.date;
			this.setState({
				oldDate: date
			})
		}
	}

	handleActionsDetails = (e) => {
		this.props.history.push("/actions/" + this.state.apiaryData.apiaryINFO.id);
	}

	componentDidMount() {
		this._isMounted = true;
		this.loadApiaryData();
	}

	componentWillUnmount() {
		this._isMounted = false;
	}

	saveFormRef = (formRef) => {
		this.formRef = formRef;
	}

	saveFormRef1 = (formRef) => {
		this.formRef1 = formRef;
	}
}




class AddHiveForm extends Component {

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
				
				{
					!this.state.isLoading && this.state.hiveTypes.length > 0 ? (
					<FormItem label="Hive type:">
					{getFieldDecorator('hive_type_id', {
						rules: [{ required: true, message: 'This field is required.' }],
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

export default withRouter(Apiary);