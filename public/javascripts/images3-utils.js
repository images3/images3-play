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
		id: {
			imagePlantId: imagePlantId,
			templateName: ""
		},
		resizingConfig: {
			unit: "PIXEL",
			width: 0,
			height: 0,
			isKeepProportions: true
		}
	};
}