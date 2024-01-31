package play;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class NetworkCaller {
    private String callName;
    public NetworkCaller(String callName) {
        this.callName = callName;
    }

    public String makeCall(int secs) throws URISyntaxException, IOException {
        System.out.println(callName + " : BEG call : " + Thread.currentThread());

        try {
            URI uri = new URI("http://httpbin.org/delay/" + secs);
            try (InputStream inputStream = uri.toURL().openStream()) {
                return new String(inputStream.readAllBytes());
            }
        } finally {
            System.out.println(callName + " : END call : " + Thread.currentThread());
        }
    }
}
