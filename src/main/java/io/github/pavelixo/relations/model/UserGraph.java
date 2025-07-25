package io.github.pavelixo.relations.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@AllArgsConstructor
@Builder
@Node("User")
public class UserGraph {
	@Id
	private String id;
	
	@Builder.Default
	@Relationship(type = "FRIENDS", direction = Relationship.Direction.OUTGOING)
	private Set<UserGraph> friends = new HashSet<>();
	
    public void addFriend(UserGraph friend) {
        this.friends.add(friend);
    }
}
