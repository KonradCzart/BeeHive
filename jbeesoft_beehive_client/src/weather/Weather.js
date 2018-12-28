import React, { Component } from 'react';
import { getCurrentWeather, getApiaryData } from '../../util/APIUtils';
import './Weather.css';
import { Redirect } from 'react-router-dom';
import NotFound from '../../common/NotFound';
import ServerError from '../../common/ServerError';


class Weather extends Component {

	constructor(props) {
        super(props);
        this.state = {
            rainMililitersPer3h: null,
	    minTemp: null,
	    maxTemp: null,
	    temp: null,
	    pressure: null,
	    humidity: null,
	    cloudsPercentage: null,
	    windSpeed: null,
	    windDeg: null,
	    weatherType: null,
            isLoading: false
        };
	this.loadWeather = this.loadWeather.bind(this);    
    }

    
    loadWeather(location) {
        this.setState({
            isLoading: true
        });

        getCurrentWeather(location)
        .then(response => {
            this.setState({
                rainMililitersPer3h: response,
	        minTemp: response,
	        maxTemp: response,
	        temp: response,
	        pressure: response,
	        humidity: response,
       	        cloudsPercentage: response,
	        windSpeed: response,
	        windDeg: response,
	        weatherType: response,
                isLoading: false
            });
        }).catch(error => {
            if(error.status === 404) {
                this.setState({
                    notFound: true,
                    isLoading: false
                });
            } else {
                this.setState({
                    serverError: true,
                    isLoading: false
                });        
            }
        });        
    }

    render() {
        if(!this.props.isAuthenticated){
            return(
                <Redirect to="/"/>
            )
        }

        if(this.state.isLoading) {
            return <LoadingIndicator />;
        }

        if(this.state.notFound) {
            return <NotFound />;
        }

        if(this.state.serverError) {
            return <ServerError />;
        }


        return (
            <div className="weather">
                { 
                    
		    <div className="rainMililitersPer3h">{this.state.rainMililitersPer3h}</div>
                    <div className="minTemp">{this.state.minTemp}</div>
                    <div className="maxTemp">{this.state.maxTemp}</div>
                    <div className="temp">{this.state.temp}</div>
		    <div className="pressure">{this.state.pressure}</div>
	 	    <div className="humidity">{this.state.humidity}</div>
		    <div className="cloudsPercentage">{this.state.cloudsPercentage}</div>
		    <div className="windSpeed">{this.state.windSpeed}</div>
		    <div className="windDeg">{this.state.windDeg}</div>
		    <div className="weatherType">{this.state.weatherType}</div>
                                         
                }
            </div>
        );
    }
}

export default Weather;
