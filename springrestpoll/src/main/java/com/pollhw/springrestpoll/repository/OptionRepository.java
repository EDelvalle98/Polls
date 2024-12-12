package com.pollhw.springrestpoll.repository;

import com.pollhw.springrestpoll.model.OptionModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends CrudRepository <OptionModel, Long>{

}
