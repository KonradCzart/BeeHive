import React from "react";
import {Checkbox, Divider} from "antd";
import {WarningIndicator} from "../common/WarningIndicator";
import LoadingIndicator from "../common/LoadingIndicator";

/**
 * ## props:
 * * apiId : _Number_ apiary ID of which hives will be represented;
 * * loading : _Boolean_ is data still being fetched;
 * * error : false|_String_ was there an error during data fetching;
 * * hives : _Array(___Hive___)_ hives to be displayed;
 * * disabled : _Boolean_ whether the whole component should be disabled;
 * * onChange : _Function(Array(Number))_.
 *
 * ## Hive:
 * * id : _Number_ ID of the hive;
 * * name : _String_ full name of the hive.
 */
export class HivesChecker extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            hivesChecked: [...this.props.hives],
            allChecked: true,
            indeterminate: false,
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleChangeAll = this.handleChangeAll.bind(this);
        this.transform = this.transform.bind(this);
    }

    transform(hives) {
        return hives.map((hive) => '(' + hive.id + ") " + hive.name);
    }

    handleChangeAll(event) {
        let hivesChecked = event.target.checked? this.props.hives : [];
        this.setState({
            hivesChecked,
            indeterminate: false,
            allChecked: event.target.checked,
        });
        this.props.onChange(hivesChecked.map((hive) => hive.id));
    }

    handleChange(hivesCheckedStr) {
        let indeterminate = !!this.state.hivesChecked.length &&
            (this.state.hivesChecked.length < this.props.hives.length);
        let allChecked =
            this.state.hivesChecked.length === this.props.hives.length;
        let hivesChecked = this.props.hives.filter(
            (hive) => hivesCheckedStr.includes(
                '(' + hive.id + ") " + hive.name));
        this.setState({
            hivesChecked,
            indeterminate,
            allChecked,
        });
        this.props.onChange(hivesChecked.map((hive) => hive.id));
    }

    render() {
        if(this.state.loading)
            return (<LoadingIndicator/>);
        if(this.state.error)
            return (<WarningIndicator messageJSX={this.state.error}/>);
        return (
            <div>
                <h2>Selected hives</h2>
                <Divider/>
                <Checkbox checked={this.state.allChecked}
                    indeterminate={this.state.indeterminate}
                    disabled={this.props.disabled}
                    onChange={this.handleChangeAll}>Select all</Checkbox>
                <Checkbox.Group
                    disabled={this.props.disabled}
                    options={this.transform(this.props.hives)}
                    value={this.transform(this.state.hivesChecked)}
                    onChange={this.handleChange}/>
            </div>
        );
    }
}