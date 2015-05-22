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

var imageS3Controllers = angular.module('imageS3Controllers', ['imageS3Services']);

imageS3Controllers.controller('ImagePlantController', ['$scope', '$state', '$stateParams', 'prompt', 'ImagePlants', 
    function ($scope, $state, $stateParams, prompt, ImagePlants) {
	
		$scope.imagePlant = initialImagePlant($stateParams.imagePlantId);
		$scope.errorCode = 0;
		$scope.errorMessage = '';
		$scope.awsAPISecretKey = '*********';
		
		$scope.viewImagePlant = function(imagePlant) {
			currentImagePlantId = imagePlant.id;
			$state.go('imageplant.overview', {imagePlantId: imagePlant.id});
		}
		
		$scope.showImagePlants = function(pageId) {
			ImagePlants.getAll({pageId: pageId}, function(response) {
				$scope.imagePlants = response.results;
				$scope.page = response.page;
			})
		}
	
		$scope.showImagePlant = function() {
			ImagePlants.getById({id: $stateParams.imagePlantId}, function(response) {
				$scope.imagePlant = response;
			})
		}
		
		$scope.createImagePlant = function(imagePlant) {
			ImagePlants.create(imagePlant, 
				function(response) {
					$state.go('imageplants', {});
				},
				function(error) {
					if (error.status >= 400
							&& error.status <= 417) {
						var errorResp = error.data;
						$scope.errorCode = errorResp.code;
						$scope.errorMessage = errorResp.message;
					}
				}
			)
			
		}
		
		$scope.removeImagePlant = function(imagePlant) {
			
			prompt({
				"title": "Do you want to continue to remove this image plant?",
				"message": "Removing this image plant will remove all associated images and template completely!" +
						" There is no way to undo this.",
				"buttons": [
					{
						"label": "Continue",
						"primary": true
					},
					{
						"label": "Cancel",
						"cancel": true
					}
				]
			}).then(function(result){
				if (result.primary) {
					$scope.myPromise = ImagePlants.remove({imagePlantId: imagePlant.id}, 
							function(response) {
			    		$state.go('imageplants', {});
					});
				}
			});
		}
		
		$scope.updateImagePlant = function(imagePlant) {
			var updateImagePlant = {};
			updateImagePlant.id = imagePlant.id;
			updateImagePlant.name = imagePlant.name;
			updateImagePlant.bucket = imagePlant.bucket;
			if ($scope.awsAPISecretKey != '*********') {
                updateImagePlant.bucket.secretKey = $scope.awsAPISecretKey;
			}
	    	ImagePlants.update({imagePlantId: imagePlant.id}, updateImagePlant,
				function(response) {
		    		prompt({
						"title": "Success",
						"message": "ImagePlant, '" + imagePlant.name + "' has been updated.",
						"buttons": [
							{
								"label": "Ok",
								"primary": true
							}
						]
					}).then(function(result){
						$state.transitionTo($state.current, $stateParams, {
						    reload: true,
						    inherit: false,
						    notify: true
						});
					});
	    			
				},
				function(error) {
					if (error.status >= 400
							&& error.status <= 417) {
						var errorResp = error.data;
						$scope.errorCode = errorResp.code;
						$scope.errorMessage = errorResp.message;
					}
				}
	    	)
		}
		
	}
]);

