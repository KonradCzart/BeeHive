import React from "react";
import {getWeather} from "../util/ApiFacade";
import {Card, Col, Divider, Icon, Popover, Row} from "antd";
import LoadingIndicator from "../common/LoadingIndicator";
import {WarningIndicator} from "../common/WarningIndicator";

/**
 * # props:
 * * apiId : _String_;
 * * city : _String_;
 *
 */
export class WeatherComponent extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            loading: true,
            error: false,
            weather: {},
        };

        this.refreshWeather = this.refreshWeather.bind(this);
        this.componentDidMount = this.componentDidMount.bind(this);
    }

    componentDidMount() {
        this.refreshWeather(this.props.apiId);
    }

    refreshWeather(apiId) {
        getWeather(apiId).then(json => {
            this.setState({loading: false, error: false, weather: json});
        }).catch(json => {
            this.setState({loading: false, error: json.message});
        });
    }

    render() {
        const header = (<h2>{"Recent weather for " + this.props.city}</h2>);

        if(this.state.loading)
            return (<Card title={header}><LoadingIndicator/></Card>);
        if(this.state.error)
            return (<Card title={header}><WarningIndicator
                messageJSX={this.state.error}/></Card>);

        const type = (<h2><Icon type="cloud"/> {this.state.weather.type}</h2>);

        return (
            <Card title={header} extra={type}>
                <Row gutter={32}>
                    <Col span={6}>
                        <h2><Popover content="Temperature"><Icon
                            type="fire"/></Popover></h2>
                        <Divider type="vertical"/>
                    </Col>
                    <Col span={6}>
                        <h4>min: {this.state.weather.minTemp}°C</h4>
                        <Divider type="vertical"/>
                    </Col>
                    <Col span={6}>
                        <h3>{this.state.weather.temp}°C</h3>
                        <Divider type="vertical"/>
                    </Col>
                    <Col span={6}>
                        <h4>max: {this.state.weather.maxTemp}°C</h4>
                    </Col>
                </Row>
                <Row gutter={32}>
                    <Col span={8}>
                        <h2><Popover content="Nebulosity"><Icon
                            type="cloud"/></Popover> {this.state.weather.clouds}%
                        </h2>
                        <Divider type="vertical"/>
                    </Col>
                    <Col span={8}>
                        <h2><Popover content="Humidity"><Icon
                            type="experiment"/></Popover> {this.state.weather.humid}%
                        </h2>
                        <Divider type="vertical"/>
                    </Col>
                    <Col span={8}>
                        <h2><Popover content="Downfall"><Icon
                            type="cloud-download"/></Popover> {this.state.weather.rain} mm/3h
                        </h2>
                    </Col>
                </Row>
                <Row gutter={32}>
                    <Col span={8}>
                        <h2><Popover content="Air pressure"><Icon
                            type="dashboard"/></Popover> {this.state.weather.press} hPa
                        </h2>
                        <Divider type="vertical"/>
                    </Col>
                    <Col span={8}>
                        <h2><Popover content="Wind direction"><Icon
                            type="compass"/></Popover> {this.state.weather.deg}°
                        </h2>
                        <Divider type="vertical"/>
                    </Col>
                    <Col span={8}>
                        <h2><Popover content="Wind speed"><Icon
                            type="rocket"/></Popover> {this.state.weather.speed} km/h
                        </h2>
                    </Col>
                </Row>
            </Card>
        );
    }
}