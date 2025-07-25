package io.github.pavelixo.relations.repository;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import io.github.pavelixo.relations.model.UserGraph;

public interface UserGraphRepository extends Neo4jRepository<UserGraph, String> {
    @Query("""
            MATCH (u:User {id: $userId})-[:FRIENDS]->(f1:User)-[:FRIENDS]->(f2:User)
            WHERE NOT (u)-[:FRIENDS]->(f2) AND u.id <> f2.id
            RETURN DISTINCT f2
            LIMIT 10
    """)
    List<UserGraph> recommendFriends(@Param("userId") String userId);
    
    @Query("""
    	    MATCH (u:User {id: $userId})--(friend:User)
    	    WHERE (u)-[:FRIENDS]-(friend)
    	    RETURN DISTINCT friend
    """)
    List<UserGraph> findAllFriendsBidirectional(@Param("userId") String userId);

}
