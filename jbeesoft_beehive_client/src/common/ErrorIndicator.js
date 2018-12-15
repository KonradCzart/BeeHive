import {Icon} from "antd"
import React from "react";

export default function ErrorIndicator() {
    return (
        <div style={{
            display: "flex",
            width: "100%",
            height: "100%",
            alignItems: "center",
            justifyContent: "center",
            fontSize: "30px",
        }}>
            <Icon type="exclamation"/>
        </div>
    );
}