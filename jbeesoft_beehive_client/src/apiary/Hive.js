import React, { Component } from 'react';
import './ApiaryList.css';
import { Form, Button, notification } from 'antd';
import { withRouter } from 'react-router-dom';
import { getHiveData, addQueenToHive, editQueenInHive, editHive, deleteQueenFromHive,
		 changeQueen, getMyPrivileges } from '../util/APIUtils';
import LoadingIndicator  from '../common/LoadingIndicator';
import AddQueenForm from './AddQueenForm';
import EditQueenForm from './EditQueenForm';
import EditHiveForm from './EditHiveForm';
import InspectionFormModule from '../actions/InspectionFormModule';

class Hive extends Component {
	_isMounted = false;

	constructor(props) {
		super(props);
		const date = new Date();
		this.receivedElements = [];
		this.queenData = [];
		this.loadHiveData = this.loadHiveData.bind(this);
		this.loadPrivileges = this.loadPrivileges.bind(this);

		this.state = {
			hiveData: [],
			privileges: [],
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
		const WrappedEditHiveForm = Form.create()(EditHiveForm);
		return (
			<div className="apiary-list">
				{
					this.state.hiveData.queenDTO == null ? (
						<Button style={{float: 'right'}} type="primary" onClick={this.showModal}>Add queen</Button>
					) : (
						<Button style={{float: 'right'}} type="primary" onClick={this.showModal}>Edit queen</Button>
					)
				}
				<h1><span className='apiary-name'>Hive name: </span>{this.state.hiveData.name}</h1>
				
				<Button style={{float: 'right'}} type="primary" onClick={this.showModal1}>Edit hive</Button>
				<h1><span className='apiary-name'>Type: </span>{this.state.hiveData.typeName}</h1>
				
				{
					this.state.hiveData.queenDTO !== null ? (
						<Button style={{float: 'right'}} type="primary" onClick={this.handleDeleteQueen}>Delete queen</Button>
					) : null
				}
				<h1><span className='apiary-name'>Box number: </span>{this.state.hiveData.boxNumber}</h1>

				<WrappedEditHiveForm
					wrappedComponentRef={this.saveFormRef1}
					visible={this.state.visible1}
					onCancel={this.handleCancel1}
					onCreate={this.handleEditHive}
					hiveData={this.state.hiveData}
					{...this.props}
				/>
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
							<h1><span className='apiary-name'>Queen:</span></h1>
							<h2><span className='apiary-name'>Race: </span>{this.state.hiveData.queenDTO.raceName}</h2>
							<h2><span className='apiary-name'>Date of birth: </span>{this.state.hiveData.queenDTO.age}</h2>
							<h2><span className='apiary-name'>Color: </span>{this.state.hiveData.queenDTO.color}</h2>
							<h2><span className='apiary-name'>Is reproducing: </span>{this.state.hiveData.queenDTO.isReproducting ? 'Yes' : 'No'}</h2>
							<h2><span className='apiary-name'>Description: </span>{this.state.hiveData.queenDTO.description}</h2>
						</div>	
						</div>
					) : null
				}

				<InspectionFormModule privileges={this.state.privileges} hiveId={this.props.match.params.id} apiaryId={this.state.hiveData.apiaryId} />

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

	showModal = () => {
		var notPrivileged = true;
		
		this.state.privileges.forEach((item) => 
			{if(item.name === "HIVE_EDITING") {
				notPrivileged = false;
			}}
		);

		if(notPrivileged) {
			notification.warning({
				message: 'BeeHive App',
				description: 'You are not privileged to perform this action'
			});
			return;
		}

		this.setState({ visible: true });
	}

	handleCancel = () => {
		this.setState({ visible: false });
	}

	showModal1 = () => {
		var notPrivileged = true;
		
		this.state.privileges.forEach((item) => 
			{if(item.name === "HIVE_EDITING") {
				notPrivileged = false;
			}}
		);

		if(notPrivileged) {
			notification.warning({
				message: 'BeeHive App',
				description: 'You are not privileged to perform this action'
			});
			return;
		}

		this.setState({ visible1: true });
	}

	handleCancel1 = () => {
		this.setState({ visible1: false });
	}

	saveFormRef1 = (formRef) => {
		this.formRef1 = formRef;
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


			const affectedHives = [];
			affectedHives.push(parseInt(this.props.match.params.id));
			const changeQueenRequest = {
				affectedHives: affectedHives,
				price: values.price
			}

			changeQueen(changeQueenRequest, this.state.hiveData.apiaryId)
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

	handleEditHive = () => {
		const form = this.formRef1.props.form;
		form.validateFields((err, values) => {
			if (err) {
				return;
			}

			const hiveRequest = values;
			form.resetFields();
			this.setState({ visible1: false });
			hiveRequest.apiaryId = this.state.hiveData.apiaryId;

			editHive(hiveRequest, this.props.match.params.id)
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

	handleDeleteQueen = () => {
		var notPrivileged = true;
		
		this.state.privileges.forEach((item) => 
			{if(item.name === "HIVE_EDITING") {
				notPrivileged = false;
			}}
		);

		if(notPrivileged) {
			notification.warning({
				message: 'BeeHive App',
				description: 'You are not privileged to perform this action'
			});
			return;
		}

		deleteQueenFromHive(this.props.match.params.id)
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

	loadPrivileges() {
		let promise = getMyPrivileges(this.props.match.params.apiaryId);

		if(!promise) {
			return;
		}

		this.setState({isLoading: true});

		promise
		.then(response => {
			if(this._isMounted) {
				this.setState({
					privileges: response,
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
			this.loadPrivileges();
		}

		if(this.state.date !== this.state.oldDate) {
			this.loadHiveData();
			this.loadPrivileges();
			const date = this.state.date;
			this.setState({
				oldDate: date
			})
		}
	}

	componentDidMount() {
		this._isMounted = true;
		this.loadHiveData();
		this.loadPrivileges();
	}

	componentWillUnmount() {
		this._isMounted = false;
	}

	saveQueenFormRef = (queenFormRef) => {
		this.queenFormRef = queenFormRef;
	}
}

export default withRouter(Hive);