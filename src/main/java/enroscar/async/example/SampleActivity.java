package enroscar.async.example;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.stanfy.enroscar.async.Action;
import com.stanfy.enroscar.async.Async;
import com.stanfy.enroscar.async.Load;
import com.stanfy.enroscar.goro.BoundGoro;
import com.stanfy.enroscar.goro.Goro;
import com.stanfy.enroscar.goro.support.AsyncGoro;

import java.util.concurrent.Callable;

import enroscar.async.example.api.SearchResult;
import enroscar.async.example.api.Tweet;
import enroscar.async.example.api.TwitterAPI;

/**
 * @author Roman Mazur - Stanfy (http://stanfy.com)
 */
public class SampleActivity extends FragmentActivity {

  private final BoundGoro goro = Goro.bindWith(this);

  TwitterAPI api;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    api = ((SampleApplication) getApplicationContext()).getApi();

    final TextView view = new TextView(this);
    view.setGravity(Gravity.CENTER);
    view.setTextSize(16);
    setContentView(view);

    view.setText("Loading...");

    final SampleActivityOperator operator = SampleActivityOperator.build()
        .operations(this)
        .withinActivity(this)
        .get();

    operator.when().tweetsIsFinished()
        .doOnResult(new Action<SearchResult>() {
          @Override
          public void act(SearchResult data) {
            StringBuilder text = new StringBuilder();
            for (Tweet t : data.getStatuses()) {
              text.append(t.getText()).append("\n");
            }
            view.setText(text);
          }
        })
        .doOnError(new Action<Throwable>() {
          @Override
          public void act(Throwable error) {
            Log.e("123123", "Oops", error);
          }
        });

    view.setText("Loading");
    operator.tweets("androiddev");

    view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        view.setText("Loading");
        operator.forceTweets("stanfy");
      }
    });

  }

  @Override
  protected void onStart() {
    super.onStart();
    goro.bind();
  }

  @Override
  protected void onStop() {
    super.onStop();
    goro.unbind();
  }

  @Load Async<SearchResult> tweets(final String q) {
    return new AsyncGoro(goro).schedule("adfsdf", new SearchTask(q, api));
  }

  private static class SearchTask implements Callable<SearchResult> {
    private final String q;
    private final TwitterAPI api;
    public SearchTask(String q, TwitterAPI api) {
      this.q = q;
      this.api = api;
    }

    @Override
    public SearchResult call() throws Exception {
      return api.searchTweets(q, 5);
    }
  }
}
