package ewm.request.service;

import ewm.event.model.Event;
import ewm.event.repository.EventRepository;
import ewm.exception.NotFoundException;
import ewm.exception.PermissionException;
import ewm.request.dto.EventRequestStatusUpdateRequest;
import ewm.request.dto.EventRequestStatusUpdateResult;
import ewm.request.dto.ParticipationRequestDto;
import ewm.request.mapper.RequestMapper;
import ewm.request.model.ParticipationRequest;
import ewm.request.model.RequestStatus;
import ewm.request.repository.RequestRepository;
import ewm.user.mappers.UserMapper;
import ewm.user.model.User;
import ewm.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestServiceImpl implements RequestService {
    final RequestRepository requestRepository;
    final UserRepository userRepository;
    final EventRepository eventRepository;
    final UserMapper userMapper;
    final RequestMapper requestMapper;

    @Override
    public List<ParticipationRequestDto> getReceivedBy(long userId, long eventId) {
        return requestRepository.findAllByEventIdAndEventInitiatorId(eventId, userId).stream()
            .map(requestMapper::toParticipantRequestDto).toList();
    }

    @Override
    public EventRequestStatusUpdateResult update(long userId, long eventId, EventRequestStatusUpdateRequest updateRequest) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new NotFoundException(""));
        List<Long> requestsIds = updateRequest.getRequestIds();
        List<ParticipationRequest> requests =
            requestRepository.findAllByIdInAndEventIdIs(requestsIds, eventId);
        long limit = event.getParticipantLimit() - event.getConfirmedRequests();
        if (requests.size() != updateRequest.getRequestIds().size()) {
            throw new IllegalArgumentException();
        }
        List<ParticipationRequest> confirmed = new ArrayList<>();
        switch (updateRequest.getStatus()) {
            case CONFIRMED -> {
                while (limit-- > 0 && !requests.isEmpty()) {
                    ParticipationRequest request = requests.removeFirst();
                    request.setStatus(RequestStatus.CONFIRMED);
                    confirmed.add(request);
                }
            }
            case REJECTED -> {
                requests.forEach(participationRequest -> participationRequest.setStatus(RequestStatus.REJECTED));
            }
        }
        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();
        result.setConfirmedRequests(confirmed.stream().map(requestMapper::toParticipantRequestDto).toList());
        result.setRejectedRequests(requests.stream().map(requestMapper::toParticipantRequestDto).toList());
        return result;
    }

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
        ParticipationRequest request = requestMapper.toParticipationRequest(event, requester);

        return requestMapper.toParticipantRequestDto(requestRepository.save(request));
    }

    @Override
    public ParticipationRequestDto cancel(long requestId, long userId) {
        User requester = userRepository.findById(userId)
            .orElseThrow();

        ParticipationRequest participationRequest = requestRepository.findById(requestId)
            .orElseThrow(() -> new NotFoundException(""));
        if (userId != participationRequest.getRequester().getId()) {
            throw new PermissionException("");
        }
        if (participationRequest.getStatus().equals(RequestStatus.PENDING)) {
            participationRequest.setStatus(RequestStatus.CANCELED);
        }
        return requestMapper.toParticipantRequestDto(participationRequest);
    }
}