imageS3Controllers.controller('TemplateController', ['$scope', '$state', '$stateParams', 'prompt', 'Templates',
    function ($scope, $state, $stateParams, prompt, Templates) {
	
		$scope.template = initialTemplate($stateParams.imagePlantId);
		$scope.errorCode = 0;
		$scope.errorMessage = '';
		
		$scope.createTemplate = function (template) {
			Templates.create(
					{imagePlantId: $stateParams.imagePlantId},
					template,
					function(response) {
						$scope.viewTemplates($stateParams.page, $stateParams.archived);
					},
					function(error) {
						if (error.status >= 400
								&& error.status <= 417) {
							var errorResp = error.data;
							$scope.errorCode = errorResp.code;
							$scope.errorMessage = errorResp.message;
						}
					}
			);
		}
		
		$scope.viewTemplate = function(template, page, archived) {
			$state.go('imageplant.template-update', {templateName: template, page:page, archived:archived}, {location: false});
		}
		
		$scope.loadTemplate = function(templateName) {
			Templates.getByName({id: $stateParams.imagePlantId, name: templateName}, 
					function(response) {
				$scope.template = response;
			})
		}
		
		$scope.viewTemplates = function(page, isArchived) {
			if ((typeof(isArchived) === 'undefined')) {
				isArchived = '';
			}
			if ((typeof(page) === 'undefined')) {
				page = '';
			}
			$state.go('imageplant.templates', {page: page, archived: isArchived});
		}
	    
	    $scope.loadTemplates = function (page, isArchived) {
			Templates.getByImagePlantId({id: $stateParams.imagePlantId, pageId: page, isArchived: isArchived}, function(response) {
				$scope.templates = response.results;
				$scope.page = response.page;
			})
		}
	    
	    $scope.viewCreateTemplate = function(page, archived) {
			$state.go('imageplant.template-create', {page: page, archived: archived}, {location: false});
		}
	    
	    $scope.removeTemplate = function(template) {
	    	prompt({
				"title": "Do you want to continue to remove this template?",
				"message": "After removing this template, there is no way to undo this.",
				"buttons": [
					{
						"label": "Continue",
						"primary": true
					},
					{
						"label": "Cancel",
						"cancel": true
					}
				]
			}).then(function(result){
				if (result.primary) {
					Templates.remove({imagePlantId: template.id.imagePlantId, templateName: template.id.templateName}, 
							function(response) {
								$scope.viewTemplates($stateParams.page, $stateParams.archived);
							}
			    	)
				}
			});
	    	
		}
	    
	    $scope.updateTemplateAvailability = function(template) {
	    	var newTemplate = angular.copy(template);
	    	if (newTemplate.isArchived) {
	    		newTemplate.isArchived = false;
	    		Templates.update({imagePlantId: newTemplate.id.imagePlantId, templateName: newTemplate.id.templateName}, 
						newTemplate,
						function(data) {
							//console.log("HERE======>data:  " + angular.toJson(data, true));
				    		$state.go('imageplant.template-update', {templateName: data.id.templateName});
				    		template.isArchived = data.isArchived; 
						},
						function(error) {
						}
		    	)
	    	} else {
	    		newTemplate.isArchived = true;
	    		prompt({
					"title": "Do you want to continue to deactivate this template?",
					"message": "Deactivating this template will cause all associated images cannot be accessed.",
					"buttons": [
						{
							"label": "Continue",
							"primary": true
						},
						{
							"label": "Cancel",
							"cancel": true
						}
					]
				}).then(function(result){
					if (result.primary) {
						Templates.update({imagePlantId: newTemplate.id.imagePlantId, templateName: newTemplate.id.templateName}, 
								newTemplate,
								function(data) {
						    		$state.go('imageplant.template-update', {templateName: data.id.templateName});
						    		template.isArchived = data.isArchived; 
								},
								function(error) {
								}
				    	)
					}
				});
	    	}
		}
	}
]);


imageS3Controllers.controller('ImageController', ['$scope', '$state', '$stateParams', '$modal', 'Images', 
    function ($scope, $state, $stateParams, $modal, Images) {
		$scope.errorCode = 0;
		$scope.errorMessage = '';
		$scope.uploadStarted = false;
		
		$scope.viewImages = function(pageId, templateName) {
			$state.go('imageplant.images', {page: pageId, template: templateName});
		}
	
		$scope.loadImages = function () {
			if ((typeof($stateParams.template) === 'undefined')) {
				$stateParams.template = 'master';
			}
			if ((typeof($stateParams.page) === 'undefined')) {
				$stateParams.page = '';
			}
			Images.getByImagePlantId({id: $stateParams.imagePlantId, pageId: $stateParams.page, template: $stateParams.template}, function(response) {
				$scope.images = response.results;
				$scope.page = response.page;
			})
		}
		
		$scope.viewImageContent = function(imageId, template, currentPage) {
			$stateParams.imageId = imageId;
			$stateParams.template = template;
			var imageContentModal = $modal.open({
				url: '/images/:imageId?template',
				templateUrl: 'html/image-content.html',
				controller: 'ImageController',
				resolve: {
					$stateParams: function() {
						return $stateParams;
					}
				}
			});
		}
		
		$scope.viewUploadImage = function(pageId, templateName) {
			$state.go('imageplant.images-upload', {page: pageId, template: templateName}, {location: false});
		}
		
		$scope.loadImageContent = function(template) {
			if (! (typeof(template) === 'undefined')) {
				$stateParams.template = template;
			}
			if (typeof($stateParams.template) === 'undefined') {
				$stateParams.template = '';
			}
			$scope.uploadStarted = true;
			Images.getByVersion({id: $stateParams.imagePlantId, imageId: $stateParams.imageId, template: $stateParams.template}, 
					function(response) {
						$scope.uploadStarted = false;
						$scope.imageContent = '/rest/v1/imageplants/' + $stateParams.imagePlantId + '/imagefiles/' + $stateParams.imageId + '?template=' + $stateParams.template;
						$scope.currentTemplate = $stateParams.template;
					},
					function(error) {
						$scope.uploadStarted = false;
					}
			)
			
		}
		
		$scope.uploadImage = function($flow) {
			$flow.opts.testChunks = false;
			$flow.opts.simultaneousUploads = 1;
			$flow.opts.forceChunkSize = false;
			$flow.opts.method = "octet";
			$flow.opts.target = "/rest/v1/imageplants/" + $stateParams.imagePlantId + "/images";
			$flow.upload();
			$scope.uploadStarted = true;
            $flow.on('fileSuccess', function (file,message) {
            	$scope.uploadStarted = false;
            	$scope.viewImages($stateParams.page, $stateParams.template);
            });
            $flow.on('fileError', function (file,message) {
            	$scope.uploadStarted = false;
            	var data = angular.fromJson(message);
            	$scope.errorCode = data.code;
            	if (data.code == 400108) {
            		$scope.errorMessage = "Unsupported image format found!";
            	}
            });
		}
		
	}
]);

