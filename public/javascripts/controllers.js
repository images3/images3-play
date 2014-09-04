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
				$state.go('imageplant.info',{imagePlantId: imagePlant.id});
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
    function ($scope, $state, $stateParams, Images) {
		$scope.imagePlantId = $stateParams.imagePlantId;
		$scope.imageId = $stateParams.imageId;
	}
]);