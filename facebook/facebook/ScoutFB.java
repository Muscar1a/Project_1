package facebook;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.Page;

import java.util.List;

public class ScoutFB {
    private static final String accessToken = "EAAEEI6GMikcBOZC4BW320ue8UTQhq5L9gcgNmZBGf89QJHrZBlXFVhR71zTMQZCnCWxigoTN6xdlnr1FZCbpHWdmngLjvKgaqsuTn1o4Cl4RsGZCMcZAZAezwCyrWOSA21M9hPfgVlO8niCeCmCsQaNf3RoPyZC09hFI8h7ioeakZCNAXZBdO9RBJAlb3UvowgkZA7iwOBjdPqLMQrMbsiqmfC2qDZCD9LYeZBCWFZC1AHLY4XAgdus6fHFiDx49A3X6yf4uFUcjQZDZD";
    public static void main(String[] args){
        Version version = Version.VERSION_20_ß;
        FacebookClient fbClient = new DefaultFacebookClient(accessToken, version);
        Connection<Page> result = fbClient.fetchConnection("me/accounts", Page.class);
        int counter = 0;
        for (List<Page> fPage : result){
            for (Page page : fPage){
                System.out.println(page.getName());
                System.out.println(page.getLikes());
                System.out.println("fb.com/" + page.getId());
            }
        }
        System.out.println("No. of result:" + counter);
    }
}
