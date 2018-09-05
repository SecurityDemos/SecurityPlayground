import { html, PolymerElement } from "@polymer/polymer/polymer-element";
import { customElement, property } from '@polymer/decorators';
import '@polymer/paper-button';
import { GET } from '../shared/util';
import { BACKEND_URL } from '../environments/environment';

import './bad-login-button';
import { UserInfo } from "../model/model";


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
    return html`
      <style>
        :host {
          display: block;
          font-family: Roboto;
        }
      </style>
      <h2>Security playground</h2>
      <dom-if if=[[!isLoggedIn]]>
        <template>
          <bad-login on-login-result='loggedIn'></bad-login>
        </template>
      </dom-if>
      <dom-if if=[[isLoggedIn]]>
        <template>
          Benvenuto [[userInfo.displayName]] !! <paper-button on-click='onLogout'>Logout</paper-button>
        </template>
      </dom-if>
    `;
  }

  loggedIn(e: CustomEvent) {
    this.userInfo = JSON.parse(e.detail);
    this.isLoggedIn = true;
  }

  onLogout() {
    this.isLoggedIn = false;
    this.userInfo = undefined;
  }

}
