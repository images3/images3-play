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
var autoRefreshImageReport = {};
var currentImagePlantId = null;

var imageS3 = angular.module('imageS3', ['ui.router', 'imageS3Controllers', 'ui.bootstrap','cgPrompt', 'cgBusy', 'flow']);


imageS3.run(['$rootScope', '$state', '$stateParams', 
    function($rootScope, $state, $stateParams) {
		$rootScope.$state = $state;
		$rootScope.$stateParams = $stateParams;
		$rootScope.$on('$locationChangeSuccess', function(event, currRoute, prevRoute) {
			var position = currRoute.indexOf('/overview');
			if ((position + 9) != currRoute.length) {
				autoRefreshImageReport[currentImagePlantId] = false;
				currentImagePlantId = null;
			}
		});
	}
]);

imageS3.config(['$stateProvider', '$urlRouterProvider', 
    function($stateProvider, $urlRouterProvider) {
		$urlRouterProvider.otherwise('/imageplants');
		
		$stateProvider
			.state('imageplants', {
				url: '/imageplants?page',
				templateUrl: 'html/imageplant-list.html',
				controller: 'ImagePlantController'
			})
			.state('imageplant-create', {
				url: '/imageplant-create',
				templateUrl: 'html/imageplant-create.html',
				controller: 'ImagePlantController'
			})
			.state('imageplant', {
				url: '/imageplants/:imagePlantId',
				templateUrl: 'html/imageplant.html',
				controller: 'ImagePlantController'
			})
			.state('imageplant.overview', {
				url: '/overview',
				views: {
					'': {
						templateUrl: 'html/imageplant-overview.html',
						controller: 'ImagePlantController'
					},
					'report@imageplant.overview': {
						templateUrl: 'html/image-report.html',
						controller: 'ImageReportController'
					}
				}
			})
			.state('imageplant.templates', {
				url: '/templates?page&archived',
				templateUrl: 'html/template-list.html',
				controller: 'TemplateController'
			})
			.state('imageplant.template-create', {
				url: '/createTemplate?page&archived',
				templateUrl: 'html/template-create.html',
				controller: 'TemplateController'
			})
			.state('imageplant.template-update', {
				url: '/templates/:templateName?page&archived',
				templateUrl: 'html/template-update.html',
				controller: 'TemplateController'
			})
			.state('imageplant.images', {
				url: '/images?page&template',
				templateUrl: 'html/image-list.html',
				controller: 'ImageController'
			})
			.state('imageplant.imagecontent', {
				url: '/images/:imageId?template',
				templateUrl: 'html/image-content.html',
				controller: 'ImageController'
			})
			.state('imageplant.images-upload', {
				url: '/uploadImage?page&template',
				templateUrl: 'html/image-upload.html',
				controller: 'ImageController'
			})
			.state('imageplant.update', {
				templateUrl: 'html/imageplant-update.html',
				controller: 'ImagePlantController',
			});
	}
]);
