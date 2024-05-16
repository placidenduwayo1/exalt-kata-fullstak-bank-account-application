import { environment } from "src/environments/environment";

export class GetApisService {

    gateway = environment.gatewayApi;

    getCustomerApi(): string {
        return `${this.gateway}/api-customer`;
    }
    getBankAccountApi(): string {
        return `${this.gateway}/api-bank-account`;
    }
    getOperationApi(): string {
        return `${this.gateway}/api-operation`;
    }
}