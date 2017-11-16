/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

/**
 * Appointment service
 */

/**
 * Controller for all appointments results
 */


kenyaemrApp.controller('ActiveAppointmentsBlocks', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

        $scope.activeAppointments = [];

        /**
         * Refreshes the visit types with active visits
         */
        $scope.init = function () {
            $http.get(ui.fragmentActionLink('wellness', 'AppointmentUtils', 'getActiveAppointmentBlocks'))
                .success(function (data) {
                    $scope.activeAppointments = data;

                });
        };

}]);