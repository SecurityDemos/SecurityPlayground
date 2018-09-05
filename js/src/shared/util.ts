export function GET(_: { url: string }): Promise<XMLHttpRequest> {

  return new Promise((resolve, reject) => {

    let req: XMLHttpRequest = new XMLHttpRequest();
    req.open('GET', _.url);
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
