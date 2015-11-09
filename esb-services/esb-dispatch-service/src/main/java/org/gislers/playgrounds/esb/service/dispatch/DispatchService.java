package org.gislers.playgrounds.esb.service.dispatch;

import org.gislers.playgrounds.esb.service.dispatch.dto.DispatchServiceDto;

import javax.ejb.Local;

/**
 * Created by:   jgisle
 * Created date: 10/15/15
 */
@Local
public interface DispatchService {

    void dispatchMessage( DispatchServiceDto dispatchServiceDto );
}
