import React, { Component } from 'react';
import './forms/ApiaryList.css';
import { Table } from 'antd';
import { getActionsHistory } from '../util/APIUtils';
import LoadingIndicator  from '../common/LoadingIndicator';

class ActionsDetails extends Component {
	_isMounted = false;

	constructor(props) {
		super(props);
		const date = new Date();
		this.loadActionsHistory = this.loadActionsHistory.bind(this);

		this.state = {
			actionsHistory: [],
			isLoading: false,
			date: date,
			oldDate: date,
			error: null
		};
	}

	render() {
		const columns = [{
			title: 'Date',
			dataIndex: 'date',
			key: 'date',
			render: (text, record) => (
				record.date.substring(0, 10) + '\u00A0\u00A0' + record.date.substring(11, 16)
			)
		}, {
			title: 'Affected hives',
			dataIndex: 'affectedHives',
			key: 'affectedHives',
			render: (text, record) => (
				record.affectedHives.length
			)
		}, {
			title: 'Performed by',
			dataIndex: 'performer',
			key: 'performer',
			render: (text, record) => (
				record.performer.username
			)
		}, {
			title: 'Action name',
			dataIndex: 'actionName',
			key: 'actionName'
		}, {
			title: 'Delete',
			dataIndex: 'deleteHive',
			key: 'id',
			/*render : (text, record) => (
				<Button type="default" onClick={(e) => this.deleteHive(record.id, e)}>Delete</Button>
			)*/
		}];

		return(
			<div className="apiary-list">
			{
				!this.state.isLoading && this.state.actionsHistory.length === 0 ? (
					<div className="no-polls-found">
						<h2>You haven't performed any action in this apiary.</h2>
					</div>	
				) : null
			}
			{
				!this.state.isLoading && this.state.actionsHistory.length > 0 ? (
					<div>
						<h2>Actions history:</h2>
						<Table rowKey={record => record.id} columns={columns} dataSource={this.state.actionsHistory} />
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

	loadActionsHistory() {
		let promise = getActionsHistory(this.props.match.params.apiaryId);

		if(!promise) {
			return;
		}

		this.setState({isLoading: true});

		promise
		.then(response => {
			if(this._isMounted) {
				this.setState({
					actionsHistory: response,
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
				isLoading: false
			});
		}

		if(this.state.date !== this.state.oldDate) {
			this.loadActionsHistory();
			const date = this.state.date;
			this.setState({
				oldDate: date
			})
		}
	}

	componentDidMount() {
		this._isMounted = true;
		this.loadActionsHistory();
	}

	componentWillUnmount() {
		this._isMounted = false;
	}
}

export default ActionsDetails;