// function add(a:number,b:number,c?:number):number{
//     return c ? a+b+c : a+b;
// }
//here c is optional parameter
// console.log(add(1,2));
// console.log(add(1,2,3));


// const multiply=(a:number,b:number,c:number=10):number => a*b*c;
//here c is req.parameter by default it will have 10 if not passed.
// console.log(multiply(1,2));


// function addition(a:number,b:number,...c:number[]):number{
//     return a+b+c.reduce((num1,num2)=>num1+num2);
// }
// here c is rest parameter. we can even use c:number[] and pass an array
// let nums=[1,2,3,4,5];
// console.log(addition(1,2,...nums));
// console.log(addition(1,2,1,2,3,4,5));

// function getItems<Type>(val:Type):Type{
//     return val;
// }
// let generic1=getItems(1);
// let generic2=getItems("hi");
// console.log(generic1+" "+generic2);