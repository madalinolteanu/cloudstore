import { BaseEntity } from './../../shared';

export class Account implements BaseEntity {
    constructor(
        public id?: any,
        public token?: string,
        public userCode?: string,
        public userType?: string,
        public username?: string,
        public password?: string,
        public email?: string,
        public firstName?: string,
        public lastName?: string,
        public active?: boolean,
        public imageUrl?: string,
        public activationKey?: string,
        public resetKey?: string,
        public resetDate?: string,
        public creationDate?: string
    ) {}
}
