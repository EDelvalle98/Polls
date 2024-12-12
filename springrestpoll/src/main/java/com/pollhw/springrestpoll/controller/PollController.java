package com.pollhw.springrestpoll.controller;

import com.pollhw.springrestpoll.exceptions.ResourceNotFoundException;
import com.pollhw.springrestpoll.model.Poll;
import com.pollhw.springrestpoll.repository.PollRepository;
import com.pollhw.springrestpoll.service.PollService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@RestController
public class PollController {
    private final Logger logger = LoggerFactory.getLogger(PollController.class);

    @Autowired
    private PollService pollService;

    @GetMapping
    public ResponseEntity<Iterable<Poll>> getAllPolls() {
        logger.info("Fetching all polls");
        Iterable<Poll> allPolls = pollService.getAllPolls();
        logger.debug("Retrieved {} polls", ((Collection<?>) allPolls).size());
        return new ResponseEntity<>(allPolls, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createPoll(@Valid @RequestBody Poll poll) {
        logger.info("Creating a new poll with question: {}", poll.getQuestion());
        poll = pollService.createPoll(poll);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPollUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(poll.getId())
                .toUri();
        responseHeaders.setLocation(newPollUri);

        logger.debug("New poll created with ID: {}", poll.getId());
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/{pollId}")
    public ResponseEntity<?> getPoll(@PathVariable Long pollId) {
        logger.info("Fetching poll with ID: {}", pollId);
        Poll poll = pollService.getPollById(pollId);
        logger.debug("Retrieved poll: {}", poll);
        return new ResponseEntity<>(poll, HttpStatus.OK);
    }

    @PutMapping("/{pollId}")
    public ResponseEntity<?> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId) {
        pollService.updatePoll(pollId, poll);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{pollId}")
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
        pollService.deletePoll(pollId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
//    @Autowired
//    private PollRepository pollRepository;
//
//    @RequestMapping(value="/polls", method= RequestMethod.GET)
//    public ResponseEntity<Iterable<Poll>> getAllPolls() {
//        Iterable<Poll> allPolls = pollRepository.findAll();
//        return new ResponseEntity<>(pollRepository.findAll(), HttpStatus.OK);
//    }
//
//    @RequestMapping(value="/polls", method=RequestMethod.POST)
//    public ResponseEntity<?> createPoll(@Valid @RequestBody Poll poll) {
//        poll = pollRepository.save(poll);
//// Set the location header for the newly created resource
//        HttpHeaders responseHeaders = new HttpHeaders();
//        URI newPollUri = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}").buildAndExpand(poll.getId()).toUri();
//        responseHeaders.setLocation(newPollUri);
//        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
//    }
//    @RequestMapping(value="/polls/{pollId}", method=RequestMethod.GET)
//    public ResponseEntity<?> getPoll(@PathVariable Long pollId) {
//        Optional<Poll> optionalPoll = pollRepository.findById(pollId);
//        if (optionalPoll.isPresent()) {
//            return new ResponseEntity<>(optionalPoll.get(), HttpStatus.OK);
//        } else {
//            throw new ResourceNotFoundException("Poll with id " + pollId + " not found");
//        }
//    }
//    @RequestMapping(value="/polls/{pollId}", method=RequestMethod.PUT)
//    public ResponseEntity<?> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId) {
//// Save the entity
//        Poll p = pollRepository.save(poll);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//    @RequestMapping(value="/polls/{pollId}", method=RequestMethod.DELETE)
//    public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
//        pollRepository.deleteById(pollId);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//protected void verifyPoll(Long pollId) throws ResourceNotFoundException {
//    Optional<Poll> optionalPoll = pollRepository.findById(pollId);
//    if (!optionalPoll.isPresent()) {
//        throw new ResourceNotFoundException("Poll with id " + pollId + " not found");
//    }
//}
//
//    @RequestMapping(value="/polls/{pollId}", method=RequestMethod.GET)
//    public ResponseEntity<?> getPoll(@PathVariable Long pollId) {
//        verifyPoll(pollId);
//        Poll poll = pollRepository.findById(pollId).get();
//        return new ResponseEntity<>(poll, HttpStatus.OK);
//    }
//
//    @RequestMapping(value="/polls/{pollId}", method=RequestMethod.PUT)
//    public ResponseEntity<?> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId) {
//        verifyPoll(pollId);
//        pollRepository.save(poll);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @RequestMapping(value="/polls/{pollId}", method=RequestMethod.DELETE)
//    public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
//        verifyPoll(pollId);
//        pollRepository.deleteById(pollId);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
