/*******************************************************************************
 * Copyright 2014 Rui Sun
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
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

