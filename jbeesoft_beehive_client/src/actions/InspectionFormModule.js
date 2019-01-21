import React, { Component } from 'react';
import './forms/ApiaryList.css';
import { Form, Button, notification } from 'antd';
import { inspection } from '../util/APIUtils';
import LoadingIndicator  from '../common/LoadingIndicator';
import InspectionForm from './forms/InspectionForm';

class InspectionFormModule extends Component {
	_isMounted = false;

	constructor(props) {
		super(props);
		const date = new Date();

		this.state = {
			isLoading: false,
			date: date,
			oldDate: date,
			error: null
		};
	}

	render() {

		const WrappedInspectionForm = Form.create()(InspectionForm);

		return (
			<div className="apiary-list">
				<Button className="formButton" type="primary" onClick={this.showModalInspection}>Perform inspection</Button>
				<WrappedInspectionForm
					wrappedComponentRef={this.saveInspectionRef}
					visible={this.state.inspectionVisible}
					onCancel={this.handleCancelInspection}
					onCreate={this.handleInspection}
					{...this.props}
				/>
				
				{
					this.state.isLoading ? 
					<LoadingIndicator /> : null
				}
			</div>
		)
	}

	state = {
		inspectionVisible: false
	};

	//Inspection

	showModalInspection = () => {
		var notPrivileged = true;
		
		this.props.privileges.forEach((item) => 
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

		this.setState({ inspectionVisible: true });
	}

	handleCancelInspection = () => {
		this.setState({ inspectionVisible: false });
	}

	saveInspectionRef = (ref) => {
		this.inspectionRef = ref;
	}

	handleInspection = () => {
		const form = this.inspectionRef.props.form;
		form.validateFields((err, values) => {
			if (err) {
				return;
			}

			const inspectionRequest = values;
			const affectedHives = [];
			affectedHives.push(parseInt(this.props.hiveId));
			inspectionRequest.affectedHives = affectedHives;

			form.resetFields();
			this.setState({ inspectionVisible: false });
			console.log(this.props.apiaryId);
			console.log(inspectionRequest);

			inspection(inspectionRequest, this.props.apiaryId)
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

	


	componentDidUpdate(nextProps) {
		if(this.props.isAuthenticated !== nextProps.isAuthenticated) {
			this.setState({
				hiveData: [],
				isLoading: false
			});
		}

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

export default InspectionFormModule;