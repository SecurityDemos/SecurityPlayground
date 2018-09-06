import { PolymerElement } from "@polymer/polymer/polymer-element";
import { customElement, property } from '@polymer/decorators';
import '@polymer/paper-button';
import { asTemplate } from '../shared/util';

import './bad-login-button';
import { UserInfo } from "../model/model";
import * as TEMPLATE from "./playground-app.html";
import { sendUserInfoRequest } from "../service/api";


/**
 * @customElement
 * @polymer
 */
@customElement('playground-app')
class PlaygroundApp extends PolymerElement {
  @property({ type: Boolean })
  isLoggedIn: boolean = false;
  @property({ type: Object })
  userInfo?: UserInfo;

  authToken?: string;

  static get template() {
    return asTemplate(TEMPLATE);
  }

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

}
