package com.ulling.firebasetest.network;

import com.ulling.firebasetest.entites.youtube.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by P100651 on 2017-07-04.
 * <p>
 * https://futurestud.io/tutorials/retrofit-2-add-multiple-query-parameter-with-querymap
 * <p>
 * https://stackoverflow.com/questions/36730086/retrofit-2-url-query-parameter
 */
public interface YoutubeApi {

    //    @FormUrlEncoded
//    @POST("search")
//    Call<MobilekeyTokenResult> getMobilekeyTokenList(@Header("APIKey") String apikey, @Path("BID") String BID);

    // https://www.googleapis.com/youtube/v3/search
    // GET https://www.googleapis.com/youtube/v3/search?part=snippet&location=37.42307%2C+-122.08427&locationRadius=50km&maxResults=10&order=date&type=video%2Clist&key={YOUR_API_KEY}
    @GET("search")
    Call<SearchResponse> getSearchList(@Query("key") String apiKey, @Query("part") String part, @Query("location") String location,
                                       @Query("locationRadius") String locationRadius, @Query("maxResults") int maxResults, @Query("order") String order,
                                       @Query("type") String type, @Query("q") String q);

    @GET("search")
    Call<SearchResponse> getSearchList(@Query("key") String apiKey, @Query("part") String part,
                                     @Query("maxResults") int maxResults, @Query("order") String order,
                                       @Query("type") String type, @Query("q") String q);

}