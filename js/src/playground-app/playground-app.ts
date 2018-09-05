import { html, PolymerElement } from "@polymer/polymer/polymer-element";
import { customElement, property } from '@polymer/decorators';
import '@polymer/paper-button';
import { GET } from '../shared/util';
import { BACKEND_URL } from '../environments/environment';


/**
 * @customElement
 * @polymer
 */
@customElement('playground-app')
class PlaygroundApp extends PolymerElement {
  @property({ type: String })
  prop1: string = "test";

  static get template() {
    return html`
      <style>
        :host {
          display: block;
        }
      </style>
      <h2>Hello [[prop1]]!</h2>
      <paper-button on-click='askServer'>click me </paper-button>
    `;
  }

  async askServer() {
    console.log('hi there!');
    let res = await GET({ url: `${BACKEND_URL}/test` });
    console.log(`Result : ${res.responseText}`);
  }

}
