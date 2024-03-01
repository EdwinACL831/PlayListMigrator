package com.example.playlistmigrator;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SpotifyAPI {
    @GET("trends/country_forecast/?country=US&freq=Q&transform=2&dateStart=2021-12-31&dateEnd=2023-12-31")
    Call<String> get();

    @FormUrlEncoded
    @POST("token")
    Call<AuthObject> authenticate(@Field("grant_type") String grantType,
                                  @Field("client_id") String clientId,
                                  @Field("client_secret") String clientSecret);
}
