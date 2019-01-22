import React from "react";
import {Col, Divider, Layout, Row} from "antd";
import {BackIcon} from "../common/Buttons";
import {ContributorStats, HiveStats} from "./Stats";
import {HivesChecker} from "./HivesChecker";

const {Header, Content, Sider} = Layout;

const KEYS_API_NAME = "apiName";

/**
 * ## props:
 * * history : _Any_ Router history;
 * * apiName : _String_ viewed apiary name.
 */
export class ApiaryStatsSite extends React.Component {

    constructor(props) {
        super(props);
        this.apiId = this.props.match.params.apiId;
        this.state = {
            hiveIds: [],
        };
        this.handleHivesChange = this.handleHivesChange.bind(this);
    }

    handleHivesChange(hiveIds) {
        this.setState({hiveIds});
    }

    render() {
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
                    <Layout>
                        <Sider>
                            <Divider />
                            <HivesChecker apiId={this.apiId}
                                onChange={this.handleHivesChange}/>
                        </Sider>
                        <Content>
                            <Divider/>
                            <HiveStats headerText={<h2>Hives statistics</h2>}
                                apiId={this.apiId}
                                hiveIds={this.state.hiveIds}/>
                            <Divider />
                            <ContributorStats
                                headerText={<h2>Contributors statistics</h2>}
                                apiId={this.apiId}/>
                        </Content>
                    </Layout>
                </Content>
            </Layout>
        );
    }
}