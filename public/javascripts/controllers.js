/**
 * 
 */

var imageS3Controllers = angular.module('imageS3Controllers', ['imageS3Services']);

imageS3Controllers.controller('ImagePlantListController', ['$scope', '$state', 'ImagePlants', 
    function ($scope, $state, ImagePlants) {
		ImagePlants.getAll({cursor: null}, function(response) {
			//console.log('HERE======>' + JSON.stringify(response));
			$scope.imagePlants = response.results;
		
			$scope.viewImagePlant = function(imagePlant) {
				$state.go('imageplant.overview',{imagePlantId: imagePlant.id});
			}
	})}

]);

imageS3Controllers.controller('ImagePlantController', ['$scope', '$state', '$stateParams', 'ImagePlants', 
    function ($scope, $state, $stateParams, ImagePlants) {
		ImagePlants.getById({id: $stateParams.imagePlantId}, function(response) {
			//console.log('HERE======>' + JSON.stringify(response));
			$scope.imagePlant = response;
	})}
]);

imageS3Controllers.controller('TemplateListController', ['$scope', '$state', '$stateParams', 'Templates', 
    function ($scope, $state, $stateParams, Templates) {
		Templates.getByImagePlantId({id: $stateParams.imagePlantId}, function(response) {
		//console.log('HERE======>' + JSON.stringify(response));
		$scope.templates = response.results;
	})}
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

imageS3Controllers.controller('ImageReportController', ['$rootScope', '$scope', '$state', '$stateParams', '$timeout', 'ImagePlants',
    function ($rootScope, $scope, $state, $stateParams, $timeout, ImagePlants) {
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

