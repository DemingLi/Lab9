package com.example.deminglee.lab9.service;

import rx.Observable;

import com.example.deminglee.lab9.model.Github;
import com.example.deminglee.lab9.model.Repos;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Deming Lee on 2017/12/27.
 */

public interface GithubService {
  @GET("/users/{user}")
  Observable<Github> getUser(@Path("user") String user);
  
  @GET("/users/{user}/repos")
  Observable<List<Repos>> getRepos(@Path("user") String user);
}
