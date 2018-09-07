/**
 * @customElement
 * @polymer
 */
import {PolymerElement} from "@polymer/polymer/polymer-element";
import {computed, customElement, property} from '@polymer/decorators';
import {asTemplate} from "../../shared/util";
import * as TEMPLATE from "./common-layout.html";
import '@polymer/paper-button';
import '@polymer/iron-flex-layout/iron-flex-layout';
import '@polymer/marked-element/marked-element';


@customElement('common-layout')
class CommonLayout extends PolymerElement {
    @property({type: String})
    readme: string = "";

    static get template() {
        return asTemplate(TEMPLATE);
    }
}