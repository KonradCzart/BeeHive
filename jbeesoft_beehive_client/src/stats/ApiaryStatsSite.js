import React from "react";
import {Col, Divider, Layout, Row} from "antd";
import {BackIcon} from "../common/Buttons";
import {ContributorStats, HiveStats} from "./Stats";
import {HivesChecker} from "./HivesChecker";
import {getHives, getMyPrivileges} from "../util/ApiFacade";
import LoadingIndicator from "../common/LoadingIndicator";
import {WarningIndicator} from "../common/WarningIndicator";

const {Header, Content} = Layout;

const KEYS_API_NAME = "apiName";

const HIVE_STATS_READING = "HIVE_STATS_READING";

/**
 * ## props:
 * * history : _Any_ Router history;
 * * apiName : _String_ viewed apiary name.
 */
export class ApiaryStatsSite extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            error: false,
            hives: [],
            hiveIds: [],
            privileges: [],
        };
        this.handleHivesChange = this.handleHivesChange.bind(this);
        this.refreshHives = this.refreshHives.bind(this);
        this.refreshPrivileges = this.refreshPrivileges.bind(this);
        this.componentDidMount = this.componentDidMount.bind(this);
        this.isPrivileged = this.isPrivileged.bind(this);
    }

    refreshHives(apiId) {
        getHives(apiId).then(json => {
            this.setState({
                loading: false,
                error: false,
                hives: json.hives,
                hiveIds: json.hives.map((hive) => hive.id)
            });
        }).catch(json => {
            this.setState({loading: false, error: json.message});
        })
    }

    refreshPrivileges() {
        getMyPrivileges(this.props.match.params.apiId).then(json => {
            this.setState({loading: false, error: false, privileges: json});
        }).catch(json => {
            this.setState({loading: false, error: json.message});
        });
    }

    componentDidMount() {
        this.refreshHives(this.props.match.params.apiId);
        this.refreshPrivileges(this.props.match.params.apiId);
    }

    isPrivileged() {
        for(let privilege of this.state.privileges)
            if(privilege.name === HIVE_STATS_READING)
                return true;
        return false;
    }

    handleHivesChange(hiveIds) {
        this.setState({hiveIds});
    }

    render() {
        if(this.state.loading)
            return <LoadingIndicator/>;
        if(this.state.error)
            return <WarningIndicator messageJSX={this.state.error}/>;

        return (
            <Layout>
                <Header>
                    <Row gutter={32}>
                        <Col span={4}>
                            <h1><BackIcon history={this.props.history}/></h1>
                        </Col>
                        <Col span={20}>
                            <h1>Apiary {this.props[KEYS_API_NAME]} statistics</h1>
                        </Col>
                    </Row>
                </Header>
                <Content>
                    <Divider/>
                    <HivesChecker hives={this.state.hives}
                        disabled={!this.isPrivileged()}
                        loading={this.state.loading} error={this.state.error}
                        onChange={this.handleHivesChange}/>
                    <Divider/>
                    <HiveStats headerText={<h2>Hives statistics</h2>}
                        apiId={this.props.match.params.apiId}
                        hiveIds={this.state.hiveIds}/>
                    <Divider/>
                    <ContributorStats
                        headerText={<h2>Contributors statistics</h2>}
                        apiId={this.props.match.params.apiId}/>
                </Content>
            </Layout>
        );
    }
}