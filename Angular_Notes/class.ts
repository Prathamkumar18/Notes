import {login, User} from "./interface";

interface Address{
    street:string,
    city:string,
    state:string
}

class Employee{
    #id:number;
    protected name:string;
    address:Address;
    static company:string;
    constructor(id:number,name:string,address:Address){
        this.#id=id;this.name=name;this.address=address;
    }
    getNameWithAddress():string{
        return `${this.name} stays at ${this.address}`;
    }
    get empId():number{
        return this.#id;
    }
    set empId(id:number){
        this.#id=id;
    }
}
class Manager extends Employee implements login{
    constructor(id:number,name:string,address:Address){
        super(id,name,address);
    }
    getNameWithAddress(): string {
        return `${this.name} (Manager) stays at ${this.address}`;
    }
    login(): User {
        return {id:1,name:"pratham"};
    }
}
let emp=new Employee(1,"john",{street:"ABC",city:"Bangalore",state:"Karnataka"});
Employee.company="subex"
let manager=new Manager(1,"Prats",{street:"ABC",city:"Bangalore",state:"Karnataka"});
console.log(manager.getNameWithAddress());
console.log(emp);
emp.empId=100;
console.log(emp.empId);