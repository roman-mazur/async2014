package enroscar.async.example;

import java.util.concurrent.Callable;

import enroscar.async.example.api.SearchResult;
import enroscar.async.example.api.TwitterAPI;

/**
 * Task that tweets from network.
 */
class SearchTask implements Callable<SearchResult> {
  private final String q;
  private final TwitterAPI api;

  public SearchTask(String q, TwitterAPI api) {
    this.q = q;
    this.api = api;
  }

  @Override
  public SearchResult call() {
    return api.searchTweets(q, 10);
  }
}
