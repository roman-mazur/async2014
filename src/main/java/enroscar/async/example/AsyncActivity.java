package enroscar.async.example;

import android.os.Bundle;
import android.util.Log;

import com.stanfy.enroscar.async.Action;
import com.stanfy.enroscar.async.Async;
import com.stanfy.enroscar.async.Load;
import com.stanfy.enroscar.goro.support.AsyncGoro;

import enroscar.async.example.api.SearchResult;

/**
 * @author Roman Mazur - Stanfy (http://stanfy.com)
 */
public class AsyncActivity extends TweetsActivity {

  private final AsyncGoro asyncGoro = new AsyncGoro(getGoro());

  private AsyncActivityOperator operator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    operator = AsyncActivityOperator.build()
        .operations(this)
        .withinActivity(this)
        .get();

    operator.when().tweetsIsFinished()
        .doOnResult(new Action<SearchResult>() {
          @Override
          public void act(SearchResult data) {
            adapter.setTweets(data.getStatuses());
          }
        })
        .doOnError(new Action<Throwable>() {
          @Override
          public void act(Throwable error) {
            Log.e("123123", "Oops", error);
          }
        });

    operator.tweets(QUERY);
  }

  @Load Async<SearchResult> tweets(final String q) {
    return asyncGoro.schedule("adfsdf", new SearchTask(q, api));
  }

  @Override
  protected void onRefresh() {
    operator.forceTweets(QUERY);
  }

}
