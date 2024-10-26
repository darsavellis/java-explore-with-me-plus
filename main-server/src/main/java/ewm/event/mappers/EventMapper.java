package ewm.event.mappers;

import ewm.category.mapper.CategoryMapper;
import ewm.category.model.Category;
import ewm.event.dto.*;
import ewm.event.model.Event;
import ewm.user.mappers.UserMapper;
import ewm.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, UserMapper.class, StateActionMapper.class},
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventMapper {
    @Mapping(target = "category", source = "category")
    @Mapping(target = "initiator", source = "initiator")
    @Mapping(target = "id", ignore = true)
    Event toEvent(NewEventDto newEventDto, User initiator, Category category);

    EventFullDto toEventFullDto(Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "category")
    @Mapping(target = "state", source = "updateEventUserRequest.stateAction")
    Event toUpdatedEvent(@MappingTarget Event event, UpdateEventUserRequest updateEventUserRequest, Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "category")
    @Mapping(target = "state", source = "updateEventAdminRequest.stateAction")
    Event toUpdatedEvent(@MappingTarget Event event, UpdateEventAdminRequest updateEventAdminRequest, Category category);

    EventShortDto toEventShortDto(Event event);
}
