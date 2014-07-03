package enroscar.async.example;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.stanfy.enroscar.goro.BoundGoro;
import com.stanfy.enroscar.goro.Goro;

import enroscar.async.example.api.TwitterAPI;

abstract class TweetsActivity extends FragmentActivity {

  protected static final String QUERY = "androiddev";

  final TweetsAdapter adapter = new TweetsAdapter();

  private final BoundGoro goro = Goro.bindWith(this);

  TwitterAPI api;

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    api = ((SampleApplication) getApplicationContext()).getApi();

    ListView list = new ListView(this);
    list.setAdapter(adapter);
    setContentView(list);
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

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_refresh:
        adapter.clear();
        onRefresh();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  protected Goro getGoro() {
    return goro;
  }

  protected abstract void onRefresh();

}
