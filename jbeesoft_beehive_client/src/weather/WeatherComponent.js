import React from "react";
import {getWeather} from "../util/ApiFacade";
import {Card, Col, Divider, Icon, Row} from "antd";

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
        const type = (<h2><Icon type="cloud"/> {this.state.weather.type}</h2>);

        return (
            <Card title={<h2>{"Recent weather for " + this.props.city}</h2>}
                extra={type}>
                <Row gutter={32}>
                    <Col span={6}>
                        <h2><Icon type="fire"/></h2>
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
                        <h2><Icon type="cloud"/> {this.state.weather.clouds}%
                        </h2>
                        <Divider type="vertical"/>
                    </Col>
                    <Col span={8}>
                        <h2><Icon type="experiment"/> {this.state.weather.humid}%
                            <Divider type="vertical"/>
                        </h2>
                    </Col>
                    <Col span={8}>
                        <h2><Icon
                            type="cloud-download"/> {this.state.weather.rain} mm/3h
                        </h2>
                    </Col>
                </Row>
                <Row gutter={32}>
                    <Col span={8}>
                        <h2><Icon type="dashboard"/> {this.state.weather.press} hPa</h2>
                        <Divider type="vertical"/>
                    </Col>
                    <Col span={8}>
                        <h2><Icon type="compass"/> {this.state.weather.deg}°</h2>
                        <Divider type="vertical"/>
                    </Col>
                    <Col span={8}>
                        <h2><Icon type="rocket"/> {this.state.weather.speed} km/h</h2>
                    </Col>
                </Row>
            </Card>
    );
    }
    }