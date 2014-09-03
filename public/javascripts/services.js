/**
 * 
 */

var imageS3Services = angular.module('imageS3Services', ['ngResource']);

imageS3Services.factory('ImagePlants', ['$resource', function($resource) {
	return $resource(
			null,
			null,
			{
				getAll: {
						url: '/rest/v1/imageplants?page=:cursor',
						method: 'GET',
						params:{cursor: '@cursor'},
						isArray: false},
				getById: {
						url: '/rest/v1/imageplants/:id',
						method: 'GET',
						params:{id: '@id'},
						isArray: false}
			});
}]);

imageS3Services.factory('Templates', ['$resource', function($resource) {
	return $resource(
			null,
			null,
			{
				getByImagePlantId: {
						url: '/rest/v1/imageplants/:id/templates',
						method: 'GET',
						params:{id: '@id'},
						isArray: false}
			});
}]);

imageS3Services.factory('Images', ['$resource', function($resource) {
	return $resource(
			null,
			null,
			{
				getByImagePlantId: {
						url: '/rest/v1/imageplants/:id/images',
						method: 'GET',
						params:{id: '@id'},
						isArray: false}
			});
}]);


