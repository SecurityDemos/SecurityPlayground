import {PolymerElement} from "@polymer/polymer/polymer-element";
import {customElement, property} from '@polymer/decorators';

import '@polymer/paper-tabs/paper-tab';
import '@polymer/paper-tabs/paper-tabs';
import '@polymer/iron-pages/iron-pages';
import '@polymer/iron-flex-layout/iron-flex-layout';
import {asTemplate} from '../shared/util';

import * as TEMPLATE from "./playground-app.html";
import '../components/bad-login-1/bad-login-1';
import '../components/bad-login-2/bad-login-2';
import '../components/login-ok/login-ok';


/**
 * @customElement
 * @polymer
 */
@customElement('playground-app')
class PlaygroundApp extends PolymerElement {

    @property({type: Number})
    pageSelected: number = 0;


    static get template() {
        return asTemplate(TEMPLATE);
    }


}
