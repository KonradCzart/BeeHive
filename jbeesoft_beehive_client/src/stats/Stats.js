import React from "react";
import "./Stats.css";
import {Card, DatePicker, Table} from "antd";
import moment from "moment";
import {
    ACT_OUT_ACTION,
    ACT_OUT_AMOUNT,
    ACT_OUT_AVERAGE,
    ACT_OUT_NAME,
    ACT_OUT_TOTAL,
    CON_OUT_CONTR,
    CON_OUT_FEEDING,
    CON_OUT_HONEY,
    CON_OUT_INSPECT,
    CON_OUT_TREATMENT,
    getContributorStats,
    getFeedingStats,
    getHoneyStats,
    getTreatmentStats
} from "../util/ApiFacade";
import LoadingIndicator from "../common/LoadingIndicator";
import {WarningIndicator} from "../common/WarningIndicator";

const {RangePicker} = DatePicker;

const KEYS_APIID = "apiId";
const DEFAULTS_APIID = 0;

const KEYS_HEADERTEXT = "headerText";
const DEFAULTS_HEADERTEXT = "Statistics";

const KEYS_HIVEIDS = "hiveIds";
const DEFAULTS_HIVEIDS = [];

const FORMAT_DATE = "DD-MM-YYYY";

class StatFacade {

    constructor() {
        this.clearData();
    }

    clearData() {
        this.idx = 0;
        this.data = [];
    }

    addData(json) {
        for(let row of json)
            this.data.push(Object.assign({}, row, {key: this.idx++}));
    }
}

/**
 * ### props:
 * * headerText : _String_;
 * * apiId: _String_;
 * * hiveIds: _Array(Integer)_;
 */
export class HiveStats extends React.Component {

    constructor(props) {
        super(props);

        this.startDate = moment().subtract(1, "weeks").startOf("day");
        this.endDate = moment().endOf("day");

        this.apiId = this.props[KEYS_APIID] || DEFAULTS_APIID;
        this.headerText = this.props[KEYS_HEADERTEXT] || DEFAULTS_HEADERTEXT;
        this.hiveIds = this.props[KEYS_HIVEIDS] || DEFAULTS_HIVEIDS;

        this.facade = new StatFacade();

        this.cols = [
            {
                title: "Action",
                dataIndex: ACT_OUT_ACTION,
                key: ACT_OUT_ACTION
            },
            {
                title: "Item",
                dataIndex: ACT_OUT_NAME,
                key: ACT_OUT_NAME
            },
            {
                title: "Amount",
                dataIndex: ACT_OUT_AMOUNT,
                key: ACT_OUT_AMOUNT
            },
            {
                title: "Average price",
                dataIndex: ACT_OUT_AVERAGE,
                key: ACT_OUT_AVERAGE
            },
            {
                title: "Total price",
                dataIndex: ACT_OUT_TOTAL,
                key: ACT_OUT_TOTAL
            },
        ];

        this.state = {
            data: [],
            loading: true,
            error: false,
        };
        this.handleRangeChange = this.handleRangeChange.bind(this);
        this.refreshStats = this.refreshStats.bind(this);

        this.refreshStats(this.apiId);
    }


    refreshStats(apiId) {

        this.facade.clearData();
        let fetchFailed = false;

        getFeedingStats(apiId, this.startDate, this.endDate, this.hiveIds)
            .then(json => {

                this.facade.addData(json);

                getHoneyStats(apiId, this.startDate, this.endDate, this.hiveIds)
                    .then(json => {

                        this.facade.addData(json);

                        getTreatmentStats(apiId, this.startDate, this.endDate,
                            this.hiveIds).then(json => {

                            this.facade.addData(json);
                            this.setState({
                                loading: false,
                                error: false,
                                data: this.facade.data
                            })

                        }).catch(json => {

                            if(!fetchFailed) {
                                fetchFailed = true;
                                this.setState(
                                    {loading: false, error: json.message});
                            }
                        });

                    }).catch(json => {

                    if(!fetchFailed) {
                        fetchFailed = true;
                        this.setState({loading: false, error: json.message});
                    }
                });

            }).catch(json => {

            if(!fetchFailed) {
                fetchFailed = true;
                this.setState({loading: false, error: json.message});
            }
        });
    }

