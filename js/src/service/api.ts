import { UserInfo } from "../model/model";
import { GET } from "../shared/util";
import { BACKEND_URL } from "../environments/environment";

export async function sendLoginRequest(username: string, password: string): Promise<UserInfo> {
  let res = await GET({
    url: `${BACKEND_URL}/login/bad?username={username}&password={password}`, parameters: {
      username: username,
      password: password,
    }
  });
  if (res.response) {
    return JSON.parse(res.response);
  } else {
    throw 'Cannot login';
  }
}
