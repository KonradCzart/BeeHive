import React from "react";
import {Col, Divider, Row} from "antd";
import {HiveStats} from "./Stats";
import {BackIcon} from "../common/Buttons";


export class HiveStatsSite extends React.Component {

    render() {

        const headerJSX = (
            <Row gutter={32}>
                <Col span={4}>
                    <h1><BackIcon history={this.props.history}/></h1>
                </Col>
                <Col span={20}>
                    <h1>Hive statistics</h1>
                </Col>
            </Row>
        );

        return (
            <div>
                <Divider/>
                <HiveStats headerText={headerJSX}
                    apiId={this.props.match.params.apiId}
                    hiveIds={[this.props.match.params.hiveId]}/>
            </div>
        );
    }
}