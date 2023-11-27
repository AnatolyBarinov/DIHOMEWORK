package org.example.repository;

import org.example.model.Post;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


public class PostRepository {
  private ConcurrentHashMap<Long, String> posts = new ConcurrentHashMap<>();
  private AtomicLong count = new AtomicLong(0);

  public ConcurrentHashMap<Long, String> all() {
    return posts;
  }

  public Optional<Post> getById(long id) {
    for (long key : posts.keySet()) {
      if (key == id) {
        Post post = new Post(id, posts.get(id));
        return Optional.of(post);
      }
    }
    return Optional.empty();
  }

  public Post save(Post post) {
    if (post.getId() != 0) {
      for (long key : posts.keySet()) {
        if (key == post.getId()) {
          return post;
        }
      }
    }
    long id = count.incrementAndGet();
    post.setId(id);
    posts.put(id, post.getContent());
    return post;
  }

  public void removeById(long id) {
    for (long key : posts.keySet()) {
      if (key == id) {
        posts.remove(id);
        break;
      }
    }
  }
}
