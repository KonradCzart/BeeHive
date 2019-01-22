import React from "react";
import {getWeather} from "../util/ApiFacade";

/**
 * # props:
 * * apiId : _String_;
 *
 */
export class WeatherComponent extends React.Component {

    constructor(props) {
        super(props);
        this.refreshWeather = this.refreshWeather.bind(this);
        this.componentDidMount = this.componentDidMount.bind(this);
    }

    componentDidMount() {
        this.refreshWeather(this.props.apiId);
    }

    refreshWeather(apiId) {
        getWeather(apiId).then(json => {
            console.log("WEATHER FETCHED", json);
        }).catch(json => {
            console.log("WEATHER FAILED", json);
        });
    }

    render() {
        return (
            <div>
                <h3>Weather for {this.props.location}</h3>
            </div>
        );
    }
}