package io.github.pavelixo.relations.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.pavelixo.relations.model.UserDocument;
import io.github.pavelixo.relations.model.UserGraph;
import io.github.pavelixo.relations.repository.UserDocumentRepository;
import io.github.pavelixo.relations.repository.UserGraphRepository;

@Service
public class UserService {
	@Autowired
	private UserDocumentRepository userDocumentRepository;
	
	@Autowired
	private UserGraphRepository userGraphRepository;
	
	public List<UserDocument> getAllUsers() {
	    return userDocumentRepository.findAll();
	}
	
	public List<UserDocument> getFriends(String userId) {
		List<UserGraph> friends= userGraphRepository
				.findAllFriendsBidirectional(userId);
		return userDocumentRepository
				.findAllById(extractIds(friends));
	}
	
	public List<UserDocument> getRecommend(String userId) {
		List<UserGraph> friends = userGraphRepository.recommendFriends(userId);
		return userDocumentRepository
				.findAllByIdIn(extractIds(friends));
	}
	
	@Transactional
	public UserDocument createUser(String username) {
		UserDocument userDocument = UserDocument
				.builder()
				.username(username)
				.build();
		userDocumentRepository.save(userDocument);
		
		UserGraph userGraph = UserGraph
				.builder()
				.id(userDocument.getId())
				.build();
		userGraphRepository.save(userGraph);
		
		return userDocument;
	}
	
	@Transactional
	public void addFriend(String userId, String friendId) {
		UserGraph user = userGraphRepository
				.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException(String.format("User not found: %s", userId)));

		UserGraph friend = userGraphRepository
				.findById(friendId)
				.orElseThrow(() -> new IllegalArgumentException(String.format("Friend not found: %s", userId)));

		user.addFriend(friend);
		userGraphRepository.save(user);
	}
	
	private List<String> extractIds(List<UserGraph> friendsGraph) {
	    return friendsGraph.stream()
	            .map(UserGraph::getId)
	            .collect(Collectors.toList());
	}
}