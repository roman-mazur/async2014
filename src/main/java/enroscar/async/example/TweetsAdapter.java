package enroscar.async.example;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import enroscar.async.example.api.Tweet;

final class TweetsAdapter extends BaseAdapter {

  private List<Tweet> tweets = Collections.emptyList();

  public void setTweets(final List<Tweet> tweets) {
    if (tweets instanceof ArrayList) {
      this.tweets = tweets;
    } else {
      this.tweets = new ArrayList<Tweet>(tweets);
    }
    notifyDataSetChanged();
  }

  public void clear() {
    tweets = Collections.emptyList();
    notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    return tweets.size();
  }

  @Override
  public Tweet getItem(final int position) {
    return tweets.get(position);
  }

  @Override
  public long getItemId(int position) {
    return tweets.get(position).getId();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View view = convertView;
    if (view == null) {
      view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet, parent, false);
    }

    Tweet tweet = tweets.get(position);

    TextView text = (TextView) view;
    text.setText(tweet.getText());

    return view;
  }

}
