import { PolymerElement } from "@polymer/polymer/polymer-element";
import { customElement, property } from '@polymer/decorators';
import '@polymer/paper-button';
import '@polymer/paper-dialog';
import '@polymer/paper-spinner/paper-spinner';
import '@polymer/paper-input/paper-input';
import { asTemplate } from "../shared/util";
import * as TEMPLATE from './bad-login-button.html';
import { sendLoginRequest } from "../service/api";

@customElement('bad-login')
class BadLoginButton extends PolymerElement {
  @property({ type: Boolean })
  loginOpened: boolean = false;
  @property({ type: String })
  username?: string;
  @property({ type: String })
  password?: string;
  @property({ type: String })
  errorMessage?: string;
  @property({ type: Boolean })
  inProgress?: boolean;


  static get template() {
    return asTemplate(TEMPLATE);
  }

  openLogin() {
    this.loginOpened = true;
    this.errorMessage = '';
  }

  doClose() {
    this.loginOpened = false;
  }

  async doLogin(_: CustomEvent) {
    this.errorMessage = '';
    try {
      this.inProgress = true;
      let token = await sendLoginRequest(this.username || "", this.password || "");
      this.dispatchEvent(new CustomEvent('login-result', { detail: token }));
      this.loginOpened = false;
    } catch (error) {
      this.errorMessage = "Credenziali non corrette!";
      console.error(`Error: ${error}`);
    } finally {
      this.inProgress = false;
    }
  }

}
