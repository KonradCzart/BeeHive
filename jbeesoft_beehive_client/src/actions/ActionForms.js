import React, { Component } from 'react';
import './forms/ApiaryList.css';
import { Form, Button, notification } from 'antd';
import { collectHoney, feedBees, treatment } from '../util/APIUtils';
import LoadingIndicator  from '../common/LoadingIndicator';
import HoneyCollectionForm from './forms/HoneyCollectionForm';
import FeedingForm from './forms/FeedingForm';
import TreatmentForm from './forms/TreatmentForm';
import { withRouter } from 'react-router-dom';

class ActionForms extends Component {
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

		const WrappedHoneyCollectionForm = Form.create()(HoneyCollectionForm);
		const WrappedFeedingForm = Form.create()(FeedingForm);
		const WrappedTreatmentForm = Form.create()(TreatmentForm);

		return (
			<div className="apiary-list">
				<Button className="formButton" type="primary" onClick={this.showModalHoneyCollection}>Collect honey</Button>
				<WrappedHoneyCollectionForm
					wrappedComponentRef={this.saveHoneyCollectRef}
					visible={this.state.honeyCollectVisible}
					onCancel={this.handleCancelHoneyCollection}
					onCreate={this.handleHoneyCollection}
					{...this.props}
				/>

				<Button className="formButton" type="primary" onClick={this.showModalFeeding}>Feeding</Button>
				<WrappedFeedingForm
					wrappedComponentRef={this.saveFeedingRef}
					visible={this.state.feedingVisible}
					onCancel={this.handleCancelFeeding}
					onCreate={this.handleFeeding}
					{...this.props}
				/>

				<Button className="formButton" type="primary" onClick={this.showModalTreatment}>Treatment</Button>
				<WrappedTreatmentForm
					wrappedComponentRef={this.saveTreatmentRef}
					visible={this.state.treatmentVisible}
					onCancel={this.handleCancelTreatment}
					onCreate={this.handleTreatment}
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
		honeyCollectVisible: false,
		feedingVisible: false,
		treatmentVisible: false
	};

	//Honey collection

	showModalHoneyCollection = () => {
		if(this.props.affectedHives.length === 0) {
			notification.warning({
				message: 'BeeHive App',
				description: "You haven't selected any hives."
			});
		} else {
			this.setState({ honeyCollectVisible: true });
		}
	}

	handleCancelHoneyCollection = () => {
		this.setState({ honeyCollectVisible: false });
	}

	saveHoneyCollectRef = (honeyCollectRef) => {
		this.honeyCollectRef = honeyCollectRef;
	}

	handleHoneyCollection = () => {
		const form = this.honeyCollectRef.props.form;
		form.validateFields((err, values) => {
			if (err) {
				return;
			}

			const honeyCollectionRequest = values;
			honeyCollectionRequest.affectedHives = this.props.affectedHives;

			form.resetFields();
			this.setState({ honeyCollectVisible: false });

			collectHoney(honeyCollectionRequest, this.props.apiaryId)
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

	//Feeding

	showModalFeeding = () => {
		if(this.props.affectedHives.length === 0) {
			notification.warning({
				message: 'BeeHive App',
				description: "You haven't selected any hives."
			});
		} else {
			this.setState({ feedingVisible: true });
		}
	}

	handleCancelFeeding = () => {
		this.setState({ feedingVisible: false });
	}

	saveFeedingRef = (ref) => {
		this.feedingRef = ref;
	}

	handleFeeding = () => {
		const form = this.feedingRef.props.form;
		form.validateFields((err, values) => {
			if (err) {
				return;
			}

			const feedingRequest = values;
			feedingRequest.affectedHives = this.props.affectedHives;

			form.resetFields();
			this.setState({ feedingVisible: false });

			feedBees(feedingRequest, this.props.apiaryId)
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


	//Treatment

	showModalTreatment = () => {
		if(this.props.affectedHives.length === 0) {
			notification.warning({
				message: 'BeeHive App',
				description: "You haven't selected any hives."
			});
		} else {
			this.setState({ treatmentVisible: true });
		}
	}

	handleCancelTreatment = () => {
		this.setState({ treatmentVisible: false });
	}

	saveTreatmentRef = (ref) => {
		this.treatmentRef = ref;
	}

	handleTreatment = () => {
		const form = this.treatmentRef.props.form;
		form.validateFields((err, values) => {
			if (err) {
				return;
			}

			const treatmentRequest = values;
			treatmentRequest.affectedHives = this.props.affectedHives;

			form.resetFields();
			this.setState({ treatmentVisible: false });

			treatment(treatmentRequest, this.props.apiaryId)
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

export default withRouter(ActionForms);