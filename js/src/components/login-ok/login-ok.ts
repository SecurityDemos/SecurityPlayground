/**
 * @customElement
 * @polymer
 */
import {PolymerElement} from "@polymer/polymer/polymer-element";
import {customElement, property, computed} from '@polymer/decorators';
import '@polymer/paper-button';
import '@polymer/iron-flex-layout/iron-flex-layout';
import {UserInfo} from "../../model/model";
import {sendUserInfoRequest} from "../../service/api";
import {asTemplate} from "../../shared/util";
import * as TEMPLATE from "./login-ok.html";
import * as README from "./login-ok.md";

import '../common-layout/bad-login-button';
import '../common-layout/common-layout';

@customElement('login-ok')
class LoginOk extends PolymerElement {
    @property({type: Boolean})
    isLoggedIn: boolean = false;
    @property({type: Object})
    userInfo?: UserInfo;
    @property({type: String})
    readme: string = README as any;
    
    authToken?: string;

    async loggedIn(e: CustomEvent) {
        this.authToken = e.detail;
        this.userInfo = await sendUserInfoRequest(this.authToken || '');
        this.isLoggedIn = true;
    }

    onLogout() {
        this.isLoggedIn = false;
        this.userInfo = undefined;
        this.authToken = undefined;
    }

    static get template() {
        return asTemplate(TEMPLATE);
    }
}