package io.github.pavelixo.relations.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.github.pavelixo.relations.model.UserDocument;

public interface UserDocumentRepository extends MongoRepository<UserDocument, String> {}
