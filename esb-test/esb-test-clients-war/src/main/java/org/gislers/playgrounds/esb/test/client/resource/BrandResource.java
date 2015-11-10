package org.gislers.playgrounds.esb.test.client.resource;

import javax.ejb.Stateless;
import javax.ws.rs.Path;

/**
 * Created by:   jgisle
 * Created date: 10/15/15
 */
@Stateless
@Path("/brand")
public class BrandResource extends AbstractResource {

    @Override
    protected String getClient() {
        return "BRAND";
    }
}
