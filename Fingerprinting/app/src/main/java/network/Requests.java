package network;
import java.util.Collection;
import model.SendModel;
import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by Ioana.Radoi on 2/20/2015.
 */
public class Requests {
    private static RestAdapter restAdapter = new RestAdapter.Builder()
            //.setEndpoint("http://10.1.1.2:8080")
            .setEndpoint("http://192.168.43.46:8080")

            .build();

    private static RestService service = restAdapter.create(RestService.class);

    public static void sendInfoUser(SendModel sendModel, Callback<String> callback) {
        service.sendInfoUser(sendModel, callback);
    }


}
