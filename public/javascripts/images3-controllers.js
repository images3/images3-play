/**
 * 
 */

var imageS3Controllers = angular.module('imageS3Controllers', ['imageS3Services']);

imageS3Controllers.controller('ImagePlantController', ['$scope', '$state', '$stateParams', 'prompt', 'ImagePlants', 
    function ($scope, $state, $stateParams, prompt, ImagePlants) {
	
		$scope.imagePlant = initialImagePlant($stateParams.imagePlantId);
		$scope.errorCode = 0;
		$scope.errorMessage = '';

		
		$scope.viewImagePlant = function(imagePlant) {
			$state.go('imageplant.overview', {imagePlantId: imagePlant.id});
		}
		
		$scope.showImagePlants = function() {
			ImagePlants.getAll({cursor: null}, function(response) {
				$scope.imagePlants = response.results;
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
					if (error.status == 400) {
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
	    	ImagePlants.update({imagePlantId: imagePlant.id}, updateImagePlant,
				function(response) {
	    			$state.go('imageplant.info', {imagePlantId: imagePlant.id});
				},
				function(error) {
					if (error.status == 400) {
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
						$state.go('imageplant.templates', {});
					},
					function(error) {
						if (error.status == 400) {
							var errorResp = error.data;
							$scope.errorCode = errorResp.code;
							$scope.errorMessage = errorResp.message;
						}
					}
			);
		}
		
		$scope.showTemplate = function() {
			Templates.getByName({id: $stateParams.imagePlantId, name: $stateParams.templateName}, 
					function(response) {
				$scope.template = response;
			})
		}

	    $scope.showTemplates = function () {
			Templates.getByImagePlantId({id: $stateParams.imagePlantId}, function(response) {
				$scope.templates = response.results;
			})
		}
	    
	    $scope.viewTemplate = function(template) {
			$state.go('imageplant.template-update', {templateName: template.id.templateName});
		}
	    
	    $scope.removeTemplate = function(template) {
	    	prompt({
				"title": "Do you want to continue to remove this template?",
				"message": "After remove this template, there is no way to undo this.",
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
					    		$state.go('imageplant.templates', {});
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

imageS3Controllers.controller('ImageListController', ['$scope', '$state', '$stateParams', 'Images', 
    function ($scope, $state, $stateParams, Images) {
		Images.getByImagePlantId({id: $stateParams.imagePlantId}, function(response) {
		$scope.images = response.results;
		
		$scope.viewImageContent = function(image) {
			$state.go('imageplant.images.imagecontent',{imageId: image.id.imageId});
		}
	})}
]);

imageS3Controllers.controller('ImageContentController', ['$scope', '$state', '$stateParams', 
    function ($scope, $state, $stateParams) {
		$scope.imagePlantId = $stateParams.imagePlantId;
		$scope.imageId = $stateParams.imageId;
	}
]);

var imageReportCounts = null;
var imageReportSize = null;

imageS3Controllers.controller('ImageReportController', 
		['$rootScope', '$scope', '$state', '$stateParams', '$timeout', 'ImagePlants',
    function ($rootScope, $scope, $state, $stateParams, $timeout, ImagePlants) {
			
		$scope.refreshImageCharts = function() {
			imageReportCounts = null;
			imageReportSize = null;
			authRefreshImageReport = true;
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
					var countData = generateImageReportMorrisData(
							response.times, response.values.COUNTS_INBOUND, response.values.COUNTS_OUTBOUND);
					drawImageReportCounts(countData);
					var sizeData = generateImageReportMorrisData(
							response.times, response.values.SIZE_INBOUND, response.values.SIZE_OUTBOUND);
					drawImageReportSize(sizeData);
				});
				if (authRefreshImageReport) {
					start = start + refreshRate;
					$timeout(tick, refreshRate);
				}
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

