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

var imageS3 = angular.module('imageS3', ['ui.router', 'imageS3Controllers', 'ui.bootstrap','cgPrompt', 'cgBusy']);


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
				url: '/imageplants',
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
				url: '/templates',
				templateUrl: 'html/template-list.html',
				controller: 'TemplateController'
			})
			.state('imageplant.template-create', {
				url: '/template-create',
				templateUrl: 'html/template-create.html',
				controller: 'TemplateController'
			})
			.state('imageplant.template-update', {
				url: '/template-update/:templateName',
				templateUrl: 'html/template-update.html',
				controller: 'TemplateController'
			})
			.state('imageplant.images', {
				url: '/images',
				templateUrl: 'html/image-list.html',
				controller: 'ImageListController'
			})
			.state('imageplant.images.imagecontent', {
				url: '/:imageId/imagecontent',
				templateUrl: 'html/image-content.html',
				controller: 'ImageContentController'
			})
			.state('imageplant.update', {
				url: '/info',
				templateUrl: 'html/imageplant-update.html',
				controller: 'ImagePlantController',
				
			});
	}
]);
