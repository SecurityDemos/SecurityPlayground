import {PolymerElement} from "@polymer/polymer/polymer-element";
import {customElement, property, observe} from '@polymer/decorators';
import '@polymer/paper-button';
import '@polymer/paper-dialog';
import '@polymer/paper-spinner/paper-spinner';
import '@polymer/paper-input/paper-input';
import '@polymer/iron-collapse/iron-collapse';
import '@polymer/paper-icon-button/paper-icon-button';
import '@polymer/iron-icons/iron-icons';
import {asTemplate} from "../../shared/util";
import * as TEMPLATE from './bad-login-button.html';
import {sendLoginRequest, sendLoginRequest2, sendLoginRequestOk} from "../../service/api";

@customElement('bad-login')
class BadLoginButton extends PolymerElement {
    @property({type: Boolean})
    loginOpened: boolean = false;
    @property({type: String})
    username?: string;
    @property({type: String})
    password?: string;
    @property({type: String})
    errorMessage?: string;
    @property({type: Boolean})
    inProgress?: boolean;
    @property({type: String})
    showHintToggleIcon: string = 'expand-more';
    @property({type: Boolean})
    hintOpened: boolean = false;
    @property({type: String})
    mode: "bad1" | "bad2" | "ok" = "bad1";

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

    @observe('hintOpened')
    changeHintIcon() {
        this.showHintToggleIcon = this.hintOpened ? 'expand-less' : 'expand-more';
    }

    async doLogin(_: CustomEvent) {
        this.errorMessage = '';
        try {
            this.inProgress = true;
            let token;
            switch (this.mode) {
                case "bad1":
                    token = await sendLoginRequest(this.username || "", this.password || "");
                    break;
                case "bad2":
                    token = await sendLoginRequest2(this.username || "", this.password || "");
                    break;
                case "ok":
                    token = await sendLoginRequestOk(this.username || "", this.password || "");
                    break;
            }
            this.dispatchEvent(new CustomEvent('login-result', {detail: token}));
            this.loginOpened = false;
        } catch (error) {
            this.errorMessage = "Credenziali non corrette!";
            console.error(`Error: ${error}`);
        } finally {
            this.inProgress = false;
        }
    }

    toggleHint() {
        this.hintOpened = !this.hintOpened;
    }

}
