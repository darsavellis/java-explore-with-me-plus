package ewm.event.mappers;

import ewm.category.mapper.categoryDto.CategoryDtoMapperImpl;
import ewm.event.dto.EventFullDto;
import ewm.event.dto.NewEventDto;
import ewm.event.model.Event;
import ewm.event.model.EventState;
import ewm.user.mappers.UserMapper;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class EventMapper {
    public Event toEvent(NewEventDto newEventDto) {
        Event event = new Event();
        event.setAnnotation(newEventDto.getAnnotation());
        event.setCreatedOn(LocalDateTime.now());
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(newEventDto.getEventDate());
        event.setLocation(newEventDto.getLocation());
        event.setPaid(newEventDto.isPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.isRequestModeration());
        event.setState(EventState.PENDING.toString());
        event.setTitle(newEventDto.getTitle());
        event.setViews(0L);
        return event;
    }

    public EventFullDto toEventFullDto(Event event) {
        EventFullDto eventFullDto = new EventFullDto();
        eventFullDto.setId(event.getId());
        eventFullDto.setAnnotation(event.getAnnotation());
        eventFullDto.setCategory(new CategoryDtoMapperImpl().toCategoryDto(event.getCategory()));
        eventFullDto.setDescription(event.getDescription());
        eventFullDto.setEventDate(event.getEventDate());
        eventFullDto.setInitiator(UserMapper.toUserShortDto(event.getInitiator()));
        eventFullDto.setLocation(event.getLocation());
        eventFullDto.setPaid(event.isPaid());
        eventFullDto.setParticipantLimit(event.getParticipantLimit());
        eventFullDto.setRequestModeration(event.isRequestModeration());
        eventFullDto.setState(event.getState());
        eventFullDto.setTitle(event.getTitle());
        return eventFullDto;
    }

}
