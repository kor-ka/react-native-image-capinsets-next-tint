import React, { Component } from "react";
import { Image } from "react-native";

class ImageCapInset extends Component {
  render() {
    return <Image {...this.props} resizeMode={"stretch"} />;
  }
}

export default ImageCapInset;