var imageReportCounts = null;
var imageReportSize = null;

imageS3Controllers.controller('ImageReportController', 
		['$rootScope', '$scope', '$state', '$stateParams', '$timeout', 'ImagePlants',
    function ($rootScope, $scope, $state, $stateParams, $timeout, ImagePlants) {
			
		$scope.refreshImageCharts = function() {
			var imagePlantId = $stateParams.imagePlantId;
			imageReportCounts = null;
			imageReportSize = null;
			autoRefreshImageReport[imagePlantId] = true;
			var start = new Date().getTime() - (10 * 60 * 1000); //back 10 mins
			var refreshRate = 10 * 1000; //milliseconds
			(function tick() {
				ImagePlants.getImageReport(
						{
							id: $stateParams.imagePlantId,
							templateName: '',
							startTime: start,
							length: '10',
							timeUnit: 'MINUTES',
							types: 'COUNTS_INBOUND, COUNTS_OUTBOUND, SIZE_INBOUND, SIZE_OUTBOUND'
							},
						function(response) {
					if (!autoRefreshImageReport[imagePlantId]) {
						return;
					}
					var countData = generateImageReportMorrisData(
							response.times, response.values.COUNTS_INBOUND, response.values.COUNTS_OUTBOUND);
					drawImageReportCounts(countData);
					var sizeData = generateImageReportMorrisData(
							response.times, response.values.SIZE_INBOUND, response.values.SIZE_OUTBOUND);
					drawImageReportSize(sizeData);
					start = start + refreshRate;
					$timeout(tick, refreshRate);
				});
				
			})();
		}
		
	}
]);

function generateImageReportMorrisData(times, inboundValues, outboundValues) {
	var data = [];
	for (i=0; i<times.length; i++) {
		var item = {
				y: times[i],
				a: inboundValues[i],
				b: outboundValues[i]
		};
		data[i] = item;
	}
	return data;
}

function drawImageReportCounts(items) {
	if (null == imageReportCounts) {
		imageReportCounts = new Morris.Line({
		    element: 'counts',
		    data: items,
		    xkey: 'y',
		    ykeys: ['a', 'b'],
		    labels: ['Inbound', 'Outbound'],
		    lineWidth: 2,
		    pointSize: 2,
		    smooth: false,
		    xLabels: 'minute',
		    hideHover: true,
		  });
	} else {
		imageReportCounts.setData(items);
	}
}

function drawImageReportSize(items) {
	if (null == imageReportSize) {
		imageReportSize = new Morris.Line({
		    element: 'size',
		    data: items,
		    xkey: 'y',
		    ykeys: ['a', 'b'],
		    labels: ['Inbound', 'Outbound'],
		    lineWidth: 2,
		    pointSize: 2,
		    smooth: false,
		    xLabels: 'minute',
		    hideHover: true,
		  });
	} else {
		imageReportSize.setData(items);
	}
}

