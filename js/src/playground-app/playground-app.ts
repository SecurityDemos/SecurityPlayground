import { PolymerElement } from "@polymer/polymer/polymer-element";
import { customElement, property } from '@polymer/decorators';
import '@polymer/paper-button';
import { asTemplate } from '../shared/util';

import './bad-login-button';
import { UserInfo } from "../model/model";
import * as TEMPLATE from "./playground-app.html";


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

  static get template() {
    return asTemplate(TEMPLATE);
  }

  loggedIn(e: CustomEvent) {
    this.userInfo = e.detail;
    this.isLoggedIn = true;
  }

  onLogout() {
    this.isLoggedIn = false;
    this.userInfo = undefined;
  }

}
