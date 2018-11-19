package examples;

import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.junit.Test;

import java.io.*;

public class ExampleCommandInjectionTest {
    @Test
    public void testCmdInjection() throws IOException, InterruptedException {
        String username;

        username = "mario`/bin/echo 'newuser:newpassword' >> /tmp/passwd`";

        Process p = Runtime.getRuntime().exec(new String[]{"/bin/bash"});

        PrintWriter w = new PrintWriter(new OutputStreamWriter(p.getOutputStream()));

        w.println("USERNAME=\""+username+"\"");
        w.println("echo \"Check $USERNAME\"");

        w.close();

        p.waitFor();

        System.err.println(Streams.asString(p.getErrorStream()));
        System.out.println(Streams.asString(p.getInputStream()));

    }

    public static void someThingElse() throws IOException {

        String appHome = System.getProperty("APP_HOME");

        Runtime.getRuntime().exec(appHome+"/scripts/myscript.sh");

    }
}
