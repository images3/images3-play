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

var imageS3Services = angular.module('imageS3Services', ['ngResource']);

imageS3Services.factory('ImagePlants', ['$resource', function($resource) {
	return $resource(
			null,
			null,
			{
				getAll: {
						url: '/rest/v1/imageplants?page=:pageId',
						method: 'GET',
						params:{pageId: '@pageId'},
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
						url: '/rest/v1/imageplants/:id/templates?page=:pageId&archived=:isArchived',
						method: 'GET',
						params:{
							id: '@id',
							pageId: '@pageId',
							isArchived: '@isArchived'
						},
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
						url: '/rest/v1/imageplants/:id/images?page=:pageId&template=:template',
						method: 'GET',
						params:{id: '@id'},
						params:{pageId: '@pageId'},
						params:{template: '@template'},
						isArray: false}
			});
}]);


