package ewm.user.service;

import com.querydsl.core.BooleanBuilder;
import ewm.exeption.NotFoundException;
import ewm.user.dto.NewUserRequest;
import ewm.user.dto.UserDto;
import ewm.user.mappers.UserMapper;
import ewm.user.model.QUser;
import ewm.user.model.User;
import ewm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final UserRepository userRepository;

    @Override
    public UserDto create(NewUserRequest newUserRequest) {
        User user = UserMapper.toUser(newUserRequest);
        return UserMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public List<UserDto> getAll(List<Long> ids, int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (ids != null && !ids.isEmpty()) {
            booleanBuilder.and(QUser.user.id.in(ids));
        }

        Page<User> usersPage = userRepository.findAll(booleanBuilder, pageable);
        return usersPage.map(UserMapper::toUserDto).toList();
    }

    @Override
    public void delete(long userId) {
        userRepository.deleteById(userId);
    }

    public UserDto getBy(long userId) {
        return userRepository.findById(userId)
                .map(UserMapper::toUserDto)
                .orElseThrow(() -> new NotFoundException("Пользователь с Id =" + userId + " не найден"));
    }
}
