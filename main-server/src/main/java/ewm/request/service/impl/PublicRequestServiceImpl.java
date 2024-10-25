package ewm.request.service.impl;

import ewm.event.model.Event;
import ewm.event.model.EventState;
import ewm.event.repository.EventRepository;
import ewm.exception.NotFoundException;
import ewm.exception.PermissionException;
import ewm.exception.ValidationException;
import ewm.request.dto.ParticipationRequestDto;
import ewm.request.mapper.RequestMapper;
import ewm.request.model.ParticipationRequest;
import ewm.request.model.RequestStatus;
import ewm.request.repository.RequestRepository;
import ewm.request.service.PublicRequestService;
import ewm.user.model.User;
import ewm.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublicRequestServiceImpl implements PublicRequestService {
    final RequestRepository requestRepository;
    final UserRepository userRepository;
    final EventRepository eventRepository;
    final RequestMapper requestMapper;

    @Override
    public List<ParticipationRequestDto> getSentBy(long userId) {
        return requestRepository.findAllByRequesterId(userId)
            .stream().map(requestMapper::toParticipantRequestDto).toList();
    }

    @Override
    public ParticipationRequestDto send(long userId, long eventId) {
        User requester = userRepository.findById(userId)
            .orElseThrow();
        Event event = eventRepository.findById(eventId)
            .orElseThrow();
        if (requester.getId().equals(event.getInitiator().getId())) {
            throw new PermissionException("Нельзя делать запрос на свое событие");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ValidationException("Заявка должна быть в состоянии PUBLISHED");
        }
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() == event.getConfirmedRequests()) {
            throw new ValidationException("Лимит запросов исчерпан");
        }
        ParticipationRequest request = requestMapper.toParticipationRequest(event, requester);
        if (!event.isRequestModeration() && event.getParticipantLimit() == 0) {
            request.setStatus(RequestStatus.CONFIRMED);
        }
        return requestMapper.toParticipantRequestDto(requestRepository.save(request));
    }

    @Override
    public ParticipationRequestDto cancel(long requestId, long userId) {
        User requester = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("Requester с таким Id не найден"));

        ParticipationRequest participationRequest = requestRepository.findById(requestId)
            .orElseThrow(() -> new NotFoundException("Запрос не найден"));

        if (userId != participationRequest.getRequester().getId()) {
            throw new PermissionException("Доступ запрещен. Отменять может только владелец");
        }

        if (participationRequest.getStatus().equals(RequestStatus.PENDING)) {
            participationRequest.setStatus(RequestStatus.CANCELED);
        }

        return requestMapper.toParticipantRequestDto(participationRequest);
    }
}
