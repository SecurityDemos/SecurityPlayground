import * as template from 'url-template';

export function GET(_: { url: string, parameters?: any }): Promise<XMLHttpRequest> {
  console.log(`Template : ${template}`);
  let t = template.parse(_.url);
  let url = t.expand(_.parameters || {});
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
