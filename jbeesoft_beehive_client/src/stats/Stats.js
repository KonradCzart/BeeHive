import React from "react";
import "./Stats.css";
import {Col, DatePicker, Divider, Layout, Row, Table} from "antd";
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
const {Header, Content} = Layout;

const KEYS_APIID = "apiId";
const KEYS_HEADERTEXT = "headerText";
const KEYS_HIVEIDS = "hiveIds";

const FORMAT_DATE = "DD-MM-YYYY";

class StatFacade {

    constructor() {
        this.clearData = this.clearData.bind(this);
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
        this.componentDidMount = this.componentDidMount.bind(this);
        this.componentDidUpdate = this.componentDidUpdate.bind(this);
    }

    /**
     * if apiId or hiveIds differ, refreshStats
     */
    componentDidUpdate(nextProps) {
        if(this.props.apiId !== nextProps.apiId)
            this.refreshStats(this.props.apiId);
        else if(this.props.hiveIds.length !== nextProps.hiveIds.length)
            this.refreshStats(this.props.apiId);
        else
            for(let i = 0; i < this.props.hiveIds.length; i++)
                if(this.props.hiveIds[i] !== nextProps.hiveIds[i]) {
                    this.refreshStats(this.props.apiId);
                    return;
                }
    }

    componentDidMount() {
        this.refreshStats(this.props[KEYS_APIID]);
    }

    refreshStats(apiId) {

        this.facade.clearData();
        let fetchFailed = false;

        getFeedingStats(apiId, this.startDate, this.endDate,
            this.props[KEYS_HIVEIDS]).then(json => {

            this.facade.addData(json);

            getHoneyStats(apiId, this.startDate, this.endDate,
                this.props[KEYS_HIVEIDS]).then(json => {

                this.facade.addData(json);

                getTreatmentStats(apiId, this.startDate, this.endDate,
                    this.props.hiveIds).then(json => {
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
        this.refreshStats(this.props[KEYS_APIID]);
    }

    render() {

        const range = [this.startDate, this.endDate];

        const tableJSX = this.state.loading?
            <LoadingIndicator/> : this.state.error?
                <WarningIndicator messageJSX={this.state.error}/> :
                <Table bordered dataSource={this.state.data}
                    columns={this.cols} size="medium"/>;

        const rangeJSX = (<RangePicker format={FORMAT_DATE} defaultValue={range}
            onChange={this.handleRangeChange}/>);

        return (
            <Layout>
                <Header>
                    <Row gutter={32}>
                        <Col span={12}>
                            {this.props[KEYS_HEADERTEXT]}
                        </Col>
                        <Col span={12}>
                            {rangeJSX}
                        </Col>
                    </Row>
                </Header>
                <Divider/>
                <Content>
                    {tableJSX}
                </Content>
            </Layout>
        );
    }
}

/**
 * ### props:
 * * headerText : _String_;
 * * apiId: _String_;
 */
export class ContributorStats extends React.Component {

    constructor(props) {
        super(props);

        this.startDate = moment().subtract(1, "weeks").startOf("day");
        this.endDate = moment().endOf("day");

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
        this.componentDidMount = this.componentDidMount.bind(this);
    }

    componentDidMount() {
        this.refreshStats(this.props[KEYS_APIID]);
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
        this.refreshStats(this.props[KEYS_APIID]);
    }

    render() {
        const range = [this.startDate, this.endDate];

        const tableJSX = this.state.loading?
            <LoadingIndicator/> : this.state.error?
                <WarningIndicator messageJSX={this.state.error}/> :
                <Table bordered dataSource={this.state.data}
                    columns={this.cols} size="medium"/>;

        const rangeJSX = (<RangePicker format={FORMAT_DATE} defaultValue={range}
            onChange={this.handleRangeChange}/>);

        return (
            <Layout>
                <Header>
                    <Row gutter={32}>
                        <Col span={12}>
                            {this.props[KEYS_HEADERTEXT]}
                        </Col>
                        <Col span={12}>
                            {rangeJSX}
                        </Col>
                    </Row>
                </Header>
                <Divider/>
                <Content>
                    {tableJSX}
                </Content>
            </Layout>
        );
    }
}