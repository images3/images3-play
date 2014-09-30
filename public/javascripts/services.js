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
						isArray: false},
				getImageReport: {
						url: '/rest/v1/imageplants/:id/imagereport',
						method: 'GET',
						params:{
							id: '@id',
							templateName: '@templateName',
							startTime: '@startTime',
							length: '@length',
							timeUnit: '@timeUnit',
							types: '@types'
							},
						isArray: false},
				create: {
						url: '/rest/v1/imageplants',
						method: 'POST'},
				remove: {
						url: '/rest/v1/imageplants/:imagePlantId',
						method: 'DELETE',
						params:{
							imagePlantId: '@imagePlantId'
						}},
				update: {
					url: '/rest/v1/imageplants/:imagePlantId',
					method: 'PUT',
					params:{
						imagePlantId: '@imagePlantId'
						}},
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
						isArray: false},
				getByName: {
					url: '/rest/v1/imageplants/:id/templates/:name',
					method: 'GET',
					params:{
						id: '@id',
						name: '@name'
							},
					isArray: false},
				create: {
					url: '/rest/v1/imageplants/:imagePlantId/templates',
					method: 'POST',
					params:{
						imagePlantId: '@imagePlantId'
						}},
				remove: {
					url: '/rest/v1/imageplants/:imagePlantId/templates/:templateName',
					method: 'DELETE',
					params:{
						imagePlantId: '@imagePlantId', 
						templateName: '@templateName'
						}},
				update: {
					url: '/rest/v1/imageplants/:imagePlantId/templates/:templateName',
					method: 'PUT',
					params:{
						imagePlantId: '@imagePlantId', 
						templateName: '@templateName'
						}},
								
			},
			{
		        stripTrailingSlashes: false
		    }
			);
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


