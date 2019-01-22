import "./WarningIndicator.css";
import {Icon, Layout} from "antd";
import React from "react";

const {Content} = Layout;

/**
 * @param props {Object} : messageJSX : _String_
 * @constructor
 */
export function WarningIndicator(props) {

    return (
        <Layout className="warnInd-parent">
            <Content>
                <Icon type="warning" theme="twoTone" twoToneColor="#ff6d00"
                    className="warnInd-icon"/>
                {props["messageJSX"]?
                    <span className="warnInd-msg">{props["messageJSX"]}</span> :
                    <br/>}
            </Content>
        </Layout>
    );
}