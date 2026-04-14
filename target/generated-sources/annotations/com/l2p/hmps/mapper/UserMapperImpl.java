package com.l2p.hmps.mapper;

import com.l2p.hmps.dto.AuthResponse;
import com.l2p.hmps.dto.RegisterRequest;
import com.l2p.hmps.dto.UserResponse;
import com.l2p.hmps.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-13T12:27:41+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(RegisterRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( request.getEmail() );
        user.role( request.getRole() );

        return user.build();
    }

    @Override
    public AuthResponse toAuthResponse(User user) {
        if ( user == null ) {
            return null;
        }

        AuthResponse.AuthResponseBuilder authResponse = AuthResponse.builder();

        authResponse.email( user.getEmail() );
        authResponse.role( user.getRole() );

        return authResponse.build();
    }

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.email( user.getEmail() );
        userResponse.id( user.getId() );
        userResponse.role( user.getRole() );

        return userResponse.build();
    }
}
