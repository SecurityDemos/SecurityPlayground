import { html, PolymerElement } from "@polymer/polymer/polymer-element";
import { customElement, property } from '@polymer/decorators';
import '@polymer/paper-button';
import '@polymer/paper-dialog';
import '@polymer/paper-spinner/paper-spinner';
import '@polymer/paper-input/paper-input';
import { BACKEND_URL } from "../environments/environment";
import { GET } from "../shared/util";


@customElement('bad-login')
export default class BadLoginButton extends PolymerElement {
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
    return html`
    <style>
      .error {
        color:red;
      }
    </style>
    <paper-dialog opened={{loginOpened}}>
      <h2>Please login</h2>
      <paper-input label='username' value="{{username}}"></paper-input>
      <paper-input label='password' value="{{password}}"></paper-input>
      <span>Password volutamente in chiaro</span>
      <div>Suggerimento : "whatever' OR '1'='1"</div>
      <div class='error'>[[errorMessage]]</div>
      <paper-spinner active='[[inProgress]]'></paper-spinner>
      <div>
        <paper-button on-click='doLogin' dialog-commit>Submit</paper-button>
        <paper-button on-click='doClose' dialog-rollback>Cancel</paper-button>
      </div>
    </paper-dialog>
    <paper-button on-click='openLogin'>Login</paper-button>
    `
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
      let res = await GET({
        url: `${BACKEND_URL}/login/bad?username={username}&password={password}`, parameters: {
          username: this.username,
          password: this.password,
        }
      });
      if (res.response) {
        this.dispatchEvent(new CustomEvent('login-result', { detail: res.response }));
        this.loginOpened = false;
      } else {
        this.errorMessage = "Credenziali non corrette!";
      }
    } catch (error) {
      this.dispatchEvent(new CustomEvent('login-error', { detail: error }));
    } finally {
      this.inProgress = false;
    }
  }

}
