package ewm.event.mappers;

import ewm.category.mapper.categoryDto.CategoryDtoMapperImpl;
import ewm.category.model.Category;
import ewm.event.dto.EventFullDto;
import ewm.event.dto.NewEventDto;
import ewm.event.model.Event;
import ewm.user.mappers.UserMapper;
import ewm.user.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EventMapper {
    public Event toEvent(NewEventDto newEventDto, User initiator, Category category) {
        Event event = new Event();
        event.setAnnotation(newEventDto.getAnnotation());
        event.setCategory(category);
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(newEventDto.getEventDate());
        event.setInitiator(initiator);
        event.setLocation(newEventDto.getLocation());
        event.setPaid(newEventDto.isPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.isRequestModeration());
        event.setTitle(newEventDto.getTitle());
        return event;
    }

    public EventFullDto toEventFullDto(Event event) {
        EventFullDto eventFullDto = new EventFullDto();
        eventFullDto.setAnnotation(event.getAnnotation());
        eventFullDto.setCategory(new CategoryDtoMapperImpl().toCategoryDto(event.getCategory()));
        eventFullDto.setDescription(event.getDescription());
        eventFullDto.setEventDate(event.getEventDate());
        eventFullDto.setInitiator(UserMapper.toUserShortDto(event.getInitiator()));
        eventFullDto.setLocation(event.getLocation());
        eventFullDto.setPaid(event.isPaid());
        eventFullDto.setParticipantLimit(event.getParticipantLimit());
        eventFullDto.setTitle(event.getTitle());
        return eventFullDto;
    }
}