    handleRangeChange(objArray, strArray) {
        this.startDate = objArray[0];
        this.endDate = objArray[1];
        this.refreshStats(this.apiId);
    }

    render() {
        const range = [this.startDate, this.endDate];

        const tableJSX = this.state.loading?
            <LoadingIndicator/> : this.state.error?
                <WarningIndicator messageJSX={this.state.error}/> :
                <Table bordered dataSource={this.state.data}
                    columns={this.cols} size="small"/>;

        const rangeJSX = (
            <div className="rangePickerWrapper"><RangePicker
                format={FORMAT_DATE} defaultValue={range}
                onChange={this.handleRangeChange} className="rangePicker"/>
            </div>);

        return (
            <Card title={this.headerText} extra={rangeJSX}
                className="statBlock">
                {tableJSX}
            </Card>
        );
    }
}

/**
 * ### props:
 * * headerText : _String_;
 * * apiId: _String_;
 * * hiveIds: _Array(Integer)_;
 */
export class ContributorStats extends React.Component {

    constructor(props) {
        super(props);

        this.startDate = moment().subtract(1, "weeks").startOf("day");
        this.endDate = moment().endOf("day");

        this.apiId = this.props[KEYS_APIID] || DEFAULTS_APIID;
        this.headerText = this.props[KEYS_HEADERTEXT] || DEFAULTS_HEADERTEXT;

        this.facade = new StatFacade();

        this.cols = [
            {
                title: "Username",
                dataIndex: CON_OUT_CONTR,
                key: CON_OUT_CONTR
            },
            {
                title: "Inspections",
                dataIndex: CON_OUT_INSPECT,
                key: CON_OUT_INSPECT
            },
            {
                title: "Feedings",
                dataIndex: CON_OUT_FEEDING,
                key: CON_OUT_FEEDING
            },
            {
                title: "Treatments",
                dataIndex: CON_OUT_TREATMENT,
                key: CON_OUT_TREATMENT
            },
            {
                title: "Honey collections",
                dataIndex: CON_OUT_HONEY,
                key: CON_OUT_HONEY
            },
        ];

        this.state = {
            data: [],
            loading: true,
            error: false,
        };
        this.handleRangeChange = this.handleRangeChange.bind(this);
        this.refreshStats = this.refreshStats.bind(this);

        this.refreshStats(this.apiId);
    }

    refreshStats(apiId) {

        this.facade.clearData();

        getContributorStats(apiId, this.startDate, this.endDate).then(json => {

            this.facade.addData(json);
            this.setState(
                {loading: false, error: false, data: this.facade.data});

        }).catch(json => {
            this.setState(
                {loading: false, error: json.message});
        });
    }

    handleRangeChange(objArray, strArray) {
        this.startDate = objArray[0];
        this.endDate = objArray[1];
        this.refreshStats(this.apiId);
    }

    render() {
        const range = [this.startDate, this.endDate];

        const tableJSX = this.state.loading?
            <LoadingIndicator/> : this.state.error?
                <WarningIndicator messageJSX={this.state.error}/> :
                <Table bordered dataSource={this.state.data}
                    columns={this.cols} size="small"/>;

        const rangeJSX = (
            <div className="rangePickerWrapper"><RangePicker
                format={FORMAT_DATE} defaultValue={range}
                onChange={this.handleRangeChange} className="rangePicker"/>
            </div>);

        return (
            <Card title={this.headerText} extra={rangeJSX}
                className="statBlock">
                {tableJSX}
            </Card>
        );
    }
}