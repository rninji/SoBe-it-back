package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.entity.Users;

public interface UserService {
    public Users create(final Users users);
    public Users getByCredentials(final String user_id, final String password);
}
