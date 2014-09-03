/**
 * 
 */
var imageS3 = angular.module('imageS3', ['ui.router', 'imageS3Controllers']);

imageS3.run(['$rootScope', '$state', '$stateParams', 
    function($rootScope, $state, $stateParams) {
		$rootScope.$state = $state;
		$rootScope.$stateParams = $stateParams;
	}
]);

imageS3.config(['$stateProvider', '$urlRouterProvider', 
    function($stateProvider, $urlRouterProvider) {
		$urlRouterProvider.otherwise('/imageplant-list');
		
		$stateProvider
			.state('imageplants', {
				url: '/imageplant-list',
				templateUrl: 'imageplant-list.html',
				controller: 'ImagePlantListController'
			})
			.state('imageplant', {
				url: '/imageplant/:imagePlantId',
				templateUrl: 'imageplant.html',
				controller: 'ImagePlantController'
			})
			.state('imageplant.info', {
				url: '/info',
				templateUrl: 'imageplant-info.html',
				controller: 'ImagePlantController'
			})
			.state('imageplant.templates', {
				url: '/templates',
				templateUrl: 'template-list.html',
				controller: 'TemplateListController'
			})
			.state('imageplant.images', {
				url: '/images',
				templateUrl: 'image-list.html',
				controller: 'ImageListController'
			})
			.state('imageplant.images.imagecontent', {
				url: '/:imageId/imagecontent',
				templateUrl: 'image-content.html',
				controller: 'ImageContentController'
			});
	}
]);