package com.example.deminglee.lab9.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.deminglee.lab9.R;
import com.example.deminglee.lab9.factory.ServiceFactory;
import com.example.deminglee.lab9.model.Repos;
import com.example.deminglee.lab9.service.GithubService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import rx.Subscriber;
import rx.android.concurrency.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ReposActivity extends AppCompatActivity {
  private ProgressBar progressBar;
  private ListView reposList;
  private String user;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_repos);
    initView();
    user = getIntent().getStringExtra("user");
    getRepos();
  }
  
  private void initView() {
    progressBar = ((ProgressBar)findViewById(R.id.progress));
    reposList = ((ListView)findViewById(R.id.repos_list));
  }
  
  private void removeProgressBar() {
    this.progressBar.setVisibility(View.GONE);
    this.reposList.setVisibility(View.VISIBLE);
  }
  
  private void getRepos() {
    ((GithubService) ServiceFactory.getmRetrofit("https://api.github.com").create(GithubService.class))
            .getRepos(this.user).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<List<Repos>>() {
              public void onCompleted() {
                ReposActivity.this.removeProgressBar();
              }
              
              public void onError(Throwable paramAnonymousThrowable) {
                Toast.makeText(ReposActivity.this, paramAnonymousThrowable.hashCode() + "获取失败", Toast.LENGTH_SHORT).show();
                ReposActivity.this.removeProgressBar();
              }
              
              public void onNext(List<Repos> paramAnonymousList) {
                ReposActivity.this.setAdapter(paramAnonymousList);
              }
            });
  }
  
  private void setAdapter(List<Repos> list) {
    ArrayList arrayList = new ArrayList();
    Iterator iterator = list.iterator();
    while (iterator.hasNext()) {
      Repos localRepos = (Repos)iterator.next();
      LinkedHashMap map = new LinkedHashMap();
      map.put("name_text", localRepos.getName());
      map.put("language_text", localRepos.getLanguage());
      map.put("description_text", localRepos.getDescription());
      arrayList.add(map);
    }
    SimpleAdapter adapter = new SimpleAdapter(this, arrayList, R.layout.item_detail,
            new String[] { "name_text", "language_text", "description_text" },
            new int[] { R.id.name, R.id.language, R.id.description });
    this.reposList.setAdapter(adapter);
  }
}
