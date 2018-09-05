import {html, PolymerElement} from "@polymer/polymer/polymer-element";
import {customElement, property} from '@polymer/decorators';
import '@polymer/paper-button';


/**
 * @customElement
 * @polymer
 */
@customElement('playground-app')
class PlaygroundApp extends PolymerElement {
  @property({type:String})
  prop1:string="test";

  static get template() {
    return html`
      <style>
        :host {
          display: block;
        }
      </style>
      <h2>Hello [[prop1]]!</h2>
      <paper-button>click me </paper-button>
    `;
  }

}
