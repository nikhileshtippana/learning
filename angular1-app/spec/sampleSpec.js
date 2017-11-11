describe("Hello World", function(){ 
   
   it("should Return Hello world",function(){ 
      expect(helloworld()).toEqual('Hello World'); 
   }); 

});

describe("Calculator", function(){ 
   var a=3;
    var b=2;
    
   it("should return 5 when 3 and 2 are added",function(){ 
      expect(Calculator.add(3,2)).toEqual(5); 
   });
    
   it("should return 1 when 3 and 2 are substracted",function(){ 
      expect(Calculator.substract(3,2)).toEqual(1); 
   });
    
   it("should return 6 when 3 and 2 are multiplied",function(){ 
      expect(Calculator.multiply(3,2)).toEqual(6); 
   });

});