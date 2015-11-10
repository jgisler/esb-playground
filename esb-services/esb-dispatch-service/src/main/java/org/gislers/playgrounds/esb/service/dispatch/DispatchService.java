package org.gislers.playgrounds.esb.service.dispatch;

import org.gislers.playgrounds.esb.service.dispatch.dto.DispatchServiceDto;

/**
 * Created by:   jgisle
 * Created date: 10/15/15
 */
public interface DispatchService {
    void dispatchMessage( DispatchServiceDto dispatchServiceDto );
}
