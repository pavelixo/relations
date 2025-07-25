package io.github.pavelixo.relations.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.github.pavelixo.relations.model.UserDocument;

public interface UserDocumentRepository extends MongoRepository<UserDocument, String> {
	List<UserDocument> findAllByIdIn(List<String> ids);
}
