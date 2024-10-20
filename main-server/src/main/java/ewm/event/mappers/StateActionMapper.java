package ewm.event.mappers;

import ewm.category.mapper.CategoryMapper;
import ewm.event.model.EventState;
import ewm.event.model.StateAction;
import ewm.user.mappers.UserMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, UserMapper.class, EventMapper.class})
public interface StateActionMapper {
    default EventState toEventState(StateAction stateAction) {
        return switch (stateAction) {
            case SEND_TO_REVIEW -> EventState.PENDING;
            case CANCEL_REVIEW -> EventState.CANCELED;
        };
    }
}
