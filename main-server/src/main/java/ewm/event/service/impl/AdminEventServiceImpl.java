package ewm.event.service.impl;


import ewm.category.service.PublicCategoryService;
import ewm.event.dto.AdminEventParam;
import ewm.event.dto.EventFullDto;
import ewm.event.dto.UpdateEventAdminRequest;
import ewm.event.mappers.EventMapper;
import ewm.event.model.Event;
import ewm.event.repository.EventRepository;
import ewm.event.service.AdminEventService;
import ewm.exeption.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {
    final EventRepository eventRepository;
    final PublicCategoryService categoryService;

    @Override
    public List<EventFullDto> getAllBy(AdminEventParam eventParam) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Pageable pageable = PageRequest.of(eventParam.getFrom(), eventParam.getSize());
        System.out.println(eventParam);
        return eventRepository.findAll(pageable).stream()
                .filter(elem -> eventParam.getUsers().contains(elem.getInitiator().getId()))
                .filter(elem -> eventParam.getStates().contains(elem.getState()))
                .filter(elem -> eventParam.getCategories().contains(elem.getCategory().getId()))
                .filter(elem -> elem.getEventDate().isAfter(LocalDateTime.parse(eventParam.getRangeStart(), formatter)))
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateBy(long eventId, UpdateEventAdminRequest updateEvent) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с с id = " + eventId + " не найдено"));

        event.setAnnotation(updateEvent.getAnnotation());
        event.setDescription(updateEvent.getDescription());
        event.setEventDate(updateEvent.getEventDate());
        event.setLocation(updateEvent.getLocation());
        event.setPaid(updateEvent.isPaid());
        event.setParticipantLimit(updateEvent.getParticipantLimit());
        event.setRequestModeration(updateEvent.isRequestModeration());
        event.setState(updateEvent.getStateAction());
        event.setTitle(updateEvent.getTitle());

        eventRepository.save(event);
        return EventMapper.toEventFullDto(event);
//        return null;

    }
}
