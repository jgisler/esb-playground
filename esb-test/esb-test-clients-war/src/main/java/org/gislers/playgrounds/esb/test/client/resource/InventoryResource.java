package org.gislers.playgrounds.esb.test.client.resource;

import javax.ws.rs.Path;

/**
 * Created by:   jgisle
 * Created date: 10/15/15
 */
@Path("/inventory")
public class InventoryResource extends AbstractResource {

    @Override
    protected String getClient() {
        return "INVENTORY";
    }
}
