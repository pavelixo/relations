package io.github.pavelixo.relations.service;

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
				.orElseThrow();
		
		UserGraph friend = userGraphRepository
				.findById(friendId)
				.orElseThrow();
		
		user.addFriend(friend);
		userGraphRepository.save(user);
	}
}