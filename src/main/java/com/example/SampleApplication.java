package com.example;

import android.app.Application;

import com.example.api.TwitterAPI;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * @author Roman Mazur - Stanfy (http://stanfy.com)
 */
public class SampleApplication extends Application {

  private TwitterAPI api;

  @Override
  public void onCreate() {
    super.onCreate();
    RestAdapter adapter = new RestAdapter.Builder()
        .setEndpoint(TwitterAPI.DEFAULT_URL)
        .setLogLevel(RestAdapter.LogLevel.BASIC)
        .setRequestInterceptor(new RequestInterceptor() {
          @Override
          public void intercept(RequestFacade request) {
            request.addHeader("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAJ3PVgAAAAAA%2BPcuieQDv6hOE9SyWM2AOWjIloc%3DU2fCluURs5dnG5A3WaaVhNgiBjXKkV5lynvoquGu7ediOCRWiF");
          }
        })
        .build();
    api = adapter.create(TwitterAPI.class);
  }

  public TwitterAPI getApi() {
    return api;
  }
}
