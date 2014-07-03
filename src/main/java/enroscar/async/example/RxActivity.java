package enroscar.async.example;

import android.os.Bundle;
import android.util.Log;

import com.stanfy.enroscar.async.Load;
import com.stanfy.enroscar.goro.support.RxGoro;

import enroscar.async.example.api.SearchResult;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * This activity loads tweets. It uses RxJava API to react on tweets being loaded.
 * Tweets are loaded by an operation running in a Service.
 * And activity uses a Loader to bind to a running operation after recreation.
 */
public class RxActivity extends TweetsActivity {

  private final RxGoro rxGoro = new RxGoro(getGoro());
  private RxActivityOperator operator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    operator = RxActivityOperator.build()
        .operations(this)
        .withinActivity(this)
        .get();

    operator.when().tweetsIsFinished()
        .doOnCompleted(new Action0() {
          @Override
          public void call() {
            Log.d("123123", "COMPLETED");
          }
        })
        .subscribe(new Action1<SearchResult>() {
          @Override
          public void call(SearchResult data) {
            adapter.setTweets(data.getStatuses());
          }
        }, new Action1<Throwable>() {
          @Override
          public void call(Throwable error) {
            Log.e("123123", "aaa", error);
          }
        });



    operator.tweets(QUERY);
  }

  @Load Observable<SearchResult> tweets(final String q) {
    return rxGoro.schedule("tweets queue", new SearchTask(q, api));
  }

  @Override
  protected void onRefresh() {
    operator.forceTweets(QUERY);
  }
}
