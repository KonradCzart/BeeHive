import React, { Component } from 'react';
import './forms/ApiaryList.css';
import { Button, Modal } from 'antd';


class ActionDetailsModal extends Component {

	render() {

		const {
			visible, onCancel, actionDetails
		} = this.props;

		return (
		<Modal
			visible={visible}
			title="Action details"
			onCancel={onCancel}
			onOk={onCancel}
			footer={[
				null,
				<Button key="back" type="primary" onClick={this.props.onCancel}>
					OK
				</Button>,
          ]}
		>
			{
				actionDetails !== undefined ?
				(
					<div>
						<h3><span className='apiary-name'>Action name: </span>{actionDetails.actionName}</h3>
						<h3><span className='apiary-name'>Date: </span>{actionDetails.date.substring(0, 10) + '\u00A0\u00A0' + actionDetails.date.substring(11, 16)}</h3>
						<h3><span className='apiary-name'>Performed by: </span>{actionDetails.performer.username}</h3>
						<h3><span className='apiary-name'>Affected hives: </span>{
							actionDetails.affectedHives.map((item) => 
								item.name + "\u00A0\u00A0"
							)
						}</h3>

						{
							actionDetails.actionName === "Treatment" ? 
							(
								<div>
								<h3><span className='apiary-name'>Disease type: </span>{actionDetails.deseaseType}</h3>
								<h3><span className='apiary-name'>Apllied medicine: </span>{actionDetails.appliedMedicine}</h3>
								<h3><span className='apiary-name'>Dose: </span>{actionDetails.dose}</h3>
								<h3><span className='apiary-name'>Price: </span>{actionDetails.price}</h3>
								</div>
							) : null
						}

						{
							actionDetails.actionName === "Feeding" ? 
							(
								<div>
								<h3><span className='apiary-name'>Food type: </span>{actionDetails.feedType}</h3>
								<h3><span className='apiary-name'>Food amount: </span>{actionDetails.feedAmount}</h3>
								<h3><span className='apiary-name'>Price: </span>{actionDetails.price}</h3>
								</div>
							) : null
						}

						{
							actionDetails.actionName === "Honey Collecting" ? 
							(
								<div>
								<h3><span className='apiary-name'>Honey type: </span>{actionDetails.honeyType.name}</h3>
								<h3><span className='apiary-name'>Honey amount: </span>{actionDetails.honeyAmount}</h3>
								</div>
							) : null
						}

						{
							actionDetails.actionName === "Inspection" ? 
							(
								<div>
								<h3><span className='apiary-name'>Is maggot present: </span>{actionDetails.isMaggotPresent ? (<span>Yes</span>) : (<span>No</span>) }</h3>
								<h3><span className='apiary-name'>Is lair present: </span>{actionDetails.isLairPresent ? (<span>Yes</span>) : (<span>No</span>) }</h3>
								<h3><span className='apiary-name'>Hive strength: </span>{actionDetails.hiveStrength}</h3>
								<h3><span className='apiary-name'>Frames with wax foundation: </span>{actionDetails.framesWithWaxFoundation}</h3>
								<h3><span className='apiary-name'>Description: </span>{actionDetails.decription}</h3>
								</div>
							) : null
						}
					</div>
				) :
				null
			}
			
		</Modal>
		);
	}
}

export default ActionDetailsModal;