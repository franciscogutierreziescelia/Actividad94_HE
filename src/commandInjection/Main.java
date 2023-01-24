package commandInjection;
/**
 * Código adaptado de https://cheatsheetseries.owasp.org/cheatsheets/OS_Command_Injection_Defense_Cheat_Sheet.html#java
 * Testeado bajo openjdk-19.0.1
 */

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String[] specialChars = new String[]{"&", "&&", "|", "||"};
        String payload = "bash -c whoami"; // String payload = "cmd /c whoami";
        String cmdTemplate = "java -version %s " + payload;
        String cmd;
        Process p;
        int returnCode;
        for (String specialChar : specialChars) {
            cmd = String.format(cmdTemplate, specialChar);
            System.out.printf("#### TEST CMD: %s\n", cmd);
            p = Runtime.getRuntime().exec(cmd);
            returnCode = p.waitFor();
            System.out.printf("RC    : %s\n", returnCode);
            //System.out.printf("OUT   :\n%s\n", IOUtils.toString(p.getInputStream(),"utf-8"));
            System.out.printf("OUT   :\n%s\n", new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8));
            //System.out.printf("ERROR :\n%s\n", IOUtils.toString(p.getErrorStream(),"utf-8"));
            System.out.printf("ERROR   :\n%s\n", new String(p.getErrorStream().readAllBytes(), StandardCharsets.UTF_8));
        }
        System.out.printf("#### TEST PAYLOAD ONLY: %s\n", payload);
        p = Runtime.getRuntime().exec(payload);
        returnCode = p.waitFor();
        System.out.printf("RC    : %s\n", returnCode);
        //System.out.printf("OUT   :\n%s\n", IOUtils.toString(p.getInputStream(),"utf-8"));
        System.out.printf("OUT   :\n%s\n", new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8));
        //System.out.printf("ERROR :\n%s\n", IOUtils.toString(p.getErrorStream(),"utf-8"));
        System.out.printf("ERROR   :\n%s\n", new String(p.getErrorStream().readAllBytes(), StandardCharsets.UTF_8));
    }
}
