/**
 * @customElement
 * @polymer
 */
import {PolymerElement} from "@polymer/polymer/polymer-element";
import {customElement, property, computed} from '@polymer/decorators';
import {UserInfo} from "../../model/model";
import {sendUserInfoRequest} from "../../service/api";
import {asTemplate} from "../../shared/util";
import './bad-login-button';
import * as TEMPLATE from "./bad-login-1.html";
import '@polymer/paper-button';
import '@polymer/iron-flex-layout/iron-flex-layout';
import 'marked';
import '@polymer/marked-element/marked-element';
import * as README from "./bad-login-1.md";

@customElement('bad-login-1')
class BadLogin1 extends PolymerElement {
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