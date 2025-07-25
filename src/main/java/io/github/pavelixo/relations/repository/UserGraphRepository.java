package io.github.pavelixo.relations.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import io.github.pavelixo.relations.model.UserGraph;

public interface UserGraphRepository extends Neo4jRepository<UserGraph, String> {}
