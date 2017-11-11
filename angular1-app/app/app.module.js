console.clear();
var nikAngularApp = angular.module("nikAngularApp", ['ngRoute', 'ui.bootstrap']);

nikAngularApp.config(function ($routeProvider) {
    $routeProvider.when('/', {
                templateUrl: 'app/component/main/main.html',
                controller: 'mainController'
            }).when('/java', {
                templateUrl: 'app/component/java/java.html',
                controller: 'javaController'
            }).otherwise({
                redirectTo: "/"
            });
});

nikAngularApp.component("navigator", {
    template: '<nav role="navigation"><a ng-repeat="page in $ctrl.pages" href="/#!{{page.path}}" ng-class="{selected: $ctrl.isPageSelected(this)}" ng-click="$ctrl.navClick(this)">{{page.title}}</a></nav>',
    controller: function() {
        var self = this;
        this.pages = [
        {
            path: '/',
            title: 'Home'
        }, 
        {
            path: 'java',
            title: 'Java'
        }];
        
        this.navSelected = this.pages[0];
    
        this.navClick = function (item) {
            self.navSelected = item.page;
        };

        this.isPageSelected = function(item) {
            return self.navSelected == item.page;
        };
    }
});