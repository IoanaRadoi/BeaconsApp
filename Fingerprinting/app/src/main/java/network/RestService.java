package network;

import java.util.Collection;

import model.SendModel;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Ioana.Radoi on 2/20/2015.
 */
public interface RestService {

    @POST("/CustomerBehaviour/Incoming")
    public void sendInfoUser(@Body SendModel sendModel, Callback<String> callback);


}
