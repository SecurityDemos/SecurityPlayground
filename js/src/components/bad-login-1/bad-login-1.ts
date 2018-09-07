/**
 * @customElement
 * @polymer
 */
import {PolymerElement} from "@polymer/polymer/polymer-element";
import {customElement, property} from '@polymer/decorators';
import {UserInfo} from "../../model/model";
import {sendUserInfoRequest} from "../../service/api";
import {asTemplate} from "../../shared/util";
import './bad-login-button';
import * as TEMPLATE from "./bad-login-1.html";
import '@polymer/paper-button';

@customElement('bad-login-1')
class BadLogin1 extends PolymerElement {
    @property({ type: Boolean })
    isLoggedIn: boolean = false;
    @property({ type: Object })
    userInfo?: UserInfo;

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