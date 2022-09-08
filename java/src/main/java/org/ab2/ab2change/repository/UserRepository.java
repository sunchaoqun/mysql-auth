package org.ab2.ab2change.repository;

import com.fasterxml.jackson.databind.node.ArrayNode;


public interface UserRepository {
    ArrayNode getUsers();
}
