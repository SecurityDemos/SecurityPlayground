import { UserInfo } from "../model/model";
import { GET } from "../shared/util";
import { BACKEND_URL } from "../environments/environment";

export async function sendLoginRequest(username: string, password: string): Promise<string> {
  let res = await GET({
    url: `${BACKEND_URL}/login/bad1?username={username}&password={password}`, parameters: {
      username: username,
      password: password,
    }
  });
  return res.response;
}

export async function sendLoginRequest2(username: string, password: string): Promise<string> {
  let res = await GET({
    url: `${BACKEND_URL}/login/bad2?username={username}&password={password}`, parameters: {
      username: username,
      password: password,
    }
  });
  return res.response;
}

export async function sendLoginRequestOk(username: string, password: string): Promise<string> {
  let res = await GET({
    url: `${BACKEND_URL}/login?username={username}&password={password}`, parameters: {
      username: username,
      password: password,
    }
  });
  return res.response;
}

export async function sendUserInfoRequest(token: string): Promise<UserInfo> {
  let res = await GET({
    url: `${BACKEND_URL}/user/me`,
    headers: new Map<string, string>([['X-AUTH', token]])
  });
  if (res.response) {
    return JSON.parse(res.response);
  } else {
    throw 'Cannot login';
  }
}
