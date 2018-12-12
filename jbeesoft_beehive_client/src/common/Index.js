import React, { Component } from 'react';
import './Index.css';
import logoSrc from "../logo.svg"

class Index extends Component {
	render() {
		return (
			<div className="index-welcome">
                <img className="logo" src={logoSrc}/>
				<h1 className="title">
					Welcome to BeeHive - absolutely the best place for your bees!
				</h1>
			</div>
		);
	}
}

export default Index;