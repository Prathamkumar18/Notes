//String
// let fname:string = 'Pratham'
// console.log(fname.toUpperCase())
// console.log(fname.substring(0,3))
// console.log(fname.replace('a','c'))

//Number
// let age:number=10;
// age=10.5;
// let num=parseInt("123")
// console.log(age+" "+num)

//Boolean
// let isValid:boolean=true

//Array
// let names:string[];
// names=["Pratham","Ayush"];
// console.log(names[0]);
// let nums:number[]=[1,2,3,4,5];
// names.push("hello");
// names.pop();
// console.log(names.find((name)=> name==="Pratham"));
// console.log(nums.filter((num)=> num>2));
// console.log(nums.reduce((prev,curr)=> prev+curr));
// nums.forEach((n)=> console.log(n));

// Enums
// const enum Colors{
//     red,green,blue
// }
// let c:Colors=Colors.blue
// console.log(c);

//Tuples- Fixed sized array.
// let nums:[number,number];
// function swap(a:number,b:number):[number,number]{
//     return [b,a];
// }
// nums=swap(1,2);
// console.log(nums[0]+" "+nums[1]);
// let values:[number,string]=[1,"pratham"];
// values.push(2,"ayush");
// values.forEach((a)=>console.log(a));

//Any - avoid using it
// let x:any
// x=10;
// x="pratham"

//Objects and Type alias
// type Employee={
//     readonly id:number,
//     name:string,
//     phone?:number,
//     retire(date:Date):void
// };
// let emp1:Employee={
//     id:0,
//     name:"pratham",
//     retire:(date:Date)=>{
//         console.log(date);
//     }
// };

// Union type- Can hold multiple datatype
// function demo(a:number | string):number{
//     if(typeof a === 'number'){
//         return a;
//     }
//     else{
//         return parseInt(a);
//     }
// }
// console.log(demo(1)+" "+demo("10kg"));

//Intersection type- should have all datatype mentioned
// type draggable={
//     drag:()=>void
// };
// type resizable={
//     resize:()=>void
// };
// type adjustable={
//     adjust:()=>void
// };
// type UiWidget= draggable & resizable & adjustable;
// let tool:UiWidget={
//     drag() {},
//     resize() {},
//     adjust() {},
// }

//Literals type
// type val=50 | 100 | 200;
// let qty:val=50;
// type stringVal="cm"|"inch";
// let size:stringVal="cm";