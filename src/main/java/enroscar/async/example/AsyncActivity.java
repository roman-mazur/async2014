package enroscar.async.example;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.stanfy.enroscar.async.Action;
import com.stanfy.enroscar.async.Async;
import com.stanfy.enroscar.async.Load;
import com.stanfy.enroscar.goro.support.AsyncGoro;

import enroscar.async.example.api.SearchResult;

/**
 * This activity loads tweets.
 * Tweets are loaded by an operation running in a Service.
 * And activity uses a Loader to bind to a running operation after recreation.
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
        })

    .alsoWhen().tweetsIsStartedDo(new Runnable() {
      @Override
      public void run() {
        Toast.makeText(getApplicationContext(), "Started", Toast.LENGTH_SHORT).show();
      }
    });

    //operator.tweets(QUERY);
  }

  @Load Async<SearchResult> tweets(final String q) {
    return asyncGoro.schedule("queue name", new SearchTask(q, api));
  }

  @Override
  protected void onRefresh() {
    operator.forceTweets(QUERY);
  }

}
