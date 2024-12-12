package com.pollhw.springrestpoll.repository;

import com.pollhw.springrestpoll.model.Poll;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends CrudRepository <Poll, Long>{

}
