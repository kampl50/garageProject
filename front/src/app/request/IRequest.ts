export interface IRequest {
    id?:string;
    clientId: string,
    workerId: string,
    dateVisit: string,
    problemDescription:string,
    kindNotification:string,
    statusNotification:string
    clientCar: {
        mark:string,
        model:string,
        dateProduction: string,
        kindEngine: string,
        power:number,
    },
    price?: number
    nameAndSurnameWorkers?: [string],
    partsToOrder?:any;
}
