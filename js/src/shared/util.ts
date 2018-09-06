import * as template from 'url-template';
import { html } from '@polymer/polymer';

export function GET(_: { url: string, parameters?: any }): Promise<XMLHttpRequest> {
  let url;
  if (_.parameters) {
    let t = template.parse(_.url);
    url = t.expand(_.parameters);
  } else {
    url = _.url;
  }
  return new Promise((resolve, reject) => {

    let req: XMLHttpRequest = new XMLHttpRequest();

    req.open('GET', url);
    req.addEventListener('load', (__) => {
      if (req.status === 200) {
        resolve(req);
      } else {
        reject(req);
      }
    });
    req.addEventListener('error', () => reject(req));
    req.addEventListener('abort', () => reject(req));
    req.send();
  });

}

type HtmlImport = typeof import('*.html');

export function asTemplate(template: HtmlImport): HTMLTemplateElement {
  return html([template] as any);
}
