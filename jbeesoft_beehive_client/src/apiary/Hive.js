import React, { Component } from 'react';
import './ApiaryList.css';
import { Form, Button, notification } from 'antd';
import { withRouter } from 'react-router-dom';
import { getHiveData, addQueenToHive, editQueenInHive, editHive, deleteQueenFromHive } from '../util/APIUtils';
import LoadingIndicator  from '../common/LoadingIndicator';
import AddQueenForm from './AddQueenForm';
import EditQueenForm from './EditQueenForm';
import EditHiveForm from './EditHiveForm';

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
				<h1>Hive name: <span className='apiary-name'>{this.state.hiveData.name}</span></h1>
				<Button style={{float: 'right'}} type="primary" onClick={this.showModal1}>Edit hive</Button>
				<h1>Type: <span className='apiary-name'>{this.state.hiveData.typeName}</span></h1>
				{
					this.state.hiveData.queenDTO !== null ? (
						<Button style={{float: 'right'}} type="primary" onClick={this.handleDeleteQueen}>Delete queen</Button>
					) : null
				}
				<h1>Box number: <span className='apiary-name'>{this.state.hiveData.boxNumber}</span></h1>

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
		visible: false,
		visible1: false
	};

	showModal = () => {
		this.setState({ visible: true });
	}

	handleCancel = () => {
		this.setState({ visible: false });
	}

	showModal1 = () => {
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
				if(error.status === 401) {
					notification.error({
						message: 'BeeHive App',
						description: 'Data is incorrect. Please try again!'
					});					
				} else {
					notification.error({
						message: 'BeeHive App',
						description: 'Sorry! Something went wrong. Please try again!'
					});											
				}
			});
		});
	}

	handleDeleteQueen = () => {
		deleteQueenFromHive(this.props.match.params.id)
		.then(response => {
			notification.success({
				message: 'BeeHive App',
				description: response.message
			});
			this.setState({date: new Date()})
		}).catch(error => {
			if(error.status === 401) {
				notification.error({
					message: 'BeeHive App',
					description: 'Data is incorrect. Please try again!'
				});					
			} else {
				notification.error({
					message: 'BeeHive App',
					description: 'Sorry! Something went wrong. Please try again!'
				});											
			}
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

export default withRouter(Hive);