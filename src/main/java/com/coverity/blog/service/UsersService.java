package com.coverity.blog.service;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;

import com.coverity.blog.beans.User;


// We simulate some kind of persistent store here.
@Service
public class UsersService {
  private static List<User> userStore = new CopyOnWriteArrayList<User>();

  static {
    loadDefaultContents();
  }

  public UsersService() {}

  public void addUser(User user) {
    userStore.add(user);
  }

  // Basic lookup in a list
  public User getUser(String name) {
    if (name == null)
      return null;

    for (User user: userStore) {
      if (user.getName().equals(name))
        return user;
    }
    return null;
  }

  public List<User> findUser(String contents) {
    List<User> candidates = new ArrayList<User>();
    for (User user: userStore) {
      if (user.getName().indexOf(contents) >=0 || user.getEmail().indexOf(contents) >= 0) {
        candidates.add(user);
      }
    }
    return candidates;
  }

  public User getLatestInsert() {
    return userStore.get(userStore.size() - 1);
  }

  private static void loadDefaultContents() {
    userStore.add(new User("The Doctor", "thedoctor@mailinator.com", "whopass"));
    userStore.add(new User("Clara Oswald", "clara.oswald@mailinator.com", "clara"));
    userStore.add(new User("Tardis", "loopback@future.com", "loopback"));
  }
}
