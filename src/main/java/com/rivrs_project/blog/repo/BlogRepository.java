package com.rivrs_project.blog.repo;

import com.rivrs_project.blog.models.BlogPost;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface BlogRepository extends CrudRepository<BlogPost, Long> {
    List<BlogPost> findAllByOrderByIdAsc();
}
