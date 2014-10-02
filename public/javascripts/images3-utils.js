/**
 * 
 */

function initialImagePlant() {
	return {
		name: "",
		bucket: {
			accessKey: "",
		  	secretKey: "",
		    name: ""
		  },
		  resizingConfig: {
			  unit: "PERCENT",
			  width: 0,
			  height: 0,
			  isKeepProportions: true
		  }
		};
}

function initialTemplate(imagePlantId) {
	return {
		name: "",
		resizingConfig: {
			unit: "PIXEL",
			width: 0,
			height: 0,
			isKeepProportions: true
		}
	};
}
