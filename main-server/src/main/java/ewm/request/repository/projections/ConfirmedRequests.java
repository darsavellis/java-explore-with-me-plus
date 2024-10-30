package ewm.request.repository.projections;

public interface ConfirmedRequests {
    long getEventId();

    long getConfirmedRequests();
}
