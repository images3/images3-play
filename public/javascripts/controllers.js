/**
 * 
 */

var imageS3Controllers = angular.module('imageS3Controllers', ['imageS3Services']);

imageS3Controllers.controller('ImagePlantController', ['$scope', '$state', '$stateParams', 'ImagePlants', 
    function ($scope, $state, $stateParams, ImagePlants) {
	
		$scope.imagePlant = initialImagePlant($stateParams.imagePlantId);
		$scope.errorCode = 0;
		$scope.errorMessage = '';
	
		$scope.viewImagePlant = function(imagePlant) {
			$state.go('imageplant.overview', {imagePlantId: imagePlant.id});
		}
		
		$scope.showImagePlants = function() {
			ImagePlants.getAll({cursor: null}, function(response) {
				//console.log('HERE======>' + JSON.stringify(response));
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
	    	ImagePlants.remove({imagePlantId: imagePlant.id}, 
					function(response) {
	    		$state.go('imageplants', {});
			})
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

imageS3Controllers.controller('TemplateController', ['$scope', '$state', '$stateParams', 'Templates',
    function ($scope, $state, $stateParams, Templates) {
	
		$scope.template = initialTemplate($stateParams.imagePlantId);
		$scope.errorCode = 0;
		$scope.errorMessage = '';
	
		$scope.createTemplate = function (template) {
			console.log('HERE======>' + angular.toJson(template, true));
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
				console.log('HERE======>' + angular.toJson(response, true));
				$scope.templates = response.results;
			})
		}
	    
	    $scope.viewTemplate = function(template) {
	    	console.log('HERE======>' + angular.toJson(template, true));
			$state.go('imageplant.template-update', {templateName: template.id.templateName});
		}
	    
	    $scope.removeTemplate = function(template) {
	    	console.log('HERE======>' + angular.toJson(template, true));
	    	Templates.remove({imagePlantId: template.id.imagePlantId, templateName: template.id.templateName}, 
					function(response) {
			    		$state.go('imageplant.templates', {});
					}
	    	)
		}
	    
	    $scope.updateTemplateAvailability = function(template) {
	    	var isArchived = template.isArchived;
	    	if (template.isArchived) {
	    		template.isArchived = false;
	    	} else {
	    		template.isArchived = true;
	    	}
	    	Templates.update({imagePlantId: template.id.imagePlantId, templateName: template.id.templateName}, 
	    			template,
					function(data) {
			    		$state.go('imageplant.template-update', {templateName: template.id.templateName});
					},
					function(error) {
						template.isArchived = isArchived;
					}
	    	)
		}
	    
	}
]);

imageS3Controllers.controller('ImageListController', ['$scope', '$state', '$stateParams', 'Images', 
    function ($scope, $state, $stateParams, Images) {
		Images.getByImagePlantId({id: $stateParams.imagePlantId}, function(response) {
 		//console.log('HERE======>' + JSON.stringify(response));
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

imageS3Controllers.controller('ImageReportController', 
		['$rootScope', '$scope', '$state', '$stateParams', '$timeout', 'ImagePlants',
    function ($rootScope, $scope, $state, $stateParams, $timeout, ImagePlants) {
		console.log('HERE======>ImagePlantId: ' + $stateParams.imagePlantId);
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

