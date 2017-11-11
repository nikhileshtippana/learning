var helloworld = function(){   
   return 'Hello World'; 
};

window.Calculator = {
    add: function() {
        var result = 0;
        for (var i=0; i<arguments.length; i++) {
            result += arguments[i];
        }
        return result;
    },
    substract: function(a,b) {
        return a-b;
    },
    multiply: function() {
        var result = 1;
        for (var i=0; i<arguments.length; i++) {
            result *= arguments[i];
        }
        return result;
    }
};