import React from "react";
import {Checkbox, Divider} from "antd";
import {getHives} from "../util/ApiFacade";
import {WarningIndicator} from "../common/WarningIndicator";
import LoadingIndicator from "../common/LoadingIndicator";

/**
 * ## props:
 * * apiId : _Number_ apiary ID of which hives will be represented;
 * * onChange : _Function(Array(Number))_.
 */
export class HivesChecker extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            error: false,
            hives: [],
            hivesChecked: [],
            allChecked: false,
            indeterminate: false,
        };
        this.componentDidMount = this.componentDidMount.bind(this);
        this.refreshHives = this.refreshHives.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleChangeAll = this.handleChangeAll.bind(this);
    }

    componentDidMount() {
        this.refreshHives(this.props.apiId);
    }

    refreshHives(apiId) {
        getHives(apiId).then(json => {
            this.setState({loading: false, error: false, hives: json.hives});
        }).catch(json => {
            this.setState({loading: false, error: json.message});
        })
    }

    handleChangeAll(event) {
        let hivesChecked = event.target.checked? this.state.hives : [];
        this.setState({
            hivesChecked,
            indeterminate: false,
            allChecked: event.target.checked,
        });
        this.props.onChange(hivesChecked.map((hive) => hive.id));
    }

    handleChange(hivesCheckedStr) {
        let indeterminate = !!this.state.hivesChecked.length &&
            (this.state.hivesChecked.length < this.state.hives.length);
        let allChecked =
            this.state.hivesChecked.length === this.state.hives.length;
        let hivesChecked = this.state.hives.filter(
            (hive) => hivesCheckedStr.includes(hive.name));
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
                <h2>Hives</h2>
                <Divider/>
                <Checkbox checked={this.state.allChecked}
                    indeterminate={this.state.indeterminate}
                    onChange={this.handleChangeAll}>Select all</Checkbox>
                <Checkbox.Group
                    options={this.state.hives.map((hive) => hive.name)}
                    value={this.state.hivesChecked.map((hive) => hive.name)}
                    onChange={this.handleChange}/>
            </div>
        );
    }
}